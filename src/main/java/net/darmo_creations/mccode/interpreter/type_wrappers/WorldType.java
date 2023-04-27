package net.darmo_creations.mccode.interpreter.type_wrappers;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.darmo_creations.mccode.MCCode;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.annotations.*;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.types.MCList;
import net.darmo_creations.mccode.interpreter.types.MCMap;
import net.darmo_creations.mccode.interpreter.types.MCSet;
import net.darmo_creations.mccode.interpreter.types.Position;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.BlockDataObject;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.NbtPathArgumentType;
import net.minecraft.command.argument.RegistryPredicateArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.*;
import net.minecraft.network.message.SignedCommandArguments;
import net.minecraft.predicate.NbtPredicate;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralTextContent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.thread.FutureQueue;
import net.minecraft.world.GameRules;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Wrapper type for {@link ServerWorld} class.
 * <p>
 * It does not have a cast operator.
 */
@Type(name = WorldType.NAME,
    generateCastOperator = false,
    doc = "Represents the world dimension (overworld, nether, end, etc.) a script is running in.\n" +
        "It is the object through which scripts can interact with blocks, entities, etc.")
public class WorldType extends TypeBase<ServerWorld> {
  public static final String NAME = "world";

  private static final String DIMENSION_ID_KEY = "DimensionID";

  private static final DynamicCommandExceptionType STRUCTURE_INVALID_EXCEPTION =
      new DynamicCommandExceptionType(id -> Text.translatable("commands.locate.structure.invalid", id));
  private static final DynamicCommandExceptionType BIOME_INVALID_EXCEPTION =
      new DynamicCommandExceptionType(id -> Text.translatable("commands.locate.biome.invalid", id));

  @Override
  public Class<ServerWorld> getWrappedType() {
    return ServerWorld.class;
  }

  @Override
  protected List<String> getAdditionalPropertiesNames(final ServerWorld self) {
    List<String> gamerules = new ArrayList<>();
    GameRules.accept(new GameRules.Visitor() {
      @Override
      public <T extends GameRules.Rule<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
        gamerules.add("gr_" + key.getName());
      }
    });
    return gamerules;
  }

  @Override
  protected Object __get_property__(final Scope scope, final ServerWorld self, final String propertyName) {
    // Hack to get the value out of the anonymous class
    List<Object> container = new ArrayList<>();
    GameRules.accept(new GameRules.Visitor() {
      @Override
      public <T extends GameRules.Rule<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
        if (("gr_" + key.getName()).equals(propertyName)) {
          T value = self.getServer().getGameRules().get(key);
          if (value instanceof GameRules.BooleanRule b) {
            container.add(b.get());
          } else if (value instanceof GameRules.IntRule i) {
            container.add(i.get());
          } else {
            container.add(value.serialize());
          }
        }
      }
    });
    if (!container.isEmpty()) {
      return container.get(0);
    }

    return super.__get_property__(scope, self, propertyName);
  }

  @Override
  protected void __set_property__(final Scope scope, ServerWorld self, final String propertyName, final Object value) {
    MinecraftServer server = self.getServer();
    // Hack to get the value out of the anonymous class
    List<Boolean> container = new ArrayList<>();

    GameRules.accept(new GameRules.Visitor() {
      @Override
      public <T extends GameRules.Rule<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
        if (("gr_" + key.getName()).equals(propertyName)) {
          GameRules.Rule<T> v = server.getGameRules().get(key);
          if (v instanceof GameRules.BooleanRule b) {
            b.set(ProgramManager.getTypeInstance(BooleanType.class).implicitCast(scope, value), server);
          } else if (v instanceof GameRules.IntRule i) {
            i.set(ProgramManager.getTypeInstance(IntType.class).implicitCast(scope, value).intValue(), server);
          } else {
            //noinspection unchecked
            v.setValue((T) value, server);
          }
          container.add(true);
        }
      }
    });

    if (container.isEmpty()) {
      super.__set_property__(scope, self, propertyName, value);
    }
  }

  @Property(name = "dimension", doc = "Name of the dimension this world object represents.")
  public String getDimensionName(final ServerWorld self) {
    return Utils.getDimension(self);
  }

  @Property(name = "dimension_type", doc = "Name of the dimension type this world object represents.")
  public String getDimensionType(final ServerWorld self) {
    return Utils.getDimensionType(self);
  }

  @Property(name = "seed", doc = "The seed of the world.")
  public Long getSeed(final ServerWorld self) {
    return self.getSeed();
  }

  @Property(name = "day", doc = "The current day of the world.")
  public Long getWorldDay(final ServerWorld self) {
    return self.getTimeOfDay() / 24000 % 0x7fffffff;
  }

  @Property(name = "day_time", doc = "The current time of day of the world.")
  public Long getWorldDayTime(final ServerWorld self) {
    return self.getTimeOfDay() % 24000;
  }

  @Property(name = "game_time", doc = "The current game time (number of ticks) of the world.")
  public Long getWorldTick(final ServerWorld self) {
    return self.getTime() % 0x7fffffff;
  }

  /*
   * Advancement-related
   */

  @Method(name = "players_have_advancement",
      parametersMetadata = {
          @ParameterMeta(name = "targets", doc = "An entity selector that targets players."),
          @ParameterMeta(name = "advancement", doc = "The advancement to check."),
          @ParameterMeta(name = "criterion", mayBeNull = true, doc = "An optional criterion for the advancement.")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "#True if all the targetted players have the specified advancement, #false otherwise, #null if an error occured."),
      doc = "Returns whether the selected players have the given advancement.")
  public Boolean hasAdvancement(final Scope scope, ServerWorld self, final String targetSelector, final String advancement, final String criterion) {
    if (advancement == null) {
      return null;
    }
    Advancement a = self.getServer().getAdvancementLoader().get(new Identifier(advancement));
    if (a == null || criterion != null && !a.getCriteria().containsKey(criterion)) {
      return null;
    }
    List<? extends Entity> selectedEntities = getSelectedEntities(self, targetSelector);
    if (selectedEntities == null) {
      return null;
    }
    return selectedEntities.stream()
        .filter(entity -> entity instanceof ServerPlayerEntity)
        .map(entity -> (ServerPlayerEntity) entity)
        .allMatch(entity -> {
          AdvancementProgress advancementProgress = entity.getAdvancementTracker().getProgress(a);
          if (criterion == null) {
            return advancementProgress.isDone();
          }
          //noinspection ConstantConditions
          return advancementProgress.getCriterionProgress(criterion).isObtained();
        });
  }

  /*
   * NBT-related
   */

  @Method(name = "get_entity_data",
      parametersMetadata = {
          @ParameterMeta(name = "target", doc = "An entity selector that targets a single entity."),
          @ParameterMeta(name = "target_nbt_path", mayBeNull = true, doc = "The path to the data to query. If #null, all data is returned.")
      },
      returnTypeMetadata = @ReturnMeta(
          doc = "The queried data or #null if none or more than one entity were targetted or the specified path is invalid.",
          mayBeNull = true),
      doc = "Returns NBT data from the targetted entity.")
  public MCList getEntityData(final Scope scope, ServerWorld self, final String target, final String targetNBTPath) {
    List<? extends Entity> entities = getSelectedEntities(self, target);
    if (entities == null || entities.size() != 1) {
      return null;
    }
    return this.getData(targetNBTPath, NbtPredicate.entityToNbt(entities.get(0)));
  }

  @Method(name = "get_block_entity_data",
      parametersMetadata = {
          @ParameterMeta(name = "position", doc = "A block position."),
          @ParameterMeta(name = "target_nbt_path", mayBeNull = true, doc = "The path to the data to query. If #null, all data is returned.")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "The queried data or #null if the targetted block does not have a block entity or the specified path is invalid."),
      doc = "Returns NBT data from the block entity at the given position.")
  public MCList getBlockEntityData(final Scope scope, ServerWorld self, final Position position, final String targetNBTPath) {
    BlockPos pos = position.toBlockPos();
    BlockEntity blockEntity = self.getBlockEntity(pos);
    if (blockEntity == null) {
      return null;
    }
    NbtCompound nbt = new BlockDataObject(blockEntity, pos).getNbt();
    return this.getData(targetNBTPath, nbt);
  }

  @Method(name = "get_storage_data",
      parametersMetadata = {
          @ParameterMeta(name = "identifier", doc = "A storage identifier."),
          @ParameterMeta(name = "target_nbt_path", mayBeNull = true, doc = "The path to the data to query. If #null, all data is returned.")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "The queried data or #null if the specified storage does not exist or the specified path is invalid."),
      doc = "Returns NBT data from the specified storage.")
  public MCList getStorageData(final Scope scope, ServerWorld self, final String identifier, final String targetNBTPath) {
    NbtCompound nbt = self.getServer().getDataCommandStorage().get(new Identifier(identifier));
    return this.getData(targetNBTPath, nbt);
  }

  /**
   * Extract NBT data from a tag using the given path.
   */
  private MCList getData(final String targetNBTPath, final NbtCompound nbt) {
    if (nbt == null) {
      return null;
    }
    NbtPathArgumentType.NbtPath path;
    try {
      path = NbtPathArgumentType.nbtPath().parse(new StringReader(targetNBTPath));
    } catch (CommandSyntaxException e) {
      return null;
    }
    List<NbtElement> nbtElements;
    try {
      nbtElements = path.get(nbt);
    } catch (CommandSyntaxException e) {
      return null;
    }
    return new MCList(nbtElements.stream().map(nbtElement -> {
      // Dummy container to be able to invoke nbtTagToMap() method
      NbtCompound container = new NbtCompound();
      container.put("_", nbtElement);
      return nbtTagToMap(container).get("_");
    }).toList());
  }

  /*
   * Datapack-related
   */

  @Method(name = "list_all_datapacks",
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "The `list of names of all available and/or enabled datapacks, #null if an error occured."),
      doc = "Returns the `list of names of all available and/or enabled datapacks.")
  public MCList listAllDatapacks(final Scope scope, ServerWorld self) {
    MCList list = listAvailablePacks(scope);
    list.addAll(listEnabledPacks(scope));
    return list;
  }

  @Method(name = "list_available_datapacks",
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "The `list of names of all available datapacks excluding those enabled, #null if an error occured."),
      doc = "Returns the `list of names of all available but non-enabled datapacks.")
  public MCList listAvailableDatapacks(final Scope scope, ServerWorld self) {
    return listAvailablePacks(scope);
  }

  @Method(name = "list_enabled_datapacks",
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "The `list of names of all enabled datapacks, #null if an error occured."),
      doc = "Returns the `list of names of all enabled datapacks.")
  public MCList listEnabledDatapacks(final Scope scope, ServerWorld self) {
    return listEnabledPacks(scope);
  }

  private static MCList listAvailablePacks(final Scope scope) {
    ResourcePackManager packrepository = scope.getProgram().getProgramManager().getWorld().getServer().getDataPackManager();
    Collection<String> selectedPacks = packrepository.getEnabledNames().stream().toList();
    Collection<String> availablePacks = packrepository.getNames().stream().toList();
    return new MCList(availablePacks.stream().filter(pack -> !selectedPacks.contains(pack)).collect(Collectors.toList()));
  }

  private static MCList listEnabledPacks(final Scope scope) {
    ResourcePackManager packrepository = scope.getProgram().getProgramManager().getWorld().getServer().getDataPackManager();
    return new MCList(packrepository.getEnabledNames().stream().toList());
  }

  @Method(name = "reload_datapacks", doc = "Reloads all datapacks.")
  public Void reloadDataPacks(final Scope scope, ServerWorld self) {
    executeCommand(scope, self, "reload");
    return null;
  }

  /*
   * Chunk-related
   */

  @Method(name = "get_force_loaded_chunks",
      returnTypeMetadata = @ReturnMeta(doc = "The `list of force loaded chunks’ positions."),
      doc = "Queries all force loaded chunks.")
  public MCList getForceLoadedChunks(final Scope scope, ServerWorld self) {
    ServerWorld serverlevel = scope.getProgram().getProgramManager().getWorld();
    LongSet longset = serverlevel.getForcedChunks();
    return new MCList(longset.longStream().sorted().mapToObj(l -> {
      ChunkPos p = new ChunkPos(l);
      return new Position(p.x, 0, p.z);
    }).collect(Collectors.toList()));
  }

  @Method(name = "is_chunk_force_loaded",
      parametersMetadata = {
          @ParameterMeta(name = "p", doc = "A block position inside the chunk to test.")
      },
      returnTypeMetadata = @ReturnMeta(doc = "#True if the chunk is force loaded, #false otherwise."),
      doc = "Checks whether the chunk at the given position is force loaded.")
  public Boolean isChunkForceLoaded(final Scope scope, ServerWorld self, final Position position) {
    ChunkPos chunkpos = new ChunkPos(position.toBlockPos());
    ServerWorld serverlevel = scope.getProgram().getProgramManager().getWorld();
    return serverlevel.getForcedChunks().contains(chunkpos.toLong());
  }

  /*
   * Block-related
   */

  @Method(name = "get_block",
      parametersMetadata = {
          @ParameterMeta(name = "p", doc = "Position of the block.")
      },
      returnTypeMetadata = @ReturnMeta(doc = "The `block at the given position."),
      doc = "Returns the `block at the given position.")
  public Block getBlock(final Scope scope, final ServerWorld self, final Position position) {
    return self.getBlockState(position.toBlockPos()).getBlock();
  }

  @Method(name = "get_block_state",
      parametersMetadata = {
          @ParameterMeta(name = "p", doc = "Position of the block.")
      },
      returnTypeMetadata = @ReturnMeta(doc = "A `map containing all properties of the block."),
      doc = "Returns the block state at the given position in this world.")
  public MCMap getBlockState(final Scope scope, final ServerWorld self, final Position position) {
    BlockState blockState = self.getBlockState(position.toBlockPos());
    return new MCMap(blockState.getProperties().stream().collect(Collectors.toMap(
        net.minecraft.state.property.Property::getName,
        p -> {
          Comparable<?> value = blockState.get(p);
          if (value instanceof Enum || value instanceof Character) {
            return value.toString();
          } else if (value instanceof Byte || value instanceof Short || value instanceof Integer) {
            return ((Number) value).longValue();
          } else if (value instanceof Float f) {
            return f.doubleValue();
          }
          return value;
        }
    )));
  }

  @Method(name = "is_block_loaded",
      parametersMetadata = {
          @ParameterMeta(name = "p", doc = "Position of the block to check.")
      },
      returnTypeMetadata = @ReturnMeta(doc = "#True if the block is loaded, #false otherwise."),
      doc = "Returns whether the `block at the given position is currently loaded.")
  public Boolean isBlockLoaded(final Scope scope, final ServerWorld self, final Position position) {
    //noinspection deprecation
    return self.isChunkLoaded(position.toBlockPos());
  }

  /*
   * Player-related
   */

  @Method(name = "get_players_list",
      returnTypeMetadata = @ReturnMeta(doc = "A `map containing data for all connected players."),
      doc = "Fetches profile data for all connected players.")
  public MCMap getPlayersList(final Scope scope, final ServerWorld self) {
    return new MCMap(self.getServer().getPlayerManager().getPlayerList()
        .stream()
        .collect(Collectors.toMap(
            p -> p.getGameProfile().getId().toString(),
            p -> {
              MCMap properties = new MCMap();
              GameProfile gameProfile = p.getGameProfile();
              properties.put("name", gameProfile.getName());
              for (Map.Entry<String, com.mojang.authlib.properties.Property> entry : gameProfile.getProperties().entries()) {
                properties.put(entry.getKey(), entry.getValue().getValue());
              }
              return properties;
            }
        )));
  }

  /*
   * Scoreboard-related
   */

  @Method(name = "get_scoreboard_objectives",
      returnTypeMetadata = @ReturnMeta(doc = "A `list of `map objects that each contain data of a single objective."),
      doc = """
          Returns the list of defined scoreboard objectives.
          Structure:
          §lname§r: Objective’s name.
          §ldisplay_name§r: Objective’s display name.
          §lrender_type§r: How the objective is rendered: either “hearts” or “integer”.
          §lcriteria§r: Objective’s criteria.
          §lread_only§r: #True if the objective is read-only, #false otherwise.
          """)
  public MCList getScoreboardObjectives(final Scope scope, ServerWorld self) {
    return new MCList(self.getScoreboard().getObjectives().stream().map(obj -> {
      MCMap map = new MCMap();
      map.put("name", obj.getName());
      map.put("display_name", obj.getDisplayName());
      map.put("render_type", obj.getRenderType().getName());
      map.put("criteria", obj.getCriterion().getName());
      map.put("read_only", obj.getCriterion().isReadOnly());
      return map;
    }).collect(Collectors.toList()));
  }

  @Method(name = "get_players_tracked_by_scoreboard",
      returnTypeMetadata = @ReturnMeta(doc = "A sorted `list of the names of all players tracked by the scoreboard."),
      doc = "Returns the names of all players tracked by the scoreboard.")
  public MCList getPlayersInScoreboard(final Scope scope, ServerWorld self) {
    List<String> list = new LinkedList<>(self.getScoreboard().getObjectiveNames());
    list.sort(String::compareToIgnoreCase);
    return new MCList(list);
  }

  @Method(name = "get_player_scores",
      parametersMetadata = {
          @ParameterMeta(name = "player_name", doc = "The name of the player.")
      },
      returnTypeMetadata = @ReturnMeta(doc = "A `map containing the scores of each objective."),
      doc = "Returns the scoreboard scores for the given player.")
  public MCMap getPlayerScores(final Scope scope, ServerWorld self, final String name) {
    ScoreboardObjective objective = self.getScoreboard().getObjective(name);
    Collection<ScoreboardPlayerScore> objectives = self.getScoreboard().getAllPlayerScores(objective);
    //noinspection ConstantConditions
    return new MCMap(objectives.stream().collect(Collectors.toMap(
        e -> e.getObjective().getName(),
        ScoreboardPlayerScore::getScore
    )));
  }

  @Method(name = "get_scoreboard_tags_for_players",
      parametersMetadata = {
          @ParameterMeta(name = "targets", doc = "An entity selector that targets players."),
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "A `map that contains the tags of each targetted player or #null if an error occurs. " +
              "Keys correspond to player entities IDs."),
      doc = "Returns the tags of selected players.")
  public MCMap getPlayersScoreboardTags(final Scope scope, ServerWorld self, final String targetSelector) {
    List<? extends Entity> entities = getSelectedEntities(self, targetSelector);
    if (entities == null) {
      return null;
    }
    MCMap tags = new MCMap();
    for (Entity entity : entities) {
      tags.put(entity.getId() + "", new MCSet(entity.getScoreboardTags()));
    }
    return tags;
  }

  @Method(name = "is_players_score_within_range",
      parametersMetadata = {
          @ParameterMeta(name = "targets", doc = "An entity selector that targets players."),
          @ParameterMeta(name = "objective", doc = "Name of the objective to check."),
          @ParameterMeta(name = "min", doc = "Lower bound of the range (inclusive)."),
          @ParameterMeta(name = "max", doc = "Upper bound of the range (inclusive).")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "#True if the score of all targetted players is within the range, #false otherwise, #null if an error occured."),
      doc = "Checks whether the score of the selected players is within the given range.")
  public Boolean isPlayersScoreWithinRange(final Scope scope, ServerWorld self, final String targetSelector,
                                           final String objective, final Long min, final Long max) {
    List<ServerPlayerEntity> selectedPlayers = Utils.getSelectedPlayers(self, targetSelector);
    if (selectedPlayers == null) {
      return null;
    }
    ServerScoreboard scoreboard = self.getScoreboard();
    ScoreboardObjective obj = scoreboard.getNullableObjective(objective);
    if (obj == null) {
      return false;
    }
    return selectedPlayers.stream().allMatch(player -> {
      int score = scoreboard.getPlayerScore(player.getGameProfile().getName(), obj).getScore();
      return min <= score && score <= max;
    });
  }

  /*
   * Entity-related.
   */

  @Method(name = "entities_match",
      parametersMetadata = {
          @ParameterMeta(name = "targets", doc = "An entity selector.")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true),
      doc = "Returns the number of entities that matched the selector or #null if an error occured.")
  public Long entitiesMatch(final Scope scope, ServerWorld self, final String targetSelector) {
    return Optional.ofNullable(getSelectedEntities(self, targetSelector))
        .map(list -> (long) list.size()).orElse(null);
  }

  @Method(name = "get_entities_data",
      parametersMetadata = {
          @ParameterMeta(name = "targets", doc = "An entity selector.")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "A `list of `map objects that each contain the data of an entity that matched the selector or #null if an error occured."),
      doc = "Fetches the data of all entities that matched the given selector.")
  public MCList getEntitiesData(final Scope scope, final ServerWorld self, final String targetSelector) {
    List<? extends Entity> entities = getSelectedEntities(self, targetSelector);
    if (entities == null) {
      return null;
    }
    return new MCList(entities.stream().map(e -> nbtTagToMap(e.writeNbt(new NbtCompound()))).collect(Collectors.toList()));
  }

  /*
   * Locate things
   */

  @Method(name = "locate_structure",
      parametersMetadata = {
          @ParameterMeta(name = "structure_id", doc = "ID of the structure to find."),
          @ParameterMeta(name = "around", doc = "Position to look around of."),
          @ParameterMeta(name = "radius", doc = "Search radius around the position."),
          @ParameterMeta(name = "skip_already_discovered", doc = "Whether to skip structures that were previously discovered.")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "The position of the nearest structure of desired type or #null if an error occured."),
      doc = "Returns the coordinates of the closest structure around the given point.")
  public Position locateStructure(final Scope scope, final ServerWorld self, final String structureID,
                                  final Position around, final Long radius, final Boolean skipAlreadyDiscovered) {
    String command = "locate structure " + structureID;
    ServerCommandSource source = self.getServer().getCommandSource();
    ParseResults<ServerCommandSource> parseResults = self.getServer().getCommandManager().getDispatcher()
        .parse(command, source);
    CommandContext<ServerCommandSource> context = parseResults.getContext().build(command);
    RegistryPredicateArgumentType.RegistryPredicate<Structure> p;
    try {
      p = RegistryPredicateArgumentType.getPredicate(
          context,
          "structure",
          Registry.STRUCTURE_KEY,
          STRUCTURE_INVALID_EXCEPTION
      );
    } catch (CommandSyntaxException e) {
      return null;
    }
    Registry<Structure> registry = source.getWorld().getRegistryManager().get(Registry.STRUCTURE_KEY);
    RegistryEntryList<Structure> registryEntryList = getStructureListForPredicate(p, registry).orElseThrow(null);
    if (registryEntryList.size() == 0) {
      return null;
    }
    ServerWorld serverWorld = source.getWorld();
    Pair<BlockPos, RegistryEntry<Structure>> pair = serverWorld.getChunkManager().getChunkGenerator()
        .locateStructure(serverWorld, registryEntryList, around.toBlockPos(), radius.intValue(), skipAlreadyDiscovered);
    if (pair == null) {
      return null;
    }
    return new Position(pair.getFirst());
  }

  private static Optional<? extends RegistryEntryList.ListBacked<Structure>> getStructureListForPredicate(RegistryPredicateArgumentType.RegistryPredicate<Structure> predicate, Registry<Structure> structureRegistry) {
    return predicate.getKey().map(key -> structureRegistry.getEntry(key).map(RegistryEntryList::of), structureRegistry::getEntryList);
  }

  @Method(name = "locate_biome",
      parametersMetadata = {
          @ParameterMeta(name = "biome_id", doc = "ID of the biome to find."),
          @ParameterMeta(name = "around", doc = "Position to look around of."),
          @ParameterMeta(name = "radius", doc = "Search radius around the position.")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "A position in the nearest biome of desired type or #null if an error occured."),
      doc = "Returns the coordinates of the closest biome around the given point.")
  public Position locateBiome(final Scope scope, final ServerWorld self, final String biomeID,
                              final Position around, final Long radius) {
    String command = "locate biome " + biomeID;
    ParseResults<ServerCommandSource> parseResults = self.getServer().getCommandManager().getDispatcher()
        .parse(command, self.getServer().getCommandSource());
    RegistryPredicateArgumentType.RegistryPredicate<Biome> p;
    try {
      p = RegistryPredicateArgumentType.getPredicate(
          parseResults.getContext().build(command),
          "biome",
          Registry.BIOME_KEY,
          BIOME_INVALID_EXCEPTION
      );
    } catch (CommandSyntaxException e) {
      return null;
    }
    Pair<BlockPos, RegistryEntry<Biome>> entry = self.locateBiome(p, around.toBlockPos(), radius.intValue(), 8, 8);
    if (entry == null) {
      return null;
    }
    return new Position(entry.getFirst());
  }

  /*
   * Generic methods
   */

  @Method(name = "execute",
      parametersMetadata = {
          @ParameterMeta(name = "name", doc = "Name of the command to execute. Must not include the / character."),
          @ParameterMeta(name = "args", doc = "The command’s arguments. All values will be converted to strings.")
      },
      returnTypeMetadata = @ReturnMeta(mayBeNull = true,
          doc = "The result of the command or #null if an error occured."),
      doc = "Executes a command. See the Minecraft Wiki for more information. The /trigger command is not accepted and will raise an error ")
  public Long executeCommand(final Scope scope, ServerWorld self, final String name, final Object... args) {
    if ("trigger".equals(name)) {
      throw new MCCodeRuntimeException(scope, name, "mccode.interpreter.error.illegal_command", name);
    }
    return executeCommand(
        scope, self,
        name,
        Arrays.stream(args).map(o -> ProgramManager.getTypeForValue(o).toString(o)).toArray(String[]::new)
    ).orElse(null);
  }

  /*
   * Utility methods
   */

  private static Optional<Long> executeCommand(final Scope scope, ServerWorld self, final String commandName, final String... args) {
    MinecraftServer server = self.getServer();
    String command = commandName;
    if (args.length != 0) {
      command += " " + String.join(" ", args);
    }
    CommandSourceStackWrapper commandSourceStack = new CommandSourceStackWrapper(server, self);
    long result = server.getCommandManager().executeWithPrefix(commandSourceStack, command);
    if (result == 0 && commandSourceStack.anyFailures) {
      final String dimension = Utils.getDimension(scope.getProgram().getProgramManager().getWorld());
      final String cmd = command;
      commandSourceStack.errors.forEach(text -> {
        String prefix = "[Program %s in %s] world.execute(\"/%s\"): ".formatted(scope.getProgram().getName(), dimension, cmd);
        MCCode.LOGGER.warn(prefix + text.getString());
      });
      return Optional.empty();
    }
    return Optional.of(result);
  }

  /**
   * Returns a list of entities that match the given target selector or null if the selector is invalid.
   */
  private static List<? extends Entity> getSelectedEntities(final ServerWorld world, final String targetSelector) {
    try {
      EntitySelector selector = EntityArgumentType.entities().parse(new StringReader(targetSelector));
      return selector.getEntities(world.getServer().getCommandSource());
    } catch (CommandSyntaxException e) {
      return null;
    }
  }

  /**
   * Convert a {@link NbtCompound} to a map.
   */
  private static MCMap nbtTagToMap(final NbtCompound tag) {
    MCMap map = new MCMap();
    for (String key : tag.getKeys()) {
      Object value = deserializeNBTTag(tag.get(key));
      if (value != null) {
        map.put(key, value);
      }
    }
    return map;
  }

  /**
   * Convert a {@link NbtElement} to an MCCode-compatible object.
   */
  private static Object deserializeNBTTag(final NbtElement tag) {
    if (tag instanceof NbtByte || tag instanceof NbtShort || tag instanceof NbtInt || tag instanceof NbtLong) {
      return ((AbstractNbtNumber) tag).longValue();
    } else if (tag instanceof NbtFloat || tag instanceof NbtDouble) {
      return ((AbstractNbtNumber) tag).doubleValue();
    } else if (tag instanceof NbtString t) {
      return t.asString();
    } else if (tag instanceof NbtByteArray t) {
      MCList list = new MCList();
      for (byte b : t.getByteArray()) {
        list.add((long) b);
      }
      return list;
    } else if (tag instanceof NbtIntArray t) {
      MCList list = new MCList();
      Arrays.stream(t.getIntArray()).mapToObj(i -> (long) i).forEach(list::add);
      return list;
    } else if (tag instanceof NbtLongArray t) {
      MCList list = new MCList();
      Arrays.stream(t.getLongArray()).forEach(list::add);
      return list;
    } else if (tag instanceof NbtList t) {
      MCList list = new MCList();
      t.stream().map(WorldType::deserializeNBTTag).forEach(list::add);
      return list;
    } else if (tag instanceof NbtCompound t) {
      return nbtTagToMap(t);
    }
    return null;
  }

  /*
   * Inherited methods.
   */

  @Override
  protected String __str__(final ServerWorld self) {
    return this.getDimensionName(self);
  }

  @Override
  protected CompoundTag _writeToTag(final ServerWorld self) {
    CompoundTag tag = super._writeToTag(self);
    tag.putString(DIMENSION_ID_KEY, Utils.getDimension(self));
    return tag;
  }

  @Override
  public ServerWorld readFromTag(final Scope scope, final CompoundTag tag) {
    MinecraftServer server = scope.getProgram().getProgramManager().getWorld().getServer();
    return server.getWorld(RegistryKey.of(Registry.WORLD_KEY, new Identifier(tag.getString(DIMENSION_ID_KEY))));
  }

  /**
   * Custom source stack wrapper.
   */
  private static class CommandSourceStackWrapper extends ServerCommandSource {
    /**
     * The list of all errors.
     */
    final List<Text> errors;
    /**
     * Whether any failures occured while executing the last command.
     */
    boolean anyFailures;

    /**
     * Create a wrapper for a command source and level.
     * The new instance has a permission level of 2 in order to
     * prevent op commands from being executed from within scripts.
     */
    CommandSourceStackWrapper(CommandOutput source, ServerWorld world) {
      super(
          source,
          Vec3d.ofBottomCenter(world.getSpawnPos()),
          Vec2f.ZERO,
          world,
          2,
          "Server", MutableText.of(new LiteralTextContent("Server")),
          world.getServer(),
          null,
          true,
          (context, success, result) -> {
          },
          EntityAnchorArgumentType.EntityAnchor.FEET,
          SignedCommandArguments.EMPTY,
          FutureQueue.NOOP
      );
      this.errors = new LinkedList<>();
      this.anyFailures = false;
    }

    @Override
    public void sendError(Text message) {
      super.sendError(message);
      this.errors.add(message);
      this.anyFailures = true;
    }
  }
}
