package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.exceptions.TypeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * Wrapper type for {@link Integer}.
 */
@Type(name = IntType.NAME, doc = "Type representing integers.")
public class IntType extends TypeBase<Long> {
  public static final String NAME = "int";

  private static final String VALUE_KEY = "Value";

  @Override
  public Class<Long> getWrappedType() {
    return Long.class;
  }

  @Override
  protected Object __minus__(final Scope scope, final Long self) {
    return -self;
  }

  /**
   * Checks whether an object can be converted to a float.
   */
  private static boolean isFloat(final Object o) {
    TypeBase<?> type = ProgramManager.getTypeForValue(o);
    try {
      return type.toFloat(o) != type.toInt(o);
    } catch (TypeException e) {
      return false;
    }
  }

  @Override
  protected Object __add__(final Scope scope, final Long self, final Object o, final boolean inPlace) {
    if (isFloat(o)) {
      return self + ProgramManager.getTypeForValue(o).toFloat(o);
    }
    return self + ProgramManager.getTypeForValue(o).toInt(o);
  }

  @Override
  protected Object __radd__(final Scope scope, final Long self, final Object o) {
    return this.__add__(scope, self, o, false);
  }

  @Override
  protected Object __sub__(final Scope scope, final Long self, final Object o, final boolean inPlace) {
    if (isFloat(o)) {
      return self - ProgramManager.getTypeForValue(o).toFloat(o);
    }
    return self - ProgramManager.getTypeForValue(o).toInt(o);
  }

  @Override
  protected Object __rsub__(final Scope scope, final Long self, final Object o) {
    if (isFloat(o)) {
      return ProgramManager.getTypeForValue(o).toFloat(o) - self;
    }
    return ProgramManager.getTypeForValue(o).toInt(o) - self;
  }

  @Override
  protected Object __mul__(final Scope scope, final Long self, final Object o, final boolean inPlace) {
    if (isFloat(o)) {
      return self * ProgramManager.getTypeForValue(o).toFloat(o);
    }
    return self * ProgramManager.getTypeForValue(o).toInt(o);
  }

  @Override
  protected Object __rmul__(final Scope scope, final Long self, final Object o) {
    return this.__mul__(scope, self, o, false);
  }

  @Override
  protected Object __div__(final Scope scope, final Long self, final Object o, final boolean inPlace) {
    double l = ProgramManager.getTypeForValue(o).toFloat(o);
    if (l == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return self / l;
  }

  @Override
  protected Object __rdiv__(final Scope scope, final Long self, final Object o) {
    if (self == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return ProgramManager.getTypeForValue(o).toFloat(o) / self;
  }

  @Override
  protected Object __mod__(final Scope scope, final Long self, final Object o, final boolean inPlace) {
    if (isFloat(o)) {
      double l = ProgramManager.getTypeForValue(o).toFloat(o);
      if (l == 0) {
        throw new ArithmeticException("/ by 0");
      }
      return Utils.trueModulo(self, l);
    }
    long l = ProgramManager.getTypeForValue(o).toInt(o);
    if (l == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return (long) Utils.trueModulo(self, l);
  }

  @Override
  protected Object __rmod__(final Scope scope, final Long self, final Object o) {
    if (isFloat(o)) {
      if (self == 0) {
        throw new ArithmeticException("/ by 0");
      }
      return Utils.trueModulo(ProgramManager.getTypeForValue(o).toFloat(o), self);
    }
    if (self == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return (long) Utils.trueModulo(ProgramManager.getTypeForValue(o).toInt(o), self);
  }

  @Override
  protected Object __pow__(final Scope scope, final Long self, final Object o, final boolean inPlace) {
    if (isFloat(o)) {
      return Math.pow(self, ProgramManager.getTypeForValue(o).toFloat(o));
    }
    return Math.pow(self, ProgramManager.getTypeForValue(o).toInt(o));
  }

  @Override
  protected Object __rpow__(final Scope scope, final Long self, final Object o) {
    if (isFloat(o)) {
      return Math.pow(ProgramManager.getTypeForValue(o).toFloat(o), self);
    }
    return Math.pow(ProgramManager.getTypeForValue(o).toInt(o), self);
  }

  @Override
  protected Object __eq__(final Scope scope, final Long self, final Object o) {
    if (isFloat(o)) {
      return self == ProgramManager.getTypeForValue(o).toFloat(o);
    } else if (o instanceof Long || o instanceof Boolean) {
      return self == ProgramManager.getTypeForValue(o).toInt(o);
    }
    return super.__eq__(scope, self, o);
  }

  @Override
  protected Object __gt__(final Scope scope, final Long self, final Object o) {
    if (isFloat(o)) {
      return self > ProgramManager.getTypeForValue(o).toFloat(o);
    } else if (o instanceof Long || o instanceof Boolean) {
      return self > ProgramManager.getTypeForValue(o).toInt(o);
    }
    return super.__gt__(scope, self, o);
  }

  @Override
  protected boolean __bool__(final Long self) {
    return self != 0;
  }

  @Override
  protected long __int__(final Long self) {
    return self;
  }

  @Override
  protected double __float__(final Long self) {
    return self;
  }

  @Override
  public Long implicitCast(final Scope scope, final Object o) {
    if (o instanceof Double) { // Cannot implicitly convert floats to ints
      return super.implicitCast(scope, o);
    }
    return ProgramManager.getTypeForValue(o).toInt(o);
  }

  @Override
  public Long explicitCast(final Scope scope, final Object o) throws MCCodeRuntimeException {
    if (o instanceof String s) {
      try {
        return Long.parseLong(s);
      } catch (NumberFormatException e) {
        throw new EvaluationException(scope, "mccode.interpreter.error.int_format", Utils.escapeString(s));
      }
    }
    return ProgramManager.getTypeForValue(o).toInt(o);
  }

  @Override
  protected CompoundTag _writeToTag(final Long self) {
    CompoundTag tag = super._writeToTag(self);
    tag.putLong(VALUE_KEY, self);
    return tag;
  }

  @Override
  public Long readFromTag(final Scope scope, final CompoundTag tag) {
    return tag.getLong(VALUE_KEY);
  }
}
