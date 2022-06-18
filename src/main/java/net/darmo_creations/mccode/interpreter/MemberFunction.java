package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.exceptions.MCCodeException;
import net.darmo_creations.mccode.interpreter.nodes.MethodCallNode;
import net.darmo_creations.mccode.interpreter.type_wrappers.TypeBase;
import net.darmo_creations.mccode.interpreter.types.Function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This class represents a method of a builtin type.
 * It wraps a Java {@link Method} object annoted by the
 * {@link net.darmo_creations.mccode.interpreter.annotations.Method} annotation
 * inside a class extending {@link TypeBase}.
 */
public class MemberFunction extends Function {
  /**
   * Name of the special variable representing the object this method belongs to.
   * Automatically generated by the {@link MethodCallNode} statement.
   */
  public static final String SELF_PARAM_NAME = "$this";

  private final TypeBase<?> hostType;
  private final Method method;
  private final String doc;

  /**
   * Create a member function (method) for a given type.
   *
   * @param hostType        Type of method’s host.
   * @param name            Method’s name.
   * @param parametersTypes Method’s parameters: a map associating a parameter name to its index and type.
   * @param returnType      Method’s return type.
   * @param method          The actual Java method.
   * @param doc             Documentation string for this method.
   * @param mayReturnNull   Whether this method may return a null value.
   */
  public MemberFunction(final TypeBase<?> hostType, final String name, final List<? extends TypeBase<?>> parametersTypes,
                        final TypeBase<?> returnType, final boolean mayReturnNull, final Method method, final String doc) {
    super(Objects.requireNonNull(name), generateParameters(parametersTypes.toArray(new TypeBase[0])), returnType, mayReturnNull);
    this.hostType = Objects.requireNonNull(hostType);
    this.method = Objects.requireNonNull(method);
    this.doc = doc;
  }

  /**
   * Return the type that hosts this method.
   */
  public TypeBase<?> getHostType() {
    return this.hostType;
  }

  /**
   * Return the documentation for this method.
   */
  public Optional<String> getDoc() {
    return Optional.ofNullable(this.doc);
  }

  @Override
  public Object apply(Scope scope, CallStack callStack) {
    try {
      Object self = scope.getVariable(SELF_PARAM_NAME, false);

      if (this.hostType != ProgramManager.getTypeForValue(self)) {
        throw new MCCodeException(String.format("method %s expected instance of type %s, got %s",
            this.getName(), this.hostType.getWrappedType(), self != null ? self.getClass() : null));
      }

      Object[] args = new Object[2 + this.parameters.size()];
      args[0] = scope;
      args[1] = self;
      for (int i = 0; i < this.parameters.size(); i++) {
        Parameter parameter = this.parameters.get(i);
        Object arg = scope.getVariable(parameter.getName(), false);
        args[i + 2] = arg == null ? null : parameter.getType().implicitCast(scope, arg);
      }

      return this.method.invoke(this.hostType, args);
    } catch (IllegalAccessException e) {
      throw new MCCodeException(e);
    } catch (InvocationTargetException e) {
      Throwable cause = e.getCause();
      if (cause instanceof RuntimeException ex) {
        throw ex;
      }
      throw new MCCodeException(cause);
    }
  }

  @Override
  public String toString() {
    String params = this.parameters.stream()
        .map(p -> p.getType().getName() + " " + p.getName())
        .collect(Collectors.joining(", "));
    return String.format("builtin method %s.%s(%s) -> %s", this.hostType, this.getName(), params, this.getReturnType());
  }
}
