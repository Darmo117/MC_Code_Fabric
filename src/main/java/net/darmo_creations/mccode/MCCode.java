package net.darmo_creations.mccode;

import net.darmo_creations.mccode.commands.ProgramCommand;
import net.darmo_creations.mccode.commands.argument_types.ModArgumentTypes;
import net.darmo_creations.mccode.interpreter.CallStackElement;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.exceptions.ProgramErrorReport;
import net.darmo_creations.mccode.interpreter.exceptions.ProgramErrorReportElement;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Mod’s common initializer
 */
public class MCCode implements ModInitializer {
  public static final String MOD_ID = "mccode";
  public static final Logger LOGGER = LoggerFactory.getLogger("MC Code");

  public static final GameRules.Key<GameRules.BooleanRule> GR_SHOW_ERROR_MESSAGES = GameRuleRegistry.register(
      "show_mccode_error_messages",
      GameRules.Category.MISC,
      GameRuleFactory.createBooleanRule(true)
  );
  public static final GameRules.Key<GameRules.BooleanRule> GR_SHOW_WARNING_MESSAGES = GameRuleRegistry.register(
      "show_mccode_warning_messages",
      GameRules.Category.MISC,
      GameRuleFactory.createBooleanRule(false)
  );

  private static MCCode instance;

  /**
   * Return the instance of this mod.
   */
  public static MCCode instance() {
    return instance;
  }

  /**
   * Map associating each world to their program managers.
   */
  private final Map<World, ProgramManager> programManagers = new HashMap<>();
  /**
   * Queue to differ program managers’ loading until all dimensions have been loaded.
   */
  private final List<ServerWorld> worldLoadQueue = new LinkedList<>();

  @Override
  public void onInitialize() {
    instance = this;
    ModArgumentTypes.registerAll();
    LOGGER.info("Setting up…");
    LOGGER.info("Setting up builtins…");
    ProgramManager.declareDefaultBuiltinTypes();
    ProgramManager.declareDefaultBuiltinFunctions();
    ProgramManager.initialize();
    LOGGER.info("Builtins loaded");
    ServerWorldEvents.LOAD.register((server, world) -> this.onWorldLoad(world));
    ServerWorldEvents.UNLOAD.register((server, world) -> this.onWorldUnload(world));
    ServerTickEvents.START_WORLD_TICK.register(this::onWorldTickStart);
    this.registerCommands();
    LOGGER.info("Setup done");
  }

  /**
   * Return the program manager for the given world.
   *
   * @param world A world object.
   * @return The world’s program manager.
   */
  public ProgramManager getProgramManager(final World world) {
    return this.programManagers.get(world);
  }

  /**
   * Registers all custom commands.
   */
  private void registerCommands() {
    LOGGER.info("Registering commands");
    CommandRegistrationCallback.EVENT.register(
        (dispatcher, registryAccess, environment) -> ProgramCommand.register(dispatcher));
  }

  /**
   * Queues a world to differ the loading of its program manager until all dimensions have loaded.
   */
  private void onWorldLoad(final ServerWorld world) {
    this.worldLoadQueue.add(world);
  }

  /**
   * Removes the program manager of the given world, when it is unloaded, from the registry.
   */
  private void onWorldUnload(final ServerWorld world) {
    LOGGER.info("Unloading program manager of dimension " + Utils.getDimension(world));
    this.programManagers.remove(world);
  }

  /**
   * Run all active programs of the given world’s program manager for one tick.
   * <p>
   * If the world queue is not empty, the program managers of the contained worlds
   * will be loaded and the queue emptied.
   */
  private void onWorldTickStart(ServerWorld world) {
    if (!this.worldLoadQueue.isEmpty()) {
      // Load program managers of worlds that are in the queue
      Iterator<ServerWorld> iterator = this.worldLoadQueue.iterator();
      while (iterator.hasNext()) {
        ServerWorld w = iterator.next();
        LOGGER.info("Loading program manager of dimension " + Utils.getDimension(w));
        this.programManagers.put(w, w.getPersistentStateManager().getOrCreate(
            nbt -> new ProgramManager(w, nbt),
            () -> new ProgramManager(w),
            MOD_ID + ":program_manager"
        ));
        iterator.remove();
      }
    }

    ProgramManager programManager = this.programManagers.get(world);
    // Log errors in chat and server console
    for (ProgramErrorReport report : programManager.executePrograms()) {
      MutableText message = null;
      // Add error elements
      for (ProgramErrorReportElement element : report.elements()) {
        MutableText t = Text.literal(element.exception().getClass().getSimpleName() + ": ")
            .append(Text.translatable(element.translationKey(), element.args()));
        if (message == null) {
          message = t;
        } else {
          message.append(Text.literal("\n")).append(t);
        }
        // Add call stack trace
        for (CallStackElement traceElement : element.callStack()) {
          // There should always be at least one ProgramErrorReportElement instance
          message.append(Text.literal(
              "\n  @ %s.%s [%d:%d]"
                  .formatted(traceElement.moduleName(), traceElement.scopeName(),
                      traceElement.line(), traceElement.column())
          ));
        }
      }
      if (message != null) {
        message.setStyle(Style.EMPTY.withColor(Formatting.RED));
      }
      if (world.getGameRules().getBoolean(GR_SHOW_ERROR_MESSAGES)) {
        final Text m = message;
        // Only show error messages to players that can use the /program command
        world.getPlayers(PlayerEntity::isCreativeLevelTwoOp)
            .forEach(player -> player.sendMessage(m, false));
      }
      world.getServer().sendMessage(message);
    }
  }
}
