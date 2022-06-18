package net.darmo_creations.mccode;

import net.darmo_creations.mccode.commands.ProgramCommand;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.CallStackElement;
import net.darmo_creations.mccode.interpreter.exceptions.ProgramErrorReport;
import net.darmo_creations.mccode.interpreter.exceptions.ProgramErrorReportElement;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.*;
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
  public static final Logger LOGGER = LoggerFactory.getLogger("MC Code");

  public static final GameRules.Key<GameRules.BooleanRule> GR_SHOW_ERROR_MESSAGES =
      GameRuleRegistry.register("show_mccode_error_messages", GameRules.Category.MISC, GameRuleFactory.createBooleanRule(true));

  public static MCCode INSTANCE;

  /**
   * Map associating worlds to their program managers.
   */
  public final Map<World, ProgramManager> PROGRAM_MANAGERS = new HashMap<>();

  @Override
  public void onInitialize() {
    MCCode.LOGGER.info("[MC Code] Setting up…");
    MCCode.LOGGER.info("[MC Code] Setting up program manager");
    ProgramManager.declareDefaultBuiltinTypes();
    ProgramManager.declareDefaultBuiltinFunctions();
    ProgramManager.initialize();
    MCCode.LOGGER.info("[MC Code] Program manager setup done");
    ServerTickEvents.START_WORLD_TICK.register(this::onWorldTickStart);
    ServerWorldEvents.LOAD.register(this::onWorldLoad);
    this.registerCommands();
    // Nasty hack, but don’t know how to do it any other way…
    INSTANCE = this;
    MCCode.LOGGER.info("[MC Code] Setup done");
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
    // Log errors in chat and server console
    for (ProgramErrorReport report : programManager.executePrograms()) {
      MutableText message = null;
      // Add error elements
      for (ProgramErrorReportElement element : report.elements()) {
        MutableText t = new LiteralText(element.exception().getClass().getSimpleName() + ": ")
            .append(new TranslatableText(element.translationKey(), element.args()));
        if (message == null) {
          message = t;
        } else {
          message.append(new LiteralText("\n")).append(t);
        }
        // Add call stack trace
        for (CallStackElement traceElement : element.callStack()) {
          // There should always be at least one ProgramErrorReportElement instance
          message.append(new LiteralText("\n  @ %s.%s [%d:%d]".formatted(
              traceElement.moduleName(), traceElement.scopeName(), traceElement.line(), traceElement.column())));
        }
      }
      if (message != null) {
        message.setStyle(Style.EMPTY.withColor(Formatting.RED));
      }
      // Only show error messages to players that can use the /program command
      if (world.getGameRules().getBoolean(GR_SHOW_ERROR_MESSAGES)) {
        final Text m = message;
        world.getPlayers(PlayerEntity::isCreativeLevelTwoOp)
            .forEach(player -> player.sendMessage(m, false));
      }
      world.getServer().sendSystemMessage(message, Util.NIL_UUID);
    }
  }
}
