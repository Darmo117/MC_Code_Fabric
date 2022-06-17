package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.CompoundTagListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

import java.util.*;

/**
 * A scope is an object that represents the context of a program during execution.
 * It holds all declared variables and functions.
 */
public class Scope implements TagDeserializable {
  /**
   * Name of the main scope.
   */
  public static final String MAIN_SCOPE_NAME = "$main";

  public static final String VARIABLES_KEY = "Variables";

  private final String name;
  private final Scope parentScope;
  private final Program program;
  private final Map<String, Variable> variables = new HashMap<>();
  private int callStackSize;

  /**
   * Create a global scope for the given program.
   *
   * @param program The program this scope belongs to.
   */
  public Scope(Program program) {
    this.name = MAIN_SCOPE_NAME;
    this.parentScope = null;
    this.program = program;
    this.callStackSize = 0;
    this.defineBuiltinConstants();
    this.defineBuiltinFunctions();
  }

  /**
   * Create a sub-scope of another scope.
   *
   * @param name        Sub-scope’s name.
   * @param parentScope Parent of this scope.
   */
  public Scope(final String name, Scope parentScope) {
    this.name = name;
    this.parentScope = parentScope;
    this.program = parentScope.program;
    this.callStackSize = parentScope.callStackSize;
  }

  /**
   * Return this scope’s name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Return this scope’s parent.
   */
  public Optional<Scope> getParentScope() {
    return Optional.ofNullable(this.parentScope);
  }

  /**
   * Return this scope’s program.
   */
  public Program getProgram() {
    return this.program;
  }

  public int getCallStackSize() {
    return this.callStackSize;
  }

  public void setCallStackSize(int callStackSize) {
    this.callStackSize = callStackSize;
  }

  /**
   * Return the value of each variable.
   */
  public Map<String, Variable> getVariables() {
    return new HashMap<>(this.variables);
  }

  /**
   * Return whether a variable with the given name is declared in this scope.
   *
   * @param name Variable’s name.
   * @return True if a variable with this name exists, false otherwise.
   */
  public boolean isVariableDefined(final String name) {
    return this.variables.containsKey(name) || (this.parentScope != null && this.parentScope.isVariableDefined(name));
  }

  /**
   * Return the value of the given variable.
   *
   * @param name        Variable’s name.
   * @param fromOutside Whether this action is performed from outside the program.
   * @return The variable’s value.
   * @throws EvaluationException If the variable doesn’t exist or cannot be accessed
   *                             from outside the program but fromOutside is true.
   */
  public Object getVariable(final String name, final boolean fromOutside) throws EvaluationException {
    if (!this.variables.containsKey(name)) {
      if (this.parentScope != null) {
        return this.parentScope.getVariable(name, fromOutside);
      } else {
        throw new EvaluationException(this, "mccode.interpreter.error.undefined_variable", name);
      }
    } else {
      return this.variables.get(name).getValue(this, fromOutside);
    }
  }

  /**
   * Sets the value of the given variable.
   *
   * @param name        Variable’s name.
   * @param value       Variable’s new value.
   * @param fromOutside Whether this action is performed from outside the program.
   * @throws EvaluationException If the variable doesn’t exist, is constant or cannot
   *                             be set from outside the program and fromOutside is true.
   */
  public void setVariable(final String name, Object value, final boolean fromOutside) throws EvaluationException {
    if (!this.variables.containsKey(name)) {
      if (this.parentScope != null) {
        this.parentScope.setVariable(name, value, fromOutside);
      } else {
        throw new EvaluationException(this, "mccode.interpreter.error.undefined_variable", name);
      }
    } else {
      this.variables.get(name).setValue(this, value, fromOutside);
    }
  }

  /**
   * Declare a variable.
   *
   * @param variable The variable.
   * @throws EvaluationException If a variable with the same name already exists.
   */
  public void declareVariable(Variable variable) throws EvaluationException {
    if (this.variables.containsKey(variable.getName())) {
      throw new EvaluationException(this, "mccode.interpreter.error.variable_already_declared", variable.getName());
    }
    this.variables.put(variable.getName(), variable);
  }

  /**
   * Delete the given variable.
   *
   * @param name        Variable’s name.
   * @param fromOutside Whether this action is performed from outside the program.
   * @throws EvaluationException If the variable doesn’t exist or is not deletable.
   */
  public void deleteVariable(final String name, final boolean fromOutside) throws EvaluationException {
    if (!this.variables.containsKey(name)) {
      if (this.parentScope != null) {
        this.parentScope.deleteVariable(name, fromOutside);
      } else {
        throw new EvaluationException(this, "mccode.interpreter.error.undefined_variable", name);
      }
    } else {
      Variable variable = this.variables.get(name);
      if (!variable.isDeletable() || fromOutside && !variable.isEditableFromOutside()) {
        throw new EvaluationException(this, "mccode.interpreter.error.cannot_delete_variable", name);
      }
      this.variables.remove(name);
    }
  }

  /**
   * Delete all declared variables of this scope.
   */
  public void reset() {
    this.variables.clear();
    this.defineBuiltinConstants();
    this.defineBuiltinFunctions();
  }

  /**
   * Return the stack trace of this scope.
   */
  public List<StackTraceElement> getStackTrace() {
    List<StackTraceElement> trace;
    if (this.parentScope != null) {
      trace = this.parentScope.getStackTrace();
    } else {
      trace = new ArrayList<>();
    }
    trace.add(0, new StackTraceElement(this.getName()));
    return trace;
  }

  /**
   * Declare builtin constants.
   */
  private void defineBuiltinConstants() {
    this.declareVariable(new Variable("INF", true, false, true, false, Double.POSITIVE_INFINITY));
    this.declareVariable(new Variable("PI", true, false, true, false, Math.PI));
    this.declareVariable(new Variable("E", true, false, true, false, Math.E));

    this.declareVariable(new Variable("DIFF_PEACEFUL", true, false, true, false, 0));
    this.declareVariable(new Variable("DIFF_EASY", true, false, true, false, 1));
    this.declareVariable(new Variable("DIFF_NORMAL", true, false, true, false, 2));
    this.declareVariable(new Variable("DIFF_HARD", true, false, true, false, 3));

    this.declareVariable(new Variable("TIME_DAY", true, false, true, false, 1000L));
    this.declareVariable(new Variable("TIME_NIGHT", true, false, true, false, 13000L));
    this.declareVariable(new Variable("TIME_NOON", true, false, true, false, 6000L));
    this.declareVariable(new Variable("TIME_MIDNIGHT", true, false, true, false, 18000L));
    this.declareVariable(new Variable("TIME_SUNRISE", true, false, true, false, 23000L));
    this.declareVariable(new Variable("TIME_SUNSET", true, false, true, false, 12000L));
  }

  /**
   * Declare builtin functions and cast operators of declared types.
   */
  private void defineBuiltinFunctions() {
    for (BuiltinFunction function : ProgramManager.getBuiltinFunctions()) {
      this.declareVariable(new Variable(function.getName(), true, false, true, false, function));
    }
  }

  @Override
  public CompoundTag writeToTag() {
    if (this.parentScope != null) {
      throw new MCCodeException("cannot save non-global scope");
    }
    CompoundTag tag = new CompoundTag();
    CompoundTagListTag variablesList = new CompoundTagListTag();
    this.variables.values().stream()
        .filter(Variable::isDeletable) // Don’t serialize builtin functions and variables
        .forEach(v -> variablesList.add(v.writeToTag()));
    tag.putTag(VARIABLES_KEY, variablesList);
    return tag;
  }

  @Override
  public void readFromTag(final CompoundTag tag) {
    if (this.parentScope != null) {
      throw new MCCodeException("cannot load non-global scope");
    }
    CompoundTagListTag list = tag.getList(VARIABLES_KEY, TagType.COMPOUND_TAG_TYPE);
    for (CompoundTag t : list) {
      Variable variable = new Variable(t, this);
      this.variables.put(variable.getName(), variable);
    }
  }
}
