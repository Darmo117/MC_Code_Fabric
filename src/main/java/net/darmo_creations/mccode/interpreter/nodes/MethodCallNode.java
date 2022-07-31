package net.darmo_creations.mccode.interpreter.nodes;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.type_wrappers.TypeBase;
import net.darmo_creations.mccode.interpreter.types.Function;
import net.darmo_creations.mccode.interpreter.types.MCList;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A node that represents the call to an object’s method.
 */
public class MethodCallNode extends OperationNode {
  public static final int ID = 102;

  private static final String INSTANCE_KEY = "Instance";
  private static final String METHOD_NAME_KEY = "MethodName";

  protected final Node instance;
  private final String methodName;

  /**
   * Create a method call node.
   *
   * @param instance  Expression evaluating to an object the method will be applied on.
   * @param arguments Method’s arguments.
   * @param line      The line this node starts on.
   * @param column    The column in the line this node starts at.
   */
  public MethodCallNode(final Node instance, final String methodName, final List<Node> arguments, final int line, final int column) {
    super(arguments, line, column);
    this.instance = Objects.requireNonNull(instance);
    this.methodName = Objects.requireNonNull(methodName);
  }

  /**
   * Create a method call node from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public MethodCallNode(final CompoundTag tag) {
    super(tag);
    this.instance = NodeTagHelper.getNodeForTag(tag.getCompound(INSTANCE_KEY));
    this.methodName = tag.getString(METHOD_NAME_KEY);
  }

  @Override
  protected Object evaluateWrapped(final Scope scope, CallStack callStack) {
    Object self = this.instance.evaluate(scope, callStack);
    TypeBase<?> selfType = ProgramManager.getTypeForValue(self);

    if (self instanceof Program module) {
      Object property = selfType.getPropertyValue(scope, self, this.methodName);

      Function function;
      try {
        function = (Function) property;
      } catch (ClassCastException e) {
        throw new EvaluationException(scope, "mccode.interpreter.error.calling_non_callable", selfType);
      }

      Scope moduleScope = module.getScope();
      moduleScope.push(function.getName());
      // Prevent method from accessing any non-global variables
      moduleScope.lockAllButTopAndBottom();

      if (function.isVarArg()) {
        if (this.arguments.size() < function.getParameters().size() - 1) {
          throw new EvaluationException(scope, "mccode.interpreter.error.invalid_vararg_function_arguments_number",
              function.getName(), function.getParameters().size() - 1, this.arguments.size());
        }
      } else if (this.arguments.size() != function.getParameters().size()) {
        throw new EvaluationException(scope, "mccode.interpreter.error.invalid_function_arguments_number",
            function.getName(), function.getParameters().size(), this.arguments.size());
      }

      for (int i = 0; i < function.getParameters().size(); i++) {
        Parameter parameter = function.getParameter(i);
        Object value;
        if (function.isVarArg() && i == function.getParameters().size() - 1) {
          value = new MCList(this.arguments.subList(i, this.arguments.size()).stream().map(node -> node.evaluate(scope, callStack)).toList());
        } else {
          value = this.arguments.get(i).evaluate(scope, callStack);
        }
        moduleScope.declareVariable(new Variable(parameter.getName(), false, false, false, true, value));
      }

      callStack.push(new CallStackElement(scope.getProgram().getName(), scope.getTopName(), this.getLine(), this.getColumn()));
      Object result = function.apply(moduleScope, callStack);
      callStack.pop();
      moduleScope.pop();

      return result;

    } else {
      MemberFunction method = selfType.getMethod(this.methodName);
      if (method == null) {
        throw new EvaluationException(scope, "mccode.interpreter.error.no_method_for_type", selfType.getName(), this.methodName);
      }
      // Create new scope to prevent built-in methods from accessing program’s variables
      Scope methodScope = new Scope(scope.getProgram());

      if (method.isVarArg()) {
        if (this.arguments.size() < method.getParameters().size() - 1) {
          throw new EvaluationException(scope, "mccode.interpreter.error.invalid_vararg_method_arguments_number",
              method.getHostType(), method.getName(), method.getParameters().size() - 1, this.arguments.size());
        }
      } else if (this.arguments.size() != method.getParameters().size()) {
        throw new EvaluationException(scope, "mccode.interpreter.error.invalid_method_arguments_number",
            method.getHostType(), method.getName(), method.getParameters().size(), this.arguments.size());
      }

      methodScope.declareVariable(new Variable(MemberFunction.SELF_PARAM_NAME, false, false, true, false, self));
      for (int i = 0; i < method.getParameters().size(); i++) {
        Parameter parameter = method.getParameter(i);
        Object value;
        if (method.isVarArg() && i == method.getParameters().size() - 1) {
          List<Node> remaining = this.arguments.subList(i, this.arguments.size());
          Object[] array = (Object[]) Array.newInstance(parameter.getType().getWrappedType(), remaining.size());
          for (int j = 0; j < remaining.size(); j++) {
            array[j] = parameter.getType().implicitCast(scope, remaining.get(j).evaluate(scope, callStack));
          }
          value = array;
        } else {
          value = this.arguments.get(i).evaluate(scope, callStack);
        }
        methodScope.declareVariable(new Variable(parameter.getName(), false, false, false, true, value));
      }

      return method.apply(methodScope, callStack);
    }
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(INSTANCE_KEY, this.instance.writeToTag());
    tag.putString(METHOD_NAME_KEY, this.methodName);
    return tag;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public String toString() {
    String parameters = this.arguments.stream().map(Node::toString).collect(Collectors.joining(", "));
    return String.format("%s.%s(%s)", this.instance, this.methodName, parameters);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    MethodCallNode that = (MethodCallNode) o;
    return this.instance.equals(that.instance) && this.methodName.equals(that.methodName) && this.arguments.equals(that.arguments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.instance, this.methodName, this.arguments);
  }
}
