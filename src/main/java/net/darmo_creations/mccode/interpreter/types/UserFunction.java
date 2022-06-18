package net.darmo_creations.mccode.interpreter.types;

import net.darmo_creations.mccode.interpreter.CallStackElement;
import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.exceptions.SyntaxErrorException;
import net.darmo_creations.mccode.interpreter.statements.ReturnStatement;
import net.darmo_creations.mccode.interpreter.statements.Statement;
import net.darmo_creations.mccode.interpreter.statements.StatementAction;
import net.darmo_creations.mccode.interpreter.statements.StatementTagHelper;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.StringListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * This class represents a user-defined function.
 * <p>
 * User functions can be serialized an deserialized to and from tags.
 */
public class UserFunction extends Function {
  public static final int MAX_CALL_DEPTH = 100;

  public static final String NAME_KEY = "Name";
  public static final String PARAMETERS_KEY = "Parameters";
  public static final String STATEMENTS_KEY = "Statements";
  public static final String IP_KEY = "IP";

  private final List<Statement> statements;
  /**
   * Instruction pointer.
   */
  private int ip;

  /**
   * Create a user function.
   *
   * @param name           Function’s name.
   * @param parameterNames Names of the function’s parameters.
   * @param statements     List of function’s statements.
   */
  public UserFunction(final String name, final List<String> parameterNames, final List<Statement> statements) {
    super(name, extractParameters(parameterNames), ProgramManager.getTypeInstance(AnyType.class), false);
    this.statements = Objects.requireNonNull(statements);
    this.ip = 0;
  }

  /**
   * Create a user function from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public UserFunction(final CompoundTag tag) {
    super(tag.getString(NAME_KEY), extractParameters(tag), ProgramManager.getTypeInstance(AnyType.class), false);
    this.statements = StatementTagHelper.deserializeStatementsList(tag, STATEMENTS_KEY);
    this.ip = tag.getInt(IP_KEY);
  }

  @Override
  public Object apply(Scope scope, CallStack callStack) {
    if (callStack.size() == MAX_CALL_DEPTH) {
      throw new EvaluationException(scope, "mccode.interpreter.error.stack_overflow");
    }

    List<Statement> statementList = this.statements;
    while (this.ip < statementList.size()) {
      Statement statement = statementList.get(this.ip);
      StatementAction action = statement.execute(scope, callStack);
      if (action == StatementAction.EXIT_FUNCTION) {
        break;
      } else {
        CallStackElement callStackElement = new CallStackElement(
            scope.getProgram().getName(), scope.getName(), statement.getLine(), statement.getColumn());
        if (action == StatementAction.EXIT_LOOP) {
          callStack.push(callStackElement);
          throw new SyntaxErrorException(statement.getLine(), statement.getColumn(),
              "mccode.interpreter.error.break_outside_loop");
        } else if (action == StatementAction.CONTINUE_LOOP) {
          callStack.push(callStackElement);
          throw new SyntaxErrorException(statement.getLine(), statement.getColumn(),
              "mccode.interpreter.error.continue_outside_loop");
        } else if (action == StatementAction.WAIT) {
          callStack.push(callStackElement);
          throw new SyntaxErrorException(statement.getLine(), statement.getColumn(),
              "mccode.interpreter.error.wait_in_function");
        }
      }
      this.ip++;
    }
    this.ip = 0;
    if (scope.isVariableDefined(ReturnStatement.RETURN_SPECIAL_VAR_NAME)) {
      return scope.getVariable(ReturnStatement.RETURN_SPECIAL_VAR_NAME, false);
    }
    return null;
  }

  /**
   * Serialize this function to an NBT tag.
   *
   * @return The tag.
   */
  public CompoundTag writeToNBT() {
    CompoundTag tag = new CompoundTag();
    tag.putString(NAME_KEY, this.getName());
    StringListTag parametersList = new StringListTag();
    this.parameters.stream()
        .map(Parameter::getName)
        .forEach(parametersList::add);
    tag.putTag(PARAMETERS_KEY, parametersList);
    tag.putTag(STATEMENTS_KEY, StatementTagHelper.serializeStatementsList(this.statements));
    tag.putInt(IP_KEY, this.ip);
    return tag;
  }

  @Override
  public String toString() {
    String params = this.parameters.stream()
        .map(Parameter::getName)
        .collect(Collectors.joining(", "));
    return String.format("function %s(%s)%send",
        this.getName(), params, this.statements.isEmpty() ? "\n" : Utils.indentStatements(this.statements));
  }

  /**
   * Extract function parameters from the given tag.
   *
   * @param tag The tag to extract parameters from.
   * @return The parameters list.
   */
  public static List<Parameter> extractParameters(final CompoundTag tag) {
    StringListTag parametersTag = tag.getList(PARAMETERS_KEY, TagType.STRING_TAG_TYPE);
    List<Parameter> parameters = new ArrayList<>();
    for (String name : parametersTag) {
      parameters.add(new Parameter(name, ProgramManager.getTypeInstance(AnyType.class)));
    }
    return parameters;
  }

  /**
   * Generate function parameters from a list of names.
   *
   * @param parameterNames List of parameter names.
   * @return The parameters list.
   */
  private static List<Parameter> extractParameters(final List<String> parameterNames) {
    return parameterNames.stream().map(n -> new Parameter(n, ProgramManager.getTypeInstance(AnyType.class))).collect(Collectors.toList());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    UserFunction that = (UserFunction) o;
    return this.getName().equals(that.getName()) && this.ip == that.ip && this.parameters.equals(that.parameters) && this.statements.equals(that.statements);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.statements, this.parameters, this.getName(), this.ip);
  }
}
