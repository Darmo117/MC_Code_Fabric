package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Method;
import net.darmo_creations.mccode.interpreter.annotations.ParameterMeta;
import net.darmo_creations.mccode.interpreter.annotations.ReturnMeta;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.IndexOutOfBoundsException;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.CompoundTagListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;
import net.darmo_creations.mccode.interpreter.types.MCList;
import net.darmo_creations.mccode.interpreter.types.Range;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Wrapper type for {@link MCList} class.
 * <p>
 * Lists are iterable and support the __get_item__, __set_item__ and __del_item__ operators.
 */
@Type(name = ListType.NAME, doc = """
    A list is an ordered sequence of values.
    Lists can be iterated over in 'for' loops and are accepted by the %len function.
    When evaluated as a `boolean, an empty list yields #false while non-empty lists yield #true.""")
public class ListType extends TypeBase<MCList> {
  public static final String NAME = "list";

  private static final String VALUES_KEY = "Values";

  /**
   * Return a comparator to sort instances of this type.
   *
   * @param scope    A scope object to query types from.
   * @param reversed Whether to reverse sort the target list.
   * @return The comparator.
   */
  public static Comparator<Object> comparator(final Scope scope, final boolean reversed) {
    return (e1, e2) -> {
      TypeBase<?> e1Type = ProgramManager.getTypeForValue(e1);
      Object gt = e1Type.applyOperator(scope, BinaryOperator.GT, e1, e2, null, false);
      if (ProgramManager.getTypeForValue(gt).toBoolean(gt)) {
        return reversed ? -1 : 1;
      }
      Object equal = e1Type.applyOperator(scope, BinaryOperator.EQUAL, e1, e2, null, false);
      return ProgramManager.getTypeForValue(equal).toBoolean(equal) ? 0 : (reversed ? 1 : -1);
    };
  }

  @Override
  public Class<MCList> getWrappedType() {
    return MCList.class;
  }

  @Method(name = "clear", doc = "Removes all values from a `list.")
  public Void clear(final Scope scope, final MCList self) {
    self.clear();
    return null;
  }

  @Method(name = "add",
      parametersMetadata = {
          @ParameterMeta(name = "value", mayBeNull = true, doc = "The value to add to this `list.")
      },
      doc = "Adds a value at the end of this `list.")
  public Void add(final Scope scope, final MCList self, final Object value) {
    self.add(ProgramManager.getTypeForValue(value).copy(scope, value));
    return null;
  }

  @Method(name = "insert",
      parametersMetadata = {
          @ParameterMeta(name = "index", doc = "Index at which to insert the value."),
          @ParameterMeta(name = "value", mayBeNull = true, doc = "The value to insert into this `list.")
      },
      doc = "Adds a value at the specified index of this `list.")
  public Void insert(final Scope scope, final MCList self, final Long index, final Object value) {
    self.add(this.getIndex(scope, self, index), ProgramManager.getTypeForValue(value).copy(scope, value));
    return null;
  }

  @Method(name = "extend",
      parametersMetadata = {
          @ParameterMeta(name = "other", mayBeNull = true, doc = "The `list to append.")
      },
      doc = """
          Appends the values of the given `list to the end of this one. Modifies this `list.
          This is strictly equivalent to 'this += other;'.
          All elements of the provided `list will be copied before being inserted.""")
  public Void extend(final Scope scope, MCList self, final MCList other) {
    this.__add__(scope, self, other, true);
    return null;
  }

  @Method(name = "count",
      parametersMetadata = {
          @ParameterMeta(name = "value", mayBeNull = true, doc = "The value to count the occurences of.")
      },
      returnTypeMetadata = @ReturnMeta(doc = "The number of occurences of the value."),
      doc = "Counts the number of times the given value occurs in this `list.")
  public Long count(final Scope scope, final MCList self, final Object value) {
    return self.stream().filter(e -> e.equals(value)).count();
  }

  @Method(name = "index",
      parametersMetadata = {
          @ParameterMeta(name = "value", mayBeNull = true, doc = "The value to get the index of.")
      },
      returnTypeMetadata = @ReturnMeta(doc = "The index of the first occurence or -1 if the value was not found."),
      doc = "Returns the index of the first occurence of the given value in this `list." +
          " Returns -1 if the value is not present in this `list.")
  public Long indexOf(final Scope scope, final MCList self, final Object value) {
    return (long) self.indexOf(value);
  }

  @Method(name = "sort",
      parametersMetadata = {
          @ParameterMeta(name = "reversed", doc = "If #true this `list will be sorted in reverse order.")
      },
      doc = "Sorts this `list using natural ordering of its elements.")
  public Void sort(final Scope scope, final MCList self, final boolean reversed) {
    self.sort(comparator(scope, reversed));
    return null;
  }

  private int getIndex(final Scope scope, final MCList self, Long index) {
    if (index < -self.size() || index >= self.size()) {
      throw new IndexOutOfBoundsException(scope, index.intValue());
    }
    if (index < 0) {
      index = self.size() + index;
    }
    return index.intValue();
  }

  private Object getItem(final Scope scope, final MCList self, final Long index) {
    return self.get(this.getIndex(scope, self, index));
  }

  private void setItem(final Scope scope, MCList self, final Long index, final Object value) {
    self.set(this.getIndex(scope, self, index), value);
  }

  @Override
  protected Object __get_item__(final Scope scope, final MCList self, final Object key) {
    if (key instanceof Long || key instanceof Boolean) {
      long index = ProgramManager.getTypeForValue(key).toInt(key);
      return this.getItem(scope, self, index);
    } else if (key instanceof Range r) {
      MCList list = new MCList();
      r.iterator().forEachRemaining(i -> list.add(this.getItem(scope, self, i)));
      return list;
    }
    return super.__get_item__(scope, self, key);
  }

  @Override
  protected void __set_item__(final Scope scope, MCList self, final Object key, final Object value) {
    if (key instanceof Long || key instanceof Boolean) {
      long index = ProgramManager.getTypeForValue(key).toInt(key);
      this.setItem(scope, self, index, ProgramManager.getTypeForValue(value).copy(scope, value));
    } else if (key instanceof Range r) {
      MCList list = this.implicitCast(scope, value);
      if (list.size() != r.size()) {
        throw new MCCodeRuntimeException(scope, null, "mccode.interpreter.error.mismatch_list_size", r.size(), list.size());
      }
      int j = 0;
      for (long i : r) {
        Object o = list.get(j);
        this.setItem(scope, self, i, ProgramManager.getTypeForValue(o).copy(scope, o));
        j++;
      }
    } else {
      super.__set_item__(scope, self, key, value);
    }
  }

  @Override
  protected void __del_item__(final Scope scope, MCList self, final Object key) {
    if (key instanceof Long || key instanceof Boolean) {
      long index = ProgramManager.getTypeForValue(key).toInt(key);
      self.remove(this.getIndex(scope, self, index));
    } else if (key instanceof Range r) {
      StreamSupport.stream(r.spliterator(), false)
          .map(i -> this.getIndex(scope, self, i))
          .sorted((i1, i2) -> -i1.compareTo(i2))
          .forEach(i -> self.remove(i.intValue())); // intValue() is necessary to use List.remove(int) and not List.remove(Object)
    } else {
      super.__del_item__(scope, self, key);
    }
  }

  /**
   * Add all values of o to self.
   */
  @Override
  protected Object __add__(final Scope scope, MCList self, final Object o, final boolean inPlace) {
    if (o instanceof MCList l) {
      if (inPlace) {
        return this.add(scope, self, l, true);
      }
      return this.add(scope, new MCList(self), l, false);
    }
    return super.__add__(scope, self, o, inPlace);
  }

  private MCList add(final Scope scope, MCList list1, final MCList list2, final boolean inPlace) {
    // Deep copy all elements to add
    if (!inPlace) {
      MCList temp = this.__copy__(scope, list1);
      list1.clear();
      list1.addAll(temp);
    }
    list1.addAll(this.__copy__(scope, list2));
    return list1;
  }

  /**
   * Remove all values of o from self.
   */
  @Override
  protected Object __sub__(final Scope scope, MCList self, final Object o, final boolean inPlace) {
    if (o instanceof MCList l) {
      if (inPlace) {
        return this.sub(self, l);
      }
      return this.sub(new MCList(self), l);
    }
    return super.__sub__(scope, self, o, inPlace);
  }

  private MCList sub(MCList list1, final MCList list2) {
    list1.removeAll(list2);
    return list1;
  }

  /**
   * Duplicates this list o times.
   */
  @Override
  protected Object __mul__(final Scope scope, MCList self, final Object o, final boolean inPlace) {
    if (o instanceof Long || o instanceof Boolean) {
      long nb = ProgramManager.getTypeInstance(IntType.class).implicitCast(scope, o);
      if (inPlace) {
        return this.mul(scope, self, nb);
      }
      return this.mul(scope, new MCList(self), nb);
    }
    return super.__mul__(scope, self, o, inPlace);
  }

  @Override
  protected Object __rmul__(final Scope scope, final MCList self, final Object o) {
    return this.__mul__(scope, self, o, false);
  }

  private MCList mul(final Scope scope, MCList list, final long nb) {
    if (nb <= 0) {
      list.clear();
      return list;
    } else if (nb == 1) {
      return list;
    }
    MCList temp = this.__copy__(scope, list);
    list.clear();
    for (int i = 0; i < nb; i++) {
      // Deep copy all elements to add
      list.addAll(this.__copy__(scope, temp));
    }
    return list;
  }

  @Override
  protected Object __eq__(final Scope scope, final MCList self, final Object o) {
    if (o instanceof MCList l) {
      return self.equals(l);
    }
    return false;
  }

  @Override
  protected Object __gt__(final Scope scope, final MCList self, final Object o) {
    if (o instanceof MCList other) {
      if (self.size() > other.size()) {
        return true;
      }
      if (self.size() < other.size()) {
        return false;
      }
      for (int i = 0; i < self.size(); i++) {
        Object selfItem = self.get(i);
        Object equal = ProgramManager.getTypeForValue(selfItem).applyOperator(scope, BinaryOperator.EQUAL, selfItem, other.get(i), null, false);
        if (!ProgramManager.getTypeForValue(equal).toBoolean(equal)) {
          Object greater = ProgramManager.getTypeForValue(selfItem).applyOperator(scope, BinaryOperator.GT, selfItem, other.get(i), null, false);
          return ProgramManager.getTypeForValue(greater).toBoolean(greater);
        }
      }
      return false;
    }
    return super.__gt__(scope, self, o);
  }

  @Override
  protected Object __in__(final Scope scope, final MCList self, final Object o) {
    return self.contains(o);
  }

  @Override
  protected boolean __bool__(final MCList self) {
    return !self.isEmpty();
  }

  @Override
  protected Iterator<?> __iter__(final Scope scope, final MCList self) {
    return self.iterator();
  }

  @Override
  protected MCList __copy__(final Scope scope, final MCList self) {
    return new MCList(self.stream().map(e -> ProgramManager.getTypeForValue(e).copy(scope, e)).collect(Collectors.toList()));
  }

  @Override
  protected long __len__(final Scope scope, final MCList self) {
    return self.size();
  }

  @Override
  public MCList explicitCast(final Scope scope, final Object o) {
    if (o instanceof Collection<?> c) {
      return this.__copy__(scope, new MCList(c));
    } else if (o instanceof Iterable<?> i) {
      MCList list = new MCList();
      for (Object e : i) {
        list.add(ProgramManager.getTypeForValue(e).copy(scope, e));
      }
      return list;
    } else if (o instanceof String s) {
      MCList list = new MCList();
      for (int i = 0; i < s.length(); i++) {
        list.add(String.valueOf(s.charAt(i)));
      }
      return list;
    }
    return super.explicitCast(scope, o);
  }

  @Override
  protected CompoundTag _writeToTag(final MCList self) {
    CompoundTag tag = super._writeToTag(self);
    CompoundTagListTag list = new CompoundTagListTag();
    self.forEach(v -> list.add(ProgramManager.getTypeForValue(v).writeToTag(v)));
    tag.putTag(VALUES_KEY, list);
    return tag;
  }

  @Override
  public MCList readFromTag(final Scope scope, final CompoundTag tag) {
    MCList list = new MCList();
    CompoundTagListTag tagsList = tag.getList(VALUES_KEY, TagType.COMPOUND_TAG_TYPE);
    tagsList.forEach(t -> list.add(ProgramManager.getTypeForName(t.getString(TypeBase.NAME_KEY)).readFromTag(scope, t)));
    return list;
  }
}
