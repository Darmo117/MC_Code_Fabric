package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.ProgramManager;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.nodes.Node;
import net.darmo_creations.mccode.interpreter.nodes.NodeTagHelper;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.type_wrappers.TypeBase;

import java.util.Objects;

/**
 * Statement that sets the value of an object’s property.
 */
public class SetPropertyStatement extends Statement {
  public static final int ID = 14;

  private static final String TARGET_KEY = "Target";
  private static final String PROPERTY_NAME_KEY = "PropertyName";
  private static final String OPERATOR_KEY = "Operator";
  private static final String VALUE_KEY = "Value";

  private final Node target;
  private final String propertyName;
  private final AssigmentOperator operator;
  private final Node value;

  /**
   * Create a property assigment statement.
   *
   * @param target       Object to get the key from.
   * @param propertyName Name of the property.
   * @param operator     The assignment operator to apply.
   * @param value        The value to assign.
   * @param line         The line this statement starts on.
   * @param column       The column in the line this statement starts at.
   */
  public SetPropertyStatement(final Node target, final String propertyName, final AssigmentOperator operator,
                              final Node value, int line, int column) {
    super(line, column);
    this.target = Objects.requireNonNull(target);
    this.propertyName = Objects.requireNonNull(propertyName);
    this.operator = Objects.requireNonNull(operator);
    this.value = Objects.requireNonNull(value);
  }

  /**
   * Create a property assigment statement from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public SetPropertyStatement(final CompoundTag tag) {
    super(tag);
    this.target = NodeTagHelper.getNodeForTag(tag.getCompound(TARGET_KEY));
    this.propertyName = tag.getString(PROPERTY_NAME_KEY);
    this.operator = AssigmentOperator.fromString(tag.getString(OPERATOR_KEY));
    this.value = NodeTagHelper.getNodeForTag(tag.getCompound(VALUE_KEY));
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    Object targetObject = this.target.evaluate(scope, callStack);
    TypeBase<?> targetType = ProgramManager.getTypeForValue(targetObject);
    TypeBase<?> propertyType = targetType.getPropertyType(scope, targetObject, this.propertyName);
    Object propertyValue = targetType.getPropertyValue(scope, targetObject, this.propertyName);
    Object newPropertyValue = this.value.evaluate(scope, callStack);
    Object result = this.operator.getBaseOperator()
        .map(op -> propertyType.applyOperator(scope, op, propertyValue, newPropertyValue, null, true))
        .orElse(newPropertyValue);
    targetType.setPropertyValue(scope, targetObject, this.propertyName, result);

    return StatementAction.PROCEED;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(TARGET_KEY, this.target.writeToTag());
    tag.putString(PROPERTY_NAME_KEY, this.propertyName);
    tag.putString(OPERATOR_KEY, this.operator.getSymbol());
    tag.putTag(VALUE_KEY, this.value.writeToTag());
    return tag;
  }

  @Override
  public String toString() {
    return String.format("%s.%s %s %s;", this.target, this.propertyName, this.operator, this.value);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    SetPropertyStatement that = (SetPropertyStatement) o;
    return this.target.equals(that.target) && this.propertyName.equals(that.propertyName)
        && this.operator == that.operator && this.value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.target, this.propertyName, this.operator, this.value);
  }
}
