package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.CompoundTagListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;

import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;

/**
 * Objects of this class can be stacked to make a scope stack.
 */
public class ScopeStackElement implements TagSerializable {
  private static final String NAME_KEY = "Name";
  private static final String PARENT_KEY = "Parent";
  private static final String VARIABLES_KEY = "Variables";
  private static final String LOCKED_KEY = "Locked";

  private final Scope scope;
  private final String name;
  private final ScopeStackElement parentElement;
  private final Map<String, Variable> variables = new HashMap<>();
  private boolean locked;

  /**
   * Creates a global scope for the given program.
   *
   * @param scope The scope stack this element belongs to.
   */
  public ScopeStackElement(final Scope scope) {
    this.scope = scope;
    this.name = Scope.MAIN_SCOPE_NAME;
    this.parentElement = null;
  }

  /**
   * Creates a scope from a tag.
   *
   * @param scope The scope stack this element belongs to.
   * @param tag   The tag to deserialize.
   */
  public ScopeStackElement(final Scope scope, final CompoundTag tag) {
    this.scope = scope;
    this.name = tag.getString(NAME_KEY);
    if (tag.contains(PARENT_KEY, TagType.COMPOUND_TAG_TYPE)) {
      this.parentElement = new ScopeStackElement(scope, tag.getCompound(PARENT_KEY));
    } else {
      this.parentElement = null;
    }
    CompoundTagListTag list = tag.getList(VARIABLES_KEY, TagType.COMPOUND_TAG_TYPE);
    for (CompoundTag t : list) {
      Variable variable = new Variable(t, scope);
      this.variables.put(variable.getName(), variable);
    }
    this.locked = tag.getBoolean(LOCKED_KEY);
  }

  /**
   * Creates a scope stack element on top of the given one.
   *
   * @param name                    This element’s name.
   * @param parentScopeStackElement Scope stack element this one should be on top of.
   */
  private ScopeStackElement(final String name, ScopeStackElement parentScopeStackElement) {
    this.scope = parentScopeStackElement.scope;
    this.name = name;
    this.parentElement = parentScopeStackElement;
  }

  /**
   * Locks all scopes in this stack except from the top and bottom ones.
   * This acts as if the intermediary scopes do not exist.
   */
  public void lockAllButTopAndBottom() {
    if (this.parentElement != null) {
      this.parentElement.lockAllButTop();
    }
  }

  private void lockAllButTop() {
    if (this.parentElement != null) {
      this.locked = true;
      this.parentElement.lockAllButTop();
    }
  }

  /**
   * Unlocks all scopes in this stack.
   */
  public void unlockStack() {
    this.locked = false;
    if (this.parentElement != null) {
      this.parentElement.unlockStack();
    }
  }

  /**
   * Pushes a new element on top of this scope.
   *
   * @param name New element’s name.
   * @return The new element.
   */
  public ScopeStackElement push(final String name) {
    return new ScopeStackElement(name, this);
  }

  /**
   * Pops the top element from the stack.
   *
   * @return The scope below this one.
   * @throws EmptyStackException If this stack has no parent.
   */
  public ScopeStackElement pop() {
    if (this.parentElement == null) {
      throw new EmptyStackException();
    }
    return this.parentElement;
  }

  /**
   * Return this element’s name.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Returns the size of this stack.
   */
  public int size() {
    if (this.parentElement == null) {
      return 1;
    }
    return 1 + this.parentElement.size();
  }

  /**
   * Return the value of each variable.
   */
  public Map<String, Variable> getVariables() {
    Map<String, Variable> variables = new HashMap<>();
    if (!this.locked) {
      variables.putAll(this.variables);
    }
    if (this.parentElement != null) {
      variables.putAll(this.parentElement.getVariables());
    }
    return variables;
  }

  /**
   * Return whether a variable with the given name is declared in this scope.
   *
   * @param name Variable’s name.
   * @return True if a variable with this name exists, false otherwise.
   */
  public boolean isVariableDefined(final String name) {
    return (!this.locked && this.variables.containsKey(name))
        || (this.parentElement != null && this.parentElement.isVariableDefined(name));
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
    if (this.locked || !this.variables.containsKey(name)) {
      if (this.parentElement != null) {
        return this.parentElement.getVariable(name, fromOutside);
      } else {
        throw new EvaluationException(this.scope, "mccode.interpreter.error.undefined_variable", name);
      }
    } else {
      return this.variables.get(name).getValue(this.scope, fromOutside);
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
    if (this.locked || !this.variables.containsKey(name)) {
      if (this.parentElement != null) {
        this.parentElement.setVariable(name, value, fromOutside);
      } else {
        throw new EvaluationException(this.scope, "mccode.interpreter.error.undefined_variable", name);
      }
    } else {
      this.variables.get(name).setValue(this.scope, value, fromOutside);
    }
  }

  /**
   * Declare a variable.
   *
   * @param variable The variable.
   * @throws EvaluationException If a variable with the same name already exists.
   */
  public void declareVariable(Variable variable) throws EvaluationException {
    if (this.locked) {
      throw new EvaluationException(this.scope, "mccode.interpreter.error.locked_scope", this.name);
    }
    if (this.variables.containsKey(variable.getName())) {
      throw new EvaluationException(this.scope, "mccode.interpreter.error.variable_already_declared", variable.getName());
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
    if (this.locked || !this.variables.containsKey(name)) {
      if (this.parentElement != null) {
        this.parentElement.deleteVariable(name, fromOutside);
      } else {
        throw new EvaluationException(this.scope, "mccode.interpreter.error.undefined_variable", name);
      }
    } else {
      Variable variable = this.variables.get(name);
      if (!variable.isDeletable() || fromOutside && !variable.isEditableFromOutside()) {
        throw new EvaluationException(this.scope, "mccode.interpreter.error.cannot_delete_variable", name);
      }
      this.variables.remove(name);
    }
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = new CompoundTag();
    tag.putString(NAME_KEY, this.name);
    if (this.parentElement != null) {
      tag.putTag(PARENT_KEY, this.parentElement.writeToTag());
    }
    CompoundTagListTag variablesList = new CompoundTagListTag();
    this.variables.values().stream()
        .filter(Variable::isDeletable) // Don’t serialize builtin functions and variables
        .forEach(v -> variablesList.add(v.writeToTag()));
    tag.putTag(VARIABLES_KEY, variablesList);
    tag.putBoolean(LOCKED_KEY, this.locked);
    return tag;
  }
}
