package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * Wrapper type for {@link Boolean} class.
 */
@Type(name = BooleanType.NAME, doc = "Represents a value that can either be #true or #false.\n" +
    "Can be implicitly converted to an `int or `float.")
public class BooleanType extends TypeBase<Boolean> {
  public static final String NAME = "boolean";

  private static final String VALUE_KEY = "Value";

  @Override
  public Class<Boolean> getWrappedType() {
    return Boolean.class;
  }

  @Override
  protected Object __minus__(final Scope scope, final Boolean self) {
    return -this.__int__(self);
  }

  @Override
  protected Object __neg__(final Scope scope, final Boolean self) {
    return ~this.toInt(self);
  }

  @Override
  protected Object __add__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self) + this.toInt(b);
    }
    return super.__add__(scope, self, o, inPlace);
  }

  @Override
  protected Object __sub__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self) - this.toInt(b);
    }
    return super.__sub__(scope, self, o, inPlace);
  }

  @Override
  protected Object __mul__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self && b);
    }
    return super.__mul__(scope, self, o, inPlace);
  }

  @Override
  protected Object __div__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      if (!b) {
        throw new ArithmeticException("/ by 0");
      }
      return this.toFloat(self);
    }
    return super.__div__(scope, self, o, inPlace);
  }

  @Override
  protected Object __intdiv__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      if (!b) {
        throw new ArithmeticException("/ by 0");
      }
      return this.toInt(self);
    }
    return super.__intdiv__(scope, self, o, inPlace);
  }

  @Override
  protected Object __mod__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      if (!b) {
        throw new ArithmeticException("/ by 0");
      }
      return 0;
    }
    return super.__mod__(scope, self, o, inPlace);
  }

  @Override
  protected Object __pow__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self || !b);
    }
    return super.__pow__(scope, self, o, inPlace);
  }

  @Override
  protected Object __iand__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self) & this.toInt(b);
    }
    return super.__iand__(scope, self, o, inPlace);
  }

  @Override
  protected Object __ior__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self) | this.toInt(b);
    }
    return super.__ior__(scope, self, o, inPlace);
  }

  @Override
  protected Object __ixor__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self) ^ this.toInt(b);
    }
    return super.__ixor__(scope, self, o, inPlace);
  }

  @Override
  protected Object __shiftl__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self) << this.toInt(b);
    }
    return super.__shiftl__(scope, self, o, inPlace);
  }

  @Override
  protected Object __shiftr__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self) >> this.toInt(b);
    }
    return super.__shiftr__(scope, self, o, inPlace);
  }

  @Override
  protected Object __shiftru__(final Scope scope, final Boolean self, final Object o, final boolean inPlace) {
    if (o instanceof Boolean b) {
      return this.toInt(self) >>> this.toInt(b);
    }
    return super.__shiftru__(scope, self, o, inPlace);
  }

  @Override
  protected Object __eq__(final Scope scope, final Boolean self, final Object o) {
    if (o instanceof Boolean b) {
      return self == b;
    } else if (o instanceof Number n) {
      return ProgramManager.getTypeInstance(FloatType.class)
          .__eq__(scope, this.__float__(self), n.doubleValue());
    } else {
      return super.__eq__(scope, self, o);
    }
  }

  @Override
  protected Object __gt__(final Scope scope, final Boolean self, final Object o) {
    if (o instanceof Boolean b) {
      return self && !b;
    } else if (o instanceof Number n) {
      return ProgramManager.getTypeInstance(FloatType.class)
          .__gt__(scope, this.__float__(self), n.doubleValue());
    } else {
      return super.__gt__(scope, self, o);
    }
  }

  @Override
  protected boolean __bool__(final Boolean self) {
    return self;
  }

  @Override
  protected long __int__(Boolean self) {
    return self ? 1 : 0;
  }

  @Override
  protected double __float__(Boolean self) {
    return this.__int__(self);
  }

  @Override
  public Boolean implicitCast(final Scope scope, final Object o) {
    return ProgramManager.getTypeForValue(o).toBoolean(o);
  }

  @Override
  protected CompoundTag _writeToTag(final Boolean self) {
    CompoundTag tag = super._writeToTag(self);
    tag.putBoolean(VALUE_KEY, self);
    return tag;
  }

  @Override
  public Boolean readFromTag(final Scope scope, final CompoundTag tag) {
    return tag.getBoolean(VALUE_KEY);
  }
}
