package net.darmo_creations.mccode.interpreter.types;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;

// TODO useless?
/**
 * A wrapper type for Minecraft’s {@link World} class.
 */
public record WorldProxy(ServerWorld world) {
}
