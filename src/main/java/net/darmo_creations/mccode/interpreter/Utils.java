package net.darmo_creations.mccode.interpreter;

import net.darmo_creations.mccode.interpreter.statements.Statement;
import net.darmo_creations.mccode.interpreter.types.MCList;
import net.darmo_creations.mccode.interpreter.types.MCMap;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Utility functions for interpreter-related classes.
 */
public final class Utils {
  private static final String INDENT = "  ";

  /**
   * Stringify the given list of statements and indents them.
   * Resulting string starts and ends with a line return.
   *
   * @param statements The statements to format.
   * @return The stringified and indented statements.
   */
  public static String indentStatements(final List<Statement> statements) {
    if (statements.isEmpty()) {
      return "";
    }
    StringJoiner code = new StringJoiner("\n" + INDENT, "\n" + INDENT, "\n");
    for (Statement s : statements) {
      code.add(String.join("\n" + INDENT, s.toString().split("\n")));
    }
    return code.toString();
  }

  /**
   * Performs a true modulo operation using the mathematical definition of "a mod b".
   *
   * @param a Value to get the modulo of.
   * @param b The divisor.
   * @return a mod b
   * @throws ArithmeticException If b = 0.
   */
  public static double trueModulo(final double a, final double b) {
    if (b == 0) {
      throw new ArithmeticException("/ by 0");
    }
    return ((a % b) + b) % b;
  }

  /**
   * Escape all special characters from the given string.
   * <p>
   * Adds double quotes, escapes '"', '\' and '\n' characters.
   *
   * @param s The string to escape.
   * @return The escaped string.
   */
  public static String escapeString(final String s) {
    return String.format("\"%s\"", s.replaceAll("([\"\\\\])", "\\\\$1").replace("\n", "\\n"));
  }

  /**
   * Unescape all escaped special characters from the given string.
   * <p>
   * Removes trailing double quotes, unescapes '\"', '\\' and '\\n' sequences.
   *
   * @param s The string to unescape.
   * @return The unescaped string.
   */
  public static String unescapeString(final String s) {
    return s.substring(1, s.length() - 1).replaceAll("\\\\([\"\\\\])", "$1").replace("\\n", "\n");
  }

  /**
   * Converts a map to a data tag string (SNBT).
   *
   * @param map A map. May be null.
   * @return The resulting string; an empty string if the map is null or empty.
   */
  public static String formatDataTags(final MCMap map) {
    if (map == null || map.isEmpty()) {
      return "";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    int i = 0;
    for (Map.Entry<String, Object> e : map.entrySet()) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(e.getKey()).append(":").append(serializeToDataTag(e.getValue()));
      i++;
    }
    sb.append("}");
    return sb.toString();
  }

  /**
   * Returns the SNBT representation of an object.
   */
  private static String serializeToDataTag(final Object o) {
    if (o instanceof String s) {
      return Utils.escapeString(s);
    } else if (o instanceof Number) {
      return o.toString();
    } else if (o instanceof MCList l) {
      return serializeList(l);
    } else if (o instanceof MCMap m) {
      return formatDataTags(m);
    } else {
      return String.valueOf(o);
    }
  }

  /**
   * Serializes a list to a SNBT string. Supports byte, int and long arrays.
   */
  private static String serializeList(final MCList l) {
    boolean isArray = false;
    Function<Object, String> serializer = Utils::serializeToDataTag;
    // Check if the list should be treated as a byte, int or long array
    if (l.size() != 0 && l.get(0) instanceof String prefix && l.stream().skip(1).allMatch(e -> e instanceof Long)) {
      serializer = switch (prefix) {
        case "B;" -> {
          isArray = true;
          yield e -> e + "b";
        }
        case "I;" -> {
          isArray = true;
          yield e -> e + "";
        }
        case "L;" -> {
          isArray = true;
          yield e -> e + "l";
        }
        default -> Utils::serializeToDataTag;
      };
    }
    Stream<Object> stream = l.stream();
    if (isArray) {
      // Skip array type
      stream = l.stream().skip(1);
    }
    String res = stream.map(serializer).collect(Collectors.joining(",", "[", "]"));
    if (isArray) {
      // Insert array type in first position, right after opening bracket
      res = res.charAt(0) + l.get(0).toString() + res.substring(1);
    }
    return res;
  }

  /**
   * Returns the JSON representation of an object.
   *
   * @param o An object. May be null.
   * @return The JSON representation of that object.
   */
  public static String serializeJSON(final Object o) {
    if (o instanceof MCMap m) {
      return mapToJSON(m);
    } else if (o instanceof MCList list) {
      return list.stream().map(Utils::serializeJSON).collect(Collectors.joining(",", "[", "]"));
    } else if (o instanceof String s) {
      return Utils.escapeString(s);
    } else {
      return String.valueOf(o);
    }
  }

  /**
   * Returns the JSON representation of a map.
   *
   * @param map A map. May be null.
   * @return The JSON representation of that map. An empty JSON object string is returned if the map is null.
   */
  private static String mapToJSON(final MCMap map) {
    if (map == null || map.isEmpty()) {
      return "{}";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("{");
    int i = 0;
    for (Map.Entry<String, Object> e : map.entrySet()) {
      if (i > 0) {
        sb.append(',');
      }
      sb.append(Utils.escapeString(e.getKey())).append(':').append(serializeJSON(e.getValue()));
      i++;
    }
    sb.append("}");
    return sb.toString();
  }

  private Utils() {
  }
}
