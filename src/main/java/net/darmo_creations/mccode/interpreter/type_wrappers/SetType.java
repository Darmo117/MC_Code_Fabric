package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Method;
import net.darmo_creations.mccode.interpreter.annotations.ParameterMeta;
import net.darmo_creations.mccode.interpreter.annotations.ReturnMeta;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.UnsupportedOperatorException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.CompoundTagListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;
import net.darmo_creations.mccode.interpreter.types.MCSet;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Wrapper type for {@link MCSet} class.
 * <p>
 * Sets are iterable.
 */
@Type(name = SetType.NAME,
    doc = """
        A set is an unordered collection of unique values.
        Sets can be iterated over in 'for' loops and are accepted by the %len function.
        The order in which elements are iterated over is not guaranteed.
        When evaluated as a `boolean, an empty set yields #false while non-empty sets yield #true.""")
public class SetType extends TypeBase<MCSet> {
  public static final String NAME = "set";

  private static final String VALUES_KEY = "Values";

  @Override
  public Class<MCSet> getWrappedType() {
    return MCSet.class;
  }

  @Method(name = "clear", doc = "Removes all values from a `set. Modifies the `set.")
  public Void clear(final Scope scope, final MCSet self) {
    self.clear();
    return null;
  }

  @Method(name = "add",
      parametersMetadata = {
          @ParameterMeta(name = "value", mayBeNull = true, doc = "The value to add to the `set.")
      },
      doc = "Adds a value to a `set. Modifies the `set.")
  public Void add(final Scope scope, final MCSet self, final Object value) {
    self.add(ProgramManager.getTypeForValue(value).copy(scope, value));
    return null;
  }

  @Method(name = "is_disjoint",
      parametersMetadata = {
          @ParameterMeta(name = "s", doc = "A `set.")
      },
      returnTypeMetadata = @ReturnMeta(doc = "#True if both sets share no elements, false if they have at least one in common."),
      doc = "Checks whether this `set and the provided one have no elements in common.")
  public Boolean isDisjoint(final Scope scope, final MCSet self, final MCSet other) {
    return self.parallelStream().noneMatch(other::contains);
  }

  @Method(name = "union",
      parametersMetadata = {
          @ParameterMeta(name = "s", doc = "The `set perform the union with.")
      },
      doc = "Performs the union of values of two `set objects, i.e. adds all values of the provided `set into this one." +
          " This is strictly equivalent to 'this |= s;'." +
          " All elements of the provided `set will be copied before being inserted.")
  public Void union(final Scope scope, final MCSet self, final MCSet other) {
    this.__ior__(scope, self, other, false);
    return null;
  }

  @Method(name = "intersection",
      parametersMetadata = {
          @ParameterMeta(name = "s", doc = "The `set to perform the intersection with.")
      },
      doc = "Performs the intersection of values of two `set objects," +
          " This is strictly equivalent to 'this &= s;'." +
          " i.e. removes all values from this `set that are not contained in the provided `set.")
  public Void intersection(final Scope scope, final MCSet self, final MCSet other) {
    this.__iand__(scope, self, other, true);
    return null;
  }

  @Method(name = "difference",
      parametersMetadata = {
          @ParameterMeta(name = "s", doc = "The `set to perform the difference with.")
      },
      doc = "Performs the difference of values of two `set objects," +
          " This is strictly equivalent to 'this -= s;'." +
          " i.e. removes all values from this `set that are contained in the provided `set.")
  public Void difference(final Scope scope, final MCSet self, final MCSet other) {
    this.__sub__(scope, self, other, true);
    return null;
  }

  @Method(name = "symmetric_difference",
      parametersMetadata = {
          @ParameterMeta(name = "s", doc = "The `set to perform the symetric difference with.")
      },
      doc = "Performs the symetric difference of values of two `set objects," +
          " i.e. retains all values that are contained in both this `set and the provided one." +
          " This is strictly equivalent to 'this ^= s;' or 'this := (this | s) - (this & s);'" +
          " (note: the latter may be less memory efficient as intermediate copies of this set may be made)." +
          " All elements of the provided `set will be copied before being inserted.")
  public Void symmetricDifference(final Scope scope, final MCSet self, final MCSet other) {
    this.__ixor__(scope, self, other, true);
    return null;
  }

  /**
   * Remove all elements of o from self.
   */
  @Override
  protected Object __sub__(final Scope scope, MCSet self, final Object o, final boolean inPlace) {
    if (o instanceof MCSet s) {
      if (inPlace) {
        return this.sub(self, s);
      }
      return this.sub(new MCSet(self), s);
    }
    return super.__sub__(scope, self, o, inPlace);
  }

  private MCSet sub(MCSet set1, final MCSet set2) {
    set1.removeAll(set2);
    return set1;
  }

  /**
   * Keep all elements present in both o and self.
   */
  @Override
  protected Object __iand__(final Scope scope, MCSet self, final Object o, boolean inPlace) {
    if (o instanceof MCSet s) {
      if (inPlace) {
        return this.iand(self, s);
      }
      return this.iand(new MCSet(self), s);
    }
    return super.__iand__(scope, self, o, inPlace);
  }

  private MCSet iand(MCSet set1, final MCSet set2) {
    set1.retainAll(set2);
    return set1;
  }

  /**
   * Add all elements of o in self.
   */
  @Override
  protected Object __ior__(final Scope scope, MCSet self, final Object o, boolean inPlace) {
    if (o instanceof MCSet s) {
      if (inPlace) {
        return this.ior(scope, self, s, true);
      }
      return this.ior(scope, new MCSet(self), s, false);
    }
    return super.__ior__(scope, self, o, inPlace);
  }

  private MCSet ior(final Scope scope, MCSet set1, final MCSet set2, final boolean inPlace) {
    // Deep copy all elements to add
    if (!inPlace) {
      MCSet temp = this.__copy__(scope, set1);
      set1.clear();
      set1.addAll(temp);
    }
    set1.addAll(this.__copy__(scope, set2));
    return set1;
  }

  /**
   * Keep all elements of o and self that are not in both sets.
   */
  @Override
  protected Object __ixor__(final Scope scope, MCSet self, final Object o, boolean inPlace) {
    if (o instanceof MCSet s) {
      if (inPlace) {
        return this.ixor(scope, self, s, true);
      }
      return this.ixor(scope, new MCSet(self), s, false);
    }
    return super.__ixor__(scope, self, o, inPlace);
  }

  private MCSet ixor(final Scope scope, MCSet set1, final MCSet set2, final boolean inPlace) {
    // set1 ^ set2 == (set1 | set2) - (set1 & set2)
    // Compute intersection before call to ior() as it may modify set1
    MCSet intersection = this.iand(new MCSet(set1), set2);
    return this.sub(this.ior(scope, set1, set2, inPlace), intersection);
  }

  @Override
  protected Object __eq__(final Scope scope, final MCSet self, final Object o) {
    if (o instanceof MCSet s) {
      return self.equals(s);
    }
    return false;
  }

  /**
   * Test whether self is a proper superset of o, that is, self >= o and self != o.
   *
   * @return True if self is a proper superset of o, false otherwise.
   */
  @Override
  protected Object __gt__(final Scope scope, final MCSet self, final Object o) {
    return (Boolean) this.__ge__(scope, self, o) && !(Boolean) this.__eq__(scope, self, o);
  }

  /**
   * Test whether every element in o is in self.
   *
   * @return True if all elements in o are in self, false otherwise.
   */
  @Override
  protected Object __ge__(final Scope scope, final MCSet self, final Object o) {
    if (o instanceof MCSet s) {
      return self.containsAll(s);
    }
    throw new UnsupportedOperatorException(scope, BinaryOperator.GE, this, ProgramManager.getTypeForValue(o));
  }

  /**
   * Test whether self is a proper subset of o, that is, self <= o and self != o.
   *
   * @return True if self is a proper subset of o, false otherwise.
   */
  @Override
  protected Object __lt__(final Scope scope, final MCSet self, final Object o) {
    return (Boolean) this.__le__(scope, self, o) && !(Boolean) this.__eq__(scope, self, o);
  }

  /**
   * Test whether every element in self is in o.
   *
   * @return True if all elements in self are in o, false otherwise.
   */
  @Override
  protected Object __le__(final Scope scope, final MCSet self, final Object o) {
    if (o instanceof MCSet s) {
      return s.containsAll(self);
    }
    throw new UnsupportedOperatorException(scope, BinaryOperator.LE, this, ProgramManager.getTypeForValue(o));
  }

  @Override
  protected Object __in__(final Scope scope, final MCSet self, final Object o) {
    return self.contains(o);
  }

  @Override
  protected boolean __bool__(final MCSet self) {
    return !self.isEmpty();
  }

  @Override
  protected Iterator<?> __iter__(final Scope scope, final MCSet self) {
    return self.iterator();
  }

  @Override
  protected MCSet __copy__(final Scope scope, final MCSet self) {
    return new MCSet(self.stream().map(e -> ProgramManager.getTypeForValue(e).copy(scope, e)).collect(Collectors.toSet()));
  }

  @Override
  protected long __len__(final Scope scope, final MCSet self) {
    return self.size();
  }

  @Override
  public MCSet explicitCast(final Scope scope, final Object o) {
    if (o instanceof Collection<?> c) {
      return this.__copy__(scope, new MCSet(c));
    } else if (o instanceof Iterable<?> i) {
      MCSet list = new MCSet();
      for (Object e : i) {
        list.add(ProgramManager.getTypeForValue(e).copy(scope, e));
      }
      return list;
    } else if (o instanceof Map<?, ?> m) {
      return this.__copy__(scope, new MCSet(m.keySet()));
    } else if (o instanceof String s) {
      MCSet set = new MCSet();
      for (int i = 0; i < s.length(); i++) {
        set.add(String.valueOf(s.charAt(i)));
      }
      return set;
    }
    return super.explicitCast(scope, o);
  }

  @Override
  protected CompoundTag _writeToTag(final MCSet self) {
    CompoundTag tag = super._writeToTag(self);
    CompoundTagListTag list = new CompoundTagListTag();
    self.forEach(v -> list.add(ProgramManager.getTypeForValue(v).writeToTag(v)));
    tag.putTag(VALUES_KEY, list);
    return tag;
  }

  @Override
  public MCSet readFromTag(final Scope scope, final CompoundTag tag) {
    MCSet set = new MCSet();
    CompoundTagListTag tagsList = tag.getList(VALUES_KEY, TagType.COMPOUND_TAG_TYPE);
    tagsList.forEach(t -> set.add(ProgramManager.getTypeForName(t.getString(TypeBase.NAME_KEY)).readFromTag(scope, t)));
    return set;
  }
}
