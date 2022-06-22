package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;

import java.util.EmptyStackException;
import java.util.Map;

/**
 * A scope is an object that represents the context of a program during execution.
 * It holds all declared variables and functions.
 */
public class Scope implements TagSerializable {
  /**
   * Name of the main scope.
   */
  public static final String MAIN_SCOPE_NAME = "<main>";

  private static final String STACK_KEY = "Stack";

  private final Program program;
  private ScopeStackElement stack;

  /**
   * Creates a global scope for the given program.
   *
   * @param program The program this scope belongs to.
   */
  public Scope(Program program) {
    this.stack = new ScopeStackElement(this);
    this.program = program;
    this.defineBuiltinConstants();
    this.defineBuiltinFunctions();
  }

  /**
   * Creates a scope from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public Scope(Program program, final CompoundTag tag) {
    this.program = program;
    this.stack = new ScopeStackElement(this, tag.getCompound(STACK_KEY));
    this.defineBuiltinConstants();
    this.defineBuiltinFunctions();
  }

  /**
   * Locks all scopes in the stack except the top and bottom ones.
   * This acts as if the intermediary scopes do not exist.
   * <p>
   * Stack is unlocked when {@link #pop()} is called.
   */
  public void lockAllButTopAndBottom() {
    this.stack.lockAllButTopAndBottom();
  }

  /**
   * Pushes a new scope on top of this scope.
   *
   * @param name New scope’s name.
   */
  public void push(final String name) {
    this.stack = this.stack.push(name);
  }

  /**
   * Pops the top scope from the stack.
   *
   * @throws EmptyStackException If this stack has no parent.
   */
  public void pop() {
    // Unlock stack in case it has been locked prior
    this.stack.unlockStack();
    this.stack = this.stack.pop();
  }

  /**
   * Return the name of the top scope.
   */
  public String getTopName() {
    return this.stack.getName();
  }

  /**
   * Return this scope’s program.
   */
  public Program getProgram() {
    return this.program;
  }

  /**
   * Returns the size of this scope’s stack.
   */
  public int size() {
    return this.stack.size();
  }

  /**
   * Return the value of each variable.
   */
  public Map<String, Variable> getVariables() {
    return this.stack.getVariables();
  }

  /**
   * Return whether a variable with the given name is declared in this scope.
   *
   * @param name Variable’s name.
   * @return True if a variable with this name exists, false otherwise.
   */
  public boolean isVariableDefined(final String name) {
    return this.stack.isVariableDefined(name);
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
    return this.stack.getVariable(name, fromOutside);
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
    this.stack.setVariable(name, value, fromOutside);
  }

  /**
   * Declares a variable in the top scope.
   *
   * @param variable The variable.
   * @throws EvaluationException If a variable with the same name already exists.
   */
  public void declareVariable(Variable variable) throws EvaluationException {
    this.stack.declareVariable(variable, false);
  }

  /**
   * Declares a variable in the global scope.
   *
   * @param variable The variable.
   * @throws EvaluationException If a variable with the same name already exists.
   */
  public void declareVariableGlobally(Variable variable) throws EvaluationException {
    this.stack.declareVariable(variable, true);
  }

  /**
   * Delete the given variable.
   *
   * @param name        Variable’s name.
   * @param fromOutside Whether this action is performed from outside the program.
   * @throws EvaluationException If the variable doesn’t exist or is not deletable.
   */
  public void deleteVariable(final String name, final boolean fromOutside) throws EvaluationException {
    this.stack.deleteVariable(name, fromOutside);
  }

  /**
   * Delete all declared variables of this scope.
   */
  public void reset() {
    this.stack = new ScopeStackElement(this);
    this.defineBuiltinConstants();
    this.defineBuiltinFunctions();
  }

  /**
   * Declare builtin constants.
   */
  private void defineBuiltinConstants() {
    this.stack.declareVariable(new Variable("INF", true, false, true, false, Double.POSITIVE_INFINITY), true);
    this.stack.declareVariable(new Variable("PI", true, false, true, false, Math.PI), true);
    this.stack.declareVariable(new Variable("E", true, false, true, false, Math.E), true);

    this.stack.declareVariable(new Variable("DIFF_PEACEFUL", true, false, true, false, 0), true);
    this.stack.declareVariable(new Variable("DIFF_EASY", true, false, true, false, 1), true);
    this.stack.declareVariable(new Variable("DIFF_NORMAL", true, false, true, false, 2), true);
    this.stack.declareVariable(new Variable("DIFF_HARD", true, false, true, false, 3), true);

    this.stack.declareVariable(new Variable("TIME_DAY", true, false, true, false, 1000L), true);
    this.stack.declareVariable(new Variable("TIME_NIGHT", true, false, true, false, 13000L), true);
    this.stack.declareVariable(new Variable("TIME_NOON", true, false, true, false, 6000L), true);
    this.stack.declareVariable(new Variable("TIME_MIDNIGHT", true, false, true, false, 18000L), true);
    this.stack.declareVariable(new Variable("TIME_SUNRISE", true, false, true, false, 23000L), true);
    this.stack.declareVariable(new Variable("TIME_SUNSET", true, false, true, false, 12000L), true);
  }

  /**
   * Declare builtin functions and cast operators of declared types.
   */
  private void defineBuiltinFunctions() {
    for (BuiltinFunction function : ProgramManager.getBuiltinFunctions()) {
      this.stack.declareVariable(new Variable(function.getName(), true, false, true, false, function), true);
    }
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = new CompoundTag();
    tag.putTag(STACK_KEY, this.stack.writeToTag());
    return tag;
  }
}
