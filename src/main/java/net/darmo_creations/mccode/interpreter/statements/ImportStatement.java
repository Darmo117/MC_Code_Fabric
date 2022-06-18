package net.darmo_creations.mccode.interpreter.statements;

import net.darmo_creations.mccode.interpreter.CallStackElement;
import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.exceptions.*;
import net.darmo_creations.mccode.interpreter.tags.CompoundTag;
import net.darmo_creations.mccode.interpreter.tags.StringListTag;
import net.darmo_creations.mccode.interpreter.tags.TagType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Statement that imports another module.
 */
public class ImportStatement extends Statement {
  public static final int ID = 0;

  private static final String NAME_KEY = "ModuleName";
  private static final String ALIAS_KEY = "Alias";

  private final List<String> moduleNamePath;
  private final String alias;

  /**
   * Create a statement that imports another module.
   *
   * @param moduleNamePath Module’s name.
   * @param alias          Alias to use instead of the full name. May be null.
   * @param line           The line this statement starts on.
   * @param column         The column in the line this statement starts at.
   */
  public ImportStatement(final List<String> moduleNamePath, final String alias, final int line, final int column) {
    super(line, column);
    this.moduleNamePath = new ArrayList<>(moduleNamePath);
    this.alias = alias;
  }

  /**
   * Create a statement that imports another module from a tag.
   *
   * @param tag The tag to deserialize.
   */
  public ImportStatement(final CompoundTag tag) {
    super(tag);
    this.moduleNamePath = tag.getList(NAME_KEY, TagType.STRING_TAG_TYPE).stream().toList();
    this.alias = tag.contains(ALIAS_KEY) ? tag.getString(ALIAS_KEY) : null;
  }

  @Override
  protected StatementAction executeWrapped(Scope scope, CallStack callStack) {
    String name = this.getModulePath();
    Program module;
    try {
      module = scope.getProgram().getProgramManager().loadProgram(name, null, true);
    } catch (SyntaxErrorException e) {
      CallStack c = new CallStack();
      c.push(new CallStackElement(name, scope.getName(), e.getLine(), e.getColumn()));
      throw new ImportException(scope, name, c, e);
    } catch (ProgramStatusException e) {
      callStack.push(new CallStackElement(scope.getProgram().getName(), scope.getName(), this.getLine(), this.getColumn()));
      throw new MCCodeRuntimeException(scope, null, this.getLine(), this.getColumn(), e.getTranslationKey(), e.getProgramName());
    }
    try {
      module.execute();
    } catch (MCCodeException e) {
      throw new ImportException(scope, name, module.getCallStack(), e);
    }
    scope.declareVariable(new Variable(this.alias != null ? this.alias : name.replace('.', '_'),
        false, false, false, true, module));
    return StatementAction.PROCEED;
  }

  @Override
  public int getID() {
    return ID;
  }

  @Override
  public CompoundTag writeToTag() {
    CompoundTag tag = super.writeToTag();
    StringListTag list = new StringListTag();
    this.moduleNamePath.forEach(list::add);
    tag.putTag(NAME_KEY, list);
    if (this.alias != null) {
      tag.putString(ALIAS_KEY, this.alias);
    }
    return tag;
  }

  @Override
  public String toString() {
    return String.format("import %s%s;",
        String.join(".", this.moduleNamePath), this.alias != null ? (" as " + this.alias) : "");
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || this.getClass() != o.getClass()) {
      return false;
    }
    ImportStatement that = (ImportStatement) o;
    return this.moduleNamePath.equals(that.moduleNamePath) && Objects.equals(this.alias, that.alias);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.moduleNamePath, this.alias);
  }

  /**
   * Return the formatted path of the imported module.
   */
  public String getModulePath() {
    return String.join(".", this.moduleNamePath);
  }
}
