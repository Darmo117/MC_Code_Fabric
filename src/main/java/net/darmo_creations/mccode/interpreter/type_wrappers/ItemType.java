package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Property;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/**
 * Wrapper type for {@link Item} class.
 * <p>
 * Implements __eq__ operator. Can explicitly cast {@link String}s and {@link Block}s.
 */
@Type(name = ItemType.NAME, doc = "Represents a Minecraft item.")
public class ItemType extends TypeBase<Item> {
  public static final String NAME = "item";

  private static final String ID_KEY = "ID";

  @Override
  public Class<Item> getWrappedType() {
    return Item.class;
  }

  @Property(name = "id", doc = "The ID of the `item.")
  public String getID(final Item self) {
    return Registry.ITEM.getKey(self).map(i -> i.getValue().toString()).orElse(null);
  }

  @Property(name = "max_stack_size", doc = "The max stack size of the `item.")
  public Long getMaxStackSize(final Item self) {
    return (long) self.getMaxCount();
  }

  @Property(name = "max_damage", doc = "The max damage of the `item.")
  public Long getMaxDamage(final Item self) {
    return (long) self.getMaxDamage();
  }

  @Override
  protected Object __eq__(final Scope scope, final Item self, final Object o) {
    if (o instanceof Item i) {
      return this.getID(self).equals(this.getID(i));
    }
    return false;
  }

  @Override
  protected String __str__(final Item self) {
    return this.getID(self);
  }

  @Override
  public Item explicitCast(final Scope scope, final Object o) throws MCCodeRuntimeException {
    if (o instanceof String s) {
      Identifier id = new Identifier(s);
      if (!Registry.ITEM.containsId(id)) {
        return super.explicitCast(scope, o);
      }
      return Registry.ITEM.get(id);
    } else if (o instanceof Block b) {
      Item itemBlock = Item.BLOCK_ITEMS.get(b);
      if (itemBlock == Items.AIR) {
        return null;
      }
      return itemBlock;
    }
    return super.explicitCast(scope, o);
  }

  @Override
  public CompoundTag _writeToTag(final Item self) {
    CompoundTag tag = super._writeToTag(self);
    tag.putString(ID_KEY, this.getID(self));
    return tag;
  }

  @Override
  public Item readFromTag(final Scope scope, final CompoundTag tag) {
    return Registry.ITEM.get(new Identifier(tag.getString(ID_KEY)));
  }
}
