package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.CompoundTagListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for deserializing {@link Statement}s from tags.
 */
public final class StatementTagHelper {
  private static final Map<Integer, Function<CompoundTag, Statement>> STMT_PROVIDERS = new HashMap<>();

  static {
    STMT_PROVIDERS.put(ImportStatement.ID, ImportStatement::new);

    STMT_PROVIDERS.put(DeclareVariableStatement.ID, DeclareVariableStatement::new);
    STMT_PROVIDERS.put(DefineFunctionStatement.ID, DefineFunctionStatement::new);
    STMT_PROVIDERS.put(AssignVariableStatement.ID, AssignVariableStatement::new);
    STMT_PROVIDERS.put(SetItemStatement.ID, SetItemStatement::new);
    STMT_PROVIDERS.put(SetPropertyStatement.ID, SetPropertyStatement::new);

    STMT_PROVIDERS.put(DeleteVariableStatement.ID, DeleteVariableStatement::new);
    STMT_PROVIDERS.put(DeleteItemStatement.ID, DeleteItemStatement::new);

    STMT_PROVIDERS.put(ExpressionStatement.ID, ExpressionStatement::new);

    STMT_PROVIDERS.put(IfStatement.ID, IfStatement::new);
    STMT_PROVIDERS.put(WhileLoopStatement.ID, WhileLoopStatement::new);
    STMT_PROVIDERS.put(ForLoopStatement.ID, ForLoopStatement::new);
    STMT_PROVIDERS.put(TryExceptStatement.ID, TryExceptStatement::new);

    STMT_PROVIDERS.put(WaitStatement.ID, WaitStatement::new);

    STMT_PROVIDERS.put(BreakStatement.ID, BreakStatement::new);
    STMT_PROVIDERS.put(ContinueStatement.ID, ContinueStatement::new);
    STMT_PROVIDERS.put(ReturnStatement.ID, ReturnStatement::new);
  }

  /**
   * Return the statement corresponding to the given tag.
   *
   * @param tag The tag to deserialize.
   * @return The statement.
   * @throws IllegalArgumentException If no {@link Statement} correspond to the {@link Statement#ID_KEY} property.
   */
  public static Statement getStatementForTag(final CompoundTag tag) {
    int tagID = tag.getInt(Statement.ID_KEY);
    if (!STMT_PROVIDERS.containsKey(tagID)) {
      throw new IllegalArgumentException("Undefined statement ID: " + tagID);
    }
    return STMT_PROVIDERS.get(tagID).apply(tag);
  }

  /**
   * Deserialize a list of statements.
   *
   * @param tag The tag to extract statements from.
   * @param key The key where the list is located.
   * @return The statements list.
   */
  public static List<Statement> deserializeStatementsList(final CompoundTag tag, final String key) {
    List<Statement> statements = new ArrayList<>();
    for (CompoundTag t : tag.getList(key, TagType.COMPOUND_TAG_TYPE)) {
      statements.add(getStatementForTag(t));
    }
    return statements;
  }

  /**
   * Serialize a list of statements.
   *
   * @param statements The statements to serialize.
   * @return The tag list.
   */
  public static CompoundTagListTag serializeStatementsList(final List<? extends Statement> statements) {
    CompoundTagListTag statementsList = new CompoundTagListTag();
    statements.forEach(s -> statementsList.add(s.writeToTag()));
    return statementsList;
  }

  private StatementTagHelper() {
  }
}
