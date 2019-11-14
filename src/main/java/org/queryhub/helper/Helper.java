package org.queryhub.helper;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import org.queryhub.field.Field;

/**
 * Stateless class for grouping the package's utility resources.
 * <p>
 * Preferably, for design purposes, only {@code static} members should be written here.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public final class Helper {

  public static final String EMPTY = "";
  public static final String SPACE = " ";

  private static final String SPACED_COMMA = ", ";

  public static final DateTimeFormatter LOCAL_DATE_TIME = ofPattern("YYYY-MM-dd hh:mm:ss");
  public static final Pattern DOUBLE_QUOTE = Pattern.compile("\"");

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

  // Functors

  /**
   * Combines variadic arguments to the string form of theirs.
   *
   * @param mapper A mapping function which converts one type to a string representation.
   * @param <T>    Inferred type shared between parameters.
   * @return A string function which accepts an array.
   * @since 0.1.0
   */
  public static <T> Function<T[], String> mapToString(final Function<T, String> mapper) {
    return arr -> {
      final var joiner = new StringJoiner(SPACED_COMMA);
      for (final var o : arr) {
        mapper.andThen(joiner::add).apply(o);
      }
      return joiner.toString();
    };
  }

  /**
   * Combines variadic arguments into one array wrapper.
   *
   * @param <T>    Inferred type for the variadic parameters.
   * @param <U>    Inferred type for the returning array.
   * @param mapper A mapping function which converts one type to another.
   * @param generator    A generator function to create a wrapping array for the handled items.
   * @return A bi-function to apply on variadic parameters which are eventually going to be handled
   * by some other method. This intends to widen function composition.
   * @since 0.1.0
   */
  public static <T, U> BiFunction<T, T[], U[]>
  variadicOf(final Function<T, U> mapper, final IntFunction<U[]> generator) {
    return (t, ts) -> {
      final var arr = generator.apply(ts.length + 1);
      for (var i = 0; i < ts.length + 1; i++) {
        arr[i] = mapper.apply(i == 0 ? t : ts[i - 1]);
      }
      return arr;
    };
  }

  // Other

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
   * Encloses with single quotes.
   *
   * @param value A string.
   * @return The given string enclosed by single quotes.
   * @see <a href="https://www.iso.org/obp/ui/#iso:std:iso-iec:9075:-11:ed-4:v1:en">Syntax
   * reference</a>
   */
  public static String quoted(final String value) {
    return "'" + value + "'";
  }
}
