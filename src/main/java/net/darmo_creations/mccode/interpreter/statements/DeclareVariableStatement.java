package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Variable;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeException;
import net.darmo_creations.mccode.interpreter.nodes.Node;
import net.darmo_creations.mccode.interpreter.nodes.NodeTagHelper;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;

import java.util.Objects;

/**
 * Statement that declares a new variable.
 */
public class DeclareVariableStatement extends Statement {
  public static final int ID = 10;

  private static final String CONSTANT_KEY = "Constant";
  private static final String PUBLIC_KEY = "Public";
  private static final String EDITABLE_KEY = "Editable";
  private static final String VAR_NAME_KEY = "VariableName";
  private static final String VALUE_KEY = "Value";

  private final boolean publiclyVisible;
  private final boolean editableByCommands;
  private final boolean constant;
  private final String variableName;
  private final Node value;

  /**
   * Create a statement that declares a new variable.
   *
   * @param publiclyVisible    Whether the variable should be visible from in-game commands.
   * @param editableByCommands Whether the variable should be editable from in-game commands.
   * @param constant           Whether the variable should be a constant.
   * @param variableName       Variable’s name.
   * @param value              Variable’s value.
   * @param line               The line this statement starts on.
   * @param column             The column in the line this statement starts at.
   */
  public DeclareVariableStatement(final boolean publiclyVisible, final boolean editableByCommands, final boolean constant,
                                  final String variableName, final Node value, final int line, final int column) {
    super(line, column);
    if (constant && editableByCommands) {
      throw new MCCodeException("constant cannot be editable through commands");
    }
    if (!publiclyVisible && editableByCommands) {
      throw new MCCodeException("private variable cannot be editable through commands");
    }
    this.publiclyVisible = publiclyVisible;
    this.editableByCommands = editableByCommands;
    this.constant = constant;
    this.variableName = Objects.requireNonNull(variableName);
    this.value = Objects.requireNonNull(value);
  }

  /**
   * Create a statement that declares a new variable from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public DeclareVariableStatement(final CompoundTag tag) {
    super(tag);
    this.publiclyVisible = tag.getBoolean(PUBLIC_KEY);
    this.editableByCommands = tag.getBoolean(EDITABLE_KEY);
    this.constant = tag.getBoolean(CONSTANT_KEY);
    this.variableName = tag.getString(VAR_NAME_KEY);
    this.value = NodeTagHelper.getNodeForTag(tag.getCompound(VALUE_KEY));
    if (this.constant && this.editableByCommands) {
      throw new MCCodeException("constant cannot be editable through commands");
    }
    if (!this.publiclyVisible && this.editableByCommands) {
      throw new MCCodeException("private variable cannot be editable through commands");
    }
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    Object value = this.value.evaluate(scope, callStack);
    scope.declareVariable(new Variable(this.variableName, this.publiclyVisible, this.editableByCommands, this.constant, true, value));

    return StatementAction.PROCEED;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putBoolean(PUBLIC_KEY, this.publiclyVisible);
    tag.putBoolean(EDITABLE_KEY, this.editableByCommands);
    tag.putBoolean(CONSTANT_KEY, this.constant);
    tag.putString(VAR_NAME_KEY, this.variableName);
    tag.putTag(VALUE_KEY, this.value.writeToTag());
    return tag;
  }

  @Override
  public String toString() {
    String s = String.format("%s := %s;", this.variableName, this.value);
    if (this.constant) {
      s = "const " + s;
    } else {
      s = "var " + s;
    }
    if (this.editableByCommands) {
      s = "editable " + s;
    }
    if (this.publiclyVisible) {
      s = "public " + s;
    }
    return s;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    DeclareVariableStatement that = (DeclareVariableStatement) o;
    return this.publiclyVisible == that.publiclyVisible && this.editableByCommands == that.editableByCommands
        && this.constant == that.constant && this.variableName.equals(that.variableName) && this.value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.publiclyVisible, this.editableByCommands, this.constant, this.variableName, this.value);
  }
}
