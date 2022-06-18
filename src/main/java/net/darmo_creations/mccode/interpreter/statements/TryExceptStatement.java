package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStack;
import net.darmo_creations.mccode.interpreter.Scope;
import net.darmo_creations.mccode.interpreter.Utils;
import net.darmo_creations.mccode.interpreter.Variable;
import net.darmo_creations.mccode.interpreter.exceptions.MCCodeRuntimeException;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.types.MCMap;

import java.util.List;
import java.util.Objects;

/**
 * Statement that represents a try-except statement that can catch any error.
 */
public class TryExceptStatement extends Statement {
  public static final int ID = 43;

  private static final String TRY_STATEMENTS_KEY = "TryStatements";
  private static final String EXCEPT_STATEMENTS_KEY = "ExceptStatements";
  private static final String ERROR_VAR_NAME_KEY = "ErrorVariableName";
  private static final String IN_EXCEPT_KEY = "InExceptClause";
  private static final String IP_KEY = "IP";

  private final List<Statement> tryStatements;
  private final List<Statement> exceptStatements;
  private final String errorVariableName;
  /**
   * Whether the execution flow is in the 'except' clause.
   */
  private boolean inExcept;
  /**
   * Instruction pointer.
   */
  private int ip;

  /**
   * Create a statement that represents a try-except statement that can catch any error.
   *
   * @param tryStatements     Statements for the 'try' clause.
   * @param exceptStatements  Statements for the 'except' clause.
   * @param errorVariableName Name of the variable that will hold the value of the exception.
   * @param line              The line this statement starts on.
   * @param column            The column in the line this statement starts at.
   */
  public TryExceptStatement(final List<Statement> tryStatements, final List<Statement> exceptStatements,
                            final String errorVariableName, final int line, final int column) {
    super(line, column);
    this.tryStatements = tryStatements;
    this.exceptStatements = exceptStatements;
    this.errorVariableName = errorVariableName;
    this.ip = 0;
  }

  /**
   * Create a statement that represents try-except statement from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public TryExceptStatement(final CompoundTag tag) {
    super(tag);
    this.tryStatements = StatementTagHelper.deserializeStatementsList(tag, TRY_STATEMENTS_KEY);
    this.exceptStatements = StatementTagHelper.deserializeStatementsList(tag, EXCEPT_STATEMENTS_KEY);
    this.errorVariableName = tag.getString(ERROR_VAR_NAME_KEY);
    this.inExcept = tag.getBoolean(IN_EXCEPT_KEY);
    this.ip = tag.getInt(IP_KEY);
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    if (!this.inExcept) {
      int intialStackSize = callStack.size();
      try {
        StatementAction action = this.executeStatements(scope, this.tryStatements, callStack);
        if (action != StatementAction.PROCEED) {
          return action;
        }
      } catch (MCCodeRuntimeException e) {
        // Remove all stack elements that may have been added by statements in the "try" block
        while (callStack.size() != intialStackSize) {
          callStack.pop();
        }
        this.inExcept = true;
        this.ip = 0;
        MCMap errorMap = new MCMap();
        errorMap.put("error_type", e.getName());
        errorMap.put("data", e.getData());
        scope.declareVariable(new Variable(this.errorVariableName, false, false, false, true, errorMap));
      }
    }

    if (this.inExcept) {
      StatementAction action = this.executeStatements(scope, this.exceptStatements, callStack);
      if (action != StatementAction.WAIT) {
        scope.deleteVariable(this.errorVariableName, false);
      }
      if (action != StatementAction.PROCEED) {
        return action;
      }
    }

    this.inExcept = false;
    this.ip = 0;

    return StatementAction.PROCEED;
  }

  private StatementAction executeStatements(Scope scope, final List<Statement> statements, CallStack callStack) {
    while (this.ip < statements.size()) {
      Statement statement = statements.get(this.ip);
      StatementAction action = statement.execute(scope, callStack);
      if (action == StatementAction.EXIT_FUNCTION || action == StatementAction.WAIT
          || action == StatementAction.EXIT_LOOP || action == StatementAction.CONTINUE_LOOP) {
        if (action == StatementAction.WAIT) {
          if (statement instanceof WaitStatement) {
            this.ip++;
          }
        } else {
          this.inExcept = false;
          this.ip = 0;
        }
        return action;
      } else {
        this.ip++;
      }
    }

    return StatementAction.PROCEED;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    tag.putTag(TRY_STATEMENTS_KEY, StatementTagHelper.serializeStatementsList(this.tryStatements));
    tag.putTag(EXCEPT_STATEMENTS_KEY, StatementTagHelper.serializeStatementsList(this.exceptStatements));
    tag.putString(ERROR_VAR_NAME_KEY, this.errorVariableName);
    tag.putBoolean(IN_EXCEPT_KEY, this.inExcept);
    tag.putInt(IP_KEY, this.ip);
    return tag;
  }

  @Override
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append("try");
    if (!this.tryStatements.isEmpty()) {
      s.append(Utils.indentStatements(this.tryStatements));
    } else {
      s.append('\n');
    }
    s.append("except ").append(this.errorVariableName).append(" then");
    if (!this.exceptStatements.isEmpty()) {
      s.append(Utils.indentStatements(this.exceptStatements));
    } else {
      s.append('\n');
    }
    s.append("end");
    return s.toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    TryExceptStatement that = (TryExceptStatement) o;
    return this.inExcept == that.inExcept && this.ip == that.ip && this.tryStatements.equals(that.tryStatements)
        && this.exceptStatements.equals(that.exceptStatements) && this.errorVariableName.equals(that.errorVariableName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.tryStatements, this.exceptStatements, this.errorVariableName, this.inExcept, this.ip);
  }
}
