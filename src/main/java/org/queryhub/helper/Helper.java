package org.queryhub.helper;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.queryhub.field.Field;

/**
 * Stateless class for grouping the package's utility resources.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public final class Helper {

  public static final String COMMA = ", ";
  public static final String EMPTY = "";
  public static final String SPACE = " ";

  private static final String SPACED_COMMA = ", ";

  public static final DateTimeFormatter LOCAL_DATE = ofPattern("YYYY-MM-dd");
  public static final DateTimeFormatter LOCAL_DATE_TIME = ofPattern("YYYY-MM-dd hh:mm:ss");
  public static final Pattern DOUBLE_QUOTE = Pattern.compile("\"");

  public static final BiFunction<Boolean, String, String> DISTINCT =
      (b, s) -> Boolean.TRUE.equals(b) ? "DISTINCT " + s : s;

  /**
   * Non-visible constructor.
   *
   * @since 0.1.0
   */
  private Helper() {
  }

  /**
   * Throws an exception conditionally to a given condition.
   *
   * @param exception A supplied exception.
   * @param condition The logical condition which may trigger the supplied exception.
   * @throws RuntimeException if the given condition is not satisfied.
   * @since 0.1.0
   */
  public static void throwIf(final Supplier<RuntimeException> exception, final boolean condition) {
    if (condition) {
      throw exception.get();
    }
  }

  /**
   * Combines variadic arguments' string forms.
   *
   * @param ts  Parameters to be joined.
   * @param <T> Inferred type shared between parameters.
   * @return A ongoing stream of the given parameters' type.
   * @since 0.1.0
   */
  public static <T> String combine(final T t, final T[] ts, final Function<T, String> mapper) {
    final var joiner = new StringJoiner(SPACED_COMMA).add(mapper.apply(t));
    for (final T o : ts) {
      joiner.add(mapper.apply(o));
    }
    return joiner.toString();
  }

  /**
   * Converts into a field, with no special modification.
   *
   * @param o A object.
   * @return A field containing the given object in the string value.
   * @since 0.1.0
   */
  public static Field asField(final Object o) {
    return () -> Objects.toString(o);
  }

  /**
   * https://www.iso.org/obp/ui/#iso:std:iso-iec:9075:-11:ed-4:v1:en
   */
  public static String quoted(final String value) {
    return "'" + value + "'";
  }
}
