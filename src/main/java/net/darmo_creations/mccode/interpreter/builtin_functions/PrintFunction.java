package net.darmo_creations.mccode.interpreter.builtin_functions;

import net.darmo_creations.mccode.interpreter.*;
import net.darmo_creations.mccode.interpreter.annotations.Function;
import net.darmo_creations.mccode.interpreter.exceptions.EvaluationException;
import net.darmo_creations.mccode.interpreter.type_wrappers.AnyType;
import net.darmo_creations.mccode.interpreter.type_wrappers.NullType;
import net.darmo_creations.mccode.interpreter.type_wrappers.StringType;
import net.darmo_creations.mccode.interpreter.types.BuiltinFunction;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A function that prints raw text into the chat.
 */
@Function(parametersDoc = {
    "An entity selector that targets the players to send the message to.",
    "The values to print. The values after the first argument are printed with a single space character between each."
},
    doc = "Prints a value in the chat.")
public class PrintFunction extends BuiltinFunction {
  /**
   * Create a function that prints raw text into the chat.
   */
  public PrintFunction() {
    super("print", ProgramManager.getTypeInstance(NullType.class), false, true,
        new Parameter("targets", ProgramManager.getTypeInstance(StringType.class)),
        new Parameter("message", ProgramManager.getTypeInstance(AnyType.class), true));
  }

  @Override
  public Object apply(final Scope scope, CallStack callStack) {
    String selector = this.getParameterValue(scope, 0);
    List<ServerPlayerEntity> players = Utils.getSelectedPlayers(scope.getProgram().getProgramManager().getWorld(), selector);
    if (players == null) {
      throw new EvaluationException(scope, "mccode.interpreter.error.invalid_player_selector", selector);
    }
    Object[] message = this.getParameterValue(scope, 1);
    Text text = new LiteralText(Arrays.stream(message).map(o -> ProgramManager.getTypeForValue(o).toString(o)).collect(Collectors.joining(" ")));
    players.forEach(player -> player.sendMessage(text, false));
    return null;
  }
}
