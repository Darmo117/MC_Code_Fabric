package net.darmo_creations.mccode.commands.argument_types;

import net.darmo_creations.mccode.MCCode;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.util.Identifier;

public final class ModArgumentTypes {
  public static void registerAll() {
    ArgumentTypeRegistry.registerArgumentType(
        new Identifier(MCCode.MOD_ID, "program_name"),
        ProgramNameArgumentType.class,
        new ProgramNameArgumentType.Serializer()
    );
    ArgumentTypeRegistry.registerArgumentType(
        new Identifier(MCCode.MOD_ID, "program_element_name"),
        ProgramElementNameArgumentType.class,
        ConstantArgumentSerializer.of(ProgramElementNameArgumentType::create)
    );
    ArgumentTypeRegistry.registerArgumentType(
        new Identifier(MCCode.MOD_ID, "program_variable_name"),
        ProgramVariableNameArgumentType.class,
        new ProgramVariableNameArgumentType.Serializer()
    );
    ArgumentTypeRegistry.registerArgumentType(
        new Identifier(MCCode.MOD_ID, "program_doc_type"),
        ProgramDocTypeTypeArgument.class,
        ConstantArgumentSerializer.of(ProgramDocTypeTypeArgument::create)
    );
  }

  private ModArgumentTypes() {
  }
}
