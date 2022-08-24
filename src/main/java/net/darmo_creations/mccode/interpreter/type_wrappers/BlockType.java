package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Property;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Wrapper type for {@link Block} class.
 * <p>
 * New instances can be created by casting {@link String}s.
 */
@Type(name = BlockType.NAME, doc = "Type that represents a block.")
public class BlockType extends TypeBase<Block> {
  public static final String NAME = "block";

  private static final String ID_KEY = "ID";

  @Override
  public Class<Block> getWrappedType() {
    return Block.class;
  }

  @Property(name = "id", doc = "The ID of the `block.")
  public String getID(final Block self) {
    return Registry.BLOCK.getKey(self).map(i -> i.getValue().toString()).orElse(null);
  }

  @Override
  protected Object __add__(final Scope scope, final Block self, final Object o, boolean inPlace) {
    if (o instanceof String s) {
      return this.__str__(self) + s;
    }
    return super.__add__(scope, self, o, inPlace);
  }

  @Override
  protected Object __eq__(final Scope scope, final Block self, final Object o) {
    if (o instanceof Block b) {
      return this.getID(self).equals(this.getID(b));
    }
    return false;
  }

  @Override
  protected String __str__(final Block self) {
    return this.getID(self);
  }

  @Override
  public Block explicitCast(final Scope scope, final Object o) throws MCCodeRuntimeException {
    if (o instanceof String s) {
      Identifier id = new Identifier(s);
      if (!Registry.BLOCK.containsId(id)) {
        return super.explicitCast(scope, o);
      }
      return Registry.BLOCK.get(id);
    }
    return super.explicitCast(scope, o);
  }

  @Override
  public CompoundTag _writeToTag(final Block self) {
    CompoundTag tag = super._writeToTag(self);
    tag.putString(ID_KEY, this.getID(self));
    return tag;
  }

  @Override
  public Block readFromTag(final Scope scope, final CompoundTag tag) {
    return Registry.BLOCK.get(new Identifier(tag.getString(ID_KEY)));
  }
}
