package net.darmo_creations.mccode.interpreter.nodes;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.types.Function;
import net.darmo_creations.mccode.interpreter.types.MCList;
import net.darmo_creations.mccode.interpreter.types.UserFunction;

import java.lang.reflect.Array;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A node that represents the call to a function.
 */
public class FunctionCallNode extends OperationNode {
  public static final int ID = 103;

  private static final String FUNCTION_OBJ_KEY = "FunctionObject";

  protected final Node functionObject;

  /**
   * Create a function call node.
   *
   * @param functionObject Expression that evaluates to a {@link Function} object.
   * @param arguments      Function’s arguments.
   * @param line           The line this node starts on.
   * @param column         The column in the line this node starts at.
   */
  public FunctionCallNode(final Node functionObject, final List<Node> arguments, final int line, final int column) {
    super(arguments, line, column);
    this.functionObject = Objects.requireNonNull(functionObject);
  }

  /**
   * Create a function call node from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public FunctionCallNode(final CompoundTag tag) {
    super(tag);
    this.functionObject = NodeTagHelper.getNodeForTag(tag.getCompound(FUNCTION_OBJ_KEY));
  }

  @Override
  protected Object evaluateWrapped(final Scope scope, CallStack callStack) {
    Object o = this.functionObject.evaluate(scope, callStack);

    Function function;
    try {
      function = (Function) o;
    } catch (ClassCastException e) {
      throw new EvaluationException(scope, "mccode.interpreter.error.calling_non_callable",
          ProgramManager.getTypeForValue(o));
    }

    List<Object> args = this.arguments.stream().map(node -> node.evaluate(scope, callStack)).collect(Collectors.toList());
    return callFunction(function, args, scope, callStack, this.getLine(), this.getColumn());
  }

  /**
   * Call a function object with the given arguments.
   *
   * @param function  Function to call.
   * @param args      Arguments to pass to the function.
   * @param scope     Function call’s scope.
   * @param callStack Function call’s call stack
   * @param line      Function call’s line.
   * @param column    Function call’s column.
   * @return The result of the function.
   * @throws EvaluationException If the number of arguments is invalid.
   */
  public static Object callFunction(
      final Function function, final List<Object> args,
      final Scope scope, CallStack callStack,
      int line, int column
  ) {
    if (function.isVarArg()) {
      if (args.size() < function.getParameters().size() - 1) {
        throw new EvaluationException(scope, "mccode.interpreter.error.invalid_vararg_function_arguments_number",
            function.getName(), function.getParameters().size() - 1, args.size());
      }
    } else if (args.size() != function.getParameters().size()) {
      throw new EvaluationException(scope, "mccode.interpreter.error.invalid_function_arguments_number",
          function.getName(), function.getParameters().size(), args.size());
    }

    // Put arguments into variables
    List<Variable> values = new LinkedList<>();
    for (int i = 0; i < function.getParameters().size(); i++) {
      Parameter parameter = function.getParameter(i);
      Object value;
      if (function.isVarArg() && i == function.getParameters().size() - 1) {
        // Pack all remaining values into array
        List<Object> remaining = args.subList(i, args.size());
        if (function instanceof UserFunction) {
          value = new MCList(remaining);
        } else {
          Object[] array = (Object[]) Array.newInstance(parameter.getType().getWrappedType(), remaining.size());
          for (int j = 0; j < remaining.size(); j++) {
            array[j] = parameter.getType().implicitCast(scope, remaining.get(j));
          }
          value = array;
        }
      } else {
        value = args.get(i);
      }
      values.add(new Variable(parameter.getName(), false, false, false, true, value));
    }

    Scope s;
    if (function instanceof UserFunction) {
      s = scope;
      s.push(function.getName());
      // Prevent function from accessing any non-global variables
      s.lockAllButTopAndBottom();
    } else {
      // Create new scope to prevent built-in functions from accessing program’s variables
      s = new Scope(scope.getProgram());
    }

    values.forEach(s::declareVariable);

    if (function instanceof UserFunction) {
      callStack.push(new CallStackElement(scope.getProgram().getName(), scope.getTopName(), line, column));
    }
    Object result = function.apply(s, callStack);
    if (function instanceof UserFunction) {
      callStack.pop();
      scope.pop();
    }
    // No scope to pop if built-in function, as it used a new scope
    return result;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(FUNCTION_OBJ_KEY, this.functionObject.writeToTag());
    return tag;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public String toString() {
    return String.format("%s(%s)", this.functionObject, this.arguments.stream().map(Node::toString).collect(Collectors.joining(", ")));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    FunctionCallNode that = (FunctionCallNode) o;
    return this.functionObject.equals(that.functionObject) && this.arguments.equals(that.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.functionObject, this.arguments);
  }
}
