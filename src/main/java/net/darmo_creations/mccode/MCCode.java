package net.darmo_creations.mccode;

import net.darmo_creations.mccode.commands.ProgramCommand;
import net.darmo_creations.mccode.interpreter.ProgramErrorReport;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

// TODO mod icon
public class MCCode implements ModInitializer {
  public static final String MOD_ID = "mccode";
  public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

  public static final GameRules.Key<GameRules.BooleanRule> GR_SHOW_ERROR_MESSAGES =
      GameRuleRegistry.register("show_mccode_error_messages", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

  public static MCCode INSTANCE;

  /**
   * Map associating worlds to their program managers.
   */
  public final Map<World, ProgramManager> PROGRAM_MANAGERS = new HashMap<>();

  @Override
  public void onInitialize() {
    ServerTickEvents.START_WORLD_TICK.register(this::onWorldTickStart);
    ServerWorldEvents.LOAD.register(this::onWorldLoad);
    ProgramManager.declareDefaultBuiltinTypes();
    ProgramManager.declareDefaultBuiltinFunctions();
    ProgramManager.initialize();
    this.registerCommands();
    // Nasty hack, but don’t know how to do it any other way…
    INSTANCE = this;
  }

  /**
   * Registers all custom commands.
   */
  private void registerCommands() {
    CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> ProgramCommand.register(dispatcher));
  }

  private void onWorldLoad(MinecraftServer server, ServerWorld world) {
    this.PROGRAM_MANAGERS.put(world, world.getPersistentStateManager().getOrCreate(
        compound -> new ProgramManager(world, compound),
        () -> new ProgramManager(world),
        "program_manager"
    ));
  }

  private void onWorldTickStart(ServerWorld world) {
    ProgramManager programManager = this.PROGRAM_MANAGERS.get(world);
    for (ProgramErrorReport report : programManager.executePrograms()) {
      // Log errors in chat and server console
      MutableText message;
      if (report.line() != -1 && report.column() != -1) {
        message = new LiteralText(
            String.format("[%s:%d:%d] ", report.scope().getProgram().getName(), report.line(), report.column()))
            .setStyle(Style.EMPTY.withColor(Formatting.RED));
      } else {
        message = new LiteralText(String.format("[%s] ", report.scope().getProgram().getName()))
            .setStyle(Style.EMPTY.withColor(Formatting.RED));
      }
      message.append(new TranslatableText(report.translationKey(), report.args())
          .setStyle(Style.EMPTY.withColor(Formatting.RED)));

      // Only show error messages to players that can use the /program command
      if (world.getGameRules().getBoolean(GR_SHOW_ERROR_MESSAGES)) {
        world.getPlayers(PlayerEntity::isCreativeLevelTwoOp)
            .forEach(player -> player.sendMessage(message, false));
      }
      world.getServer().sendSystemMessage(message, Util.NIL_UUID);
    }
  }
}
