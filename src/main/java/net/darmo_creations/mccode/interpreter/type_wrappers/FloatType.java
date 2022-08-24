package net.darmo_creations.mccode.interpreter.type_wrappers;

import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.annotations.Type;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

/**
 * Wrapper type for {@link Double}.
 */
@Type(name = FloatType.NAME, doc = "Type representing real numbers.")
public class FloatType extends TypeBase<Double> {
  public static final String NAME = "float";

  public static final String VALUE_KEY = "Value";

  @Override
  public Class<Double> getWrappedType() {
    return Double.class;
  }

  @Override
  protected Object __minus__(final Scope scope, final Double self) {
    return self == 0 ? 0.0 : -self; // Avoid -0.0
  }

  @Override
  protected Object __add__(final Scope scope, final Double self, final Object o, final boolean inPlace) {
    return self + ProgramManager.getTypeForValue(o).toFloat(o);
  }

  @Override
  protected Object __radd__(final Scope scope, final Double self, final Object o) {
    return this.__add__(scope, self, o, false);
  }

  @Override
  protected Object __sub__(final Scope scope, final Double self, final Object o, final boolean inPlace) {
    return self - ProgramManager.getTypeForValue(o).toFloat(o);
  }

  @Override
  protected Object __rsub__(final Scope scope, final Double self, final Object o) {
    return ProgramManager.getTypeForValue(o).toFloat(o) - self;
  }

  @Override
  protected Object __mul__(final Scope scope, final Double self, final Object o, final boolean inPlace) {
    return self * ProgramManager.getTypeForValue(o).toFloat(o);
  }

  @Override
  protected Object __rmul__(final Scope scope, final Double self, final Object o) {
    return this.__mul__(scope, self, o, false);
  }

  @Override
  protected Object __div__(final Scope scope, final Double self, final Object o, final boolean inPlace) {
    double v = ProgramManager.getTypeForValue(o).toFloat(o);
    if (v == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return self / v;
  }

  @Override
  protected Object __rdiv__(final Scope scope, final Double self, final Object o) {
    if (self == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return ProgramManager.getTypeForValue(o).toFloat(o) / self;
  }

  @Override
  protected Object __mod__(final Scope scope, final Double self, final Object o, final boolean inPlace) {
    double v = ProgramManager.getTypeForValue(o).toFloat(o);
    if (v == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return Utils.trueModulo(self, v);
  }

  @Override
  protected Object __rmod__(final Scope scope, final Double self, final Object o) {
    if (self == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return Utils.trueModulo(ProgramManager.getTypeForValue(o).toFloat(o), self);
  }

  @Override
  protected Object __pow__(final Scope scope, final Double self, final Object o, final boolean inPlace) {
    return Math.pow(self, ProgramManager.getTypeForValue(o).toFloat(o));
  }

  @Override
  protected Object __rpow__(final Scope scope, final Double self, final Object o) {
    return Math.pow(ProgramManager.getTypeForValue(o).toFloat(o), self);
  }

  @Override
  protected Object __eq__(final Scope scope, final Double self, final Object o) {
    if (o instanceof Number || o instanceof Boolean) {
      return self == ProgramManager.getTypeForValue(o).toFloat(o);
    }
    return super.__eq__(scope, self, o);
  }

  @Override
  protected Object __gt__(final Scope scope, final Double self, final Object o) {
    if (o instanceof Number || o instanceof Boolean) {
      return self > ProgramManager.getTypeForValue(o).toFloat(o);
    }
    return super.__gt__(scope, self, o);
  }

  @Override
  protected boolean __bool__(final Double self) {
    return self != 0;
  }

  @Override
  protected long __int__(final Double self) {
    return self.longValue();
  }

  @Override
  protected double __float__(final Double self) {
    return self;
  }

  @Override
  public Double implicitCast(final Scope scope, final Object o) {
    return ProgramManager.getTypeForValue(o).toFloat(o);
  }

  @Override
  public Double explicitCast(final Scope scope, final Object o) throws MCCodeRuntimeException {
    if (o instanceof String s) {
      try {
        return Double.parseDouble(s);
      } catch (NumberFormatException e) {
        throw new EvaluationException(scope, "mccode.interpreter.error.float_format", Utils.escapeString(s));
      }
    }
    return ProgramManager.getTypeForValue(o).toFloat(o);
  }

  @Override
  protected CompoundTag _writeToTag(final Double self) {
    CompoundTag tag = super._writeToTag(self);
    tag.putDouble(VALUE_KEY, self);
    return tag;
  }

  @Override
  public Double readFromTag(final Scope scope, final CompoundTag tag) {
    return tag.getDouble(VALUE_KEY);
  }
}
