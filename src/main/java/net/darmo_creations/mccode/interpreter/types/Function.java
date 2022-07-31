package net.darmo_creations.mccode.interpreter.types;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Parameter;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeException;
import net.darmo_creations.mccode.interpreter.parser.ProgramParser;
import net.darmo_creations.mccode.interpreter.type_wrappers.TypeBase;

import java.util.*;

/**
 * Base class for functions.
 */
public abstract class Function {
  private final String name;
  protected final List<Parameter> parameters;
  private final TypeBase<?> returnType;
  private final boolean mayReturnNull;
  private final boolean vararg;

  /**
   * Create a function with the given name.
   *
   * @param name          Function’s name.
   * @param parameters    Functions parameters.
   * @param returnType    Function’s return type.
   * @param mayReturnNull Whether this function may return a null value.
   * @param vararg        Whether the last parameter is a vararg.
   */
  public Function(final String name, final List<Parameter> parameters, final TypeBase<?> returnType, final boolean mayReturnNull, final boolean vararg) {
    if (!ProgramParser.IDENTIFIER_PATTERN.asPredicate().test(name)) {
      throw new MCCodeException(String.format("invalid function name \"%s\" for class %s", name, this.getClass().getSimpleName()));
    }
    this.name = name == null ? "<anonymous>" : name;
    if (vararg && parameters.isEmpty()) {
      throw new MCCodeException("function \"%s\" is vararg but has no parameters".formatted(this.name));
    }
    // Check for duplicate names
    Set<String> names = new HashSet<>();
    for (Parameter parameter : parameters) {
      String parameterName = parameter.getName();
      if (names.contains(parameterName)) {
        throw new MCCodeException("parameter %s declared twice in function %s".formatted(parameterName, names));
      }
      names.add(parameterName);
    }
    this.parameters = parameters;
    this.returnType = Objects.requireNonNull(returnType);
    this.mayReturnNull = mayReturnNull;
    this.vararg = vararg;
  }

  /**
   * Return this function’s name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Return the return type of this function.
   */
  public TypeBase<?> getReturnType() {
    return this.returnType;
  }

  /**
   * Indicate whether this function may return a null value.
   */
  public boolean mayReturnNull() {
    return this.mayReturnNull;
  }

  /**
   * Indicate whether this function has a vararg.
   */
  public boolean isVarArg() {
    return this.vararg;
  }

  /**
   * Return the name and type of the parameter at the given index.
   *
   * @param index Parameter’s index.
   * @return The parameter object.
   */
  public Parameter getParameter(final int index) {
    return this.parameters.get(index);
  }

  /**
   * Return the list of parameters of this function.
   */
  public List<Parameter> getParameters() {
    return this.parameters;
  }

  /**
   * Call this function in the given scope.
   *
   * @param scope     The scope the function is called from.It contains variables named
   *                  after this function’s parameters containing the values of the arguments.
   * @param callStack The current call stack.
   * @return A value.
   */
  public abstract Object apply(Scope scope, CallStack callStack);

  /**
   * Generate a parameter types list from an array of types.
   * <p>
   * This is a helper method for subclasses of this class.
   *
   * @param types The array to generate types list from.
   * @return The types list.
   */
  protected static List<Parameter> generateParameters(final TypeBase<?>... types) {
    List<Parameter> list = new ArrayList<>();
    for (int i = 0; i < types.length; i++) {
      list.add(new Parameter(getAutoParameterNameForIndex(i), types[i]));
    }
    return list;
  }

  /**
   * Return the automatic parameter name for the given parameter index.
   * <p>
   * This is a helper method for subclasses of this class.
   *
   * @param index Parameter’s index.
   * @return Parameter’s name.
   */
  protected static String getAutoParameterNameForIndex(final int index) {
    return String.format("_x%d_", index);
  }

  /**
   * Formats this function’s arguments as a string.
   *
   * @param addTypes Whether to prefix arguments with their respective type.
   * @return A string.
   */
  protected String formatParametersForToString(boolean addTypes) {
    StringJoiner joiner = new StringJoiner(", ");
    List<Parameter> parameterList = this.parameters;
    for (int i = 0; i < parameterList.size(); i++) {
      Parameter p = parameterList.get(i);
      String vargs = i == this.parameters.size() - 1 && this.isVarArg() ? "..." : "";
      String s = p.getName() + vargs;
      if (addTypes) {
        s = p.getType().getName() + " " + s;
      }
      joiner.add(s);
    }
    return joiner.toString();
  }
}
