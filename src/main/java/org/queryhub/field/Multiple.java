package org.queryhub.field;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.queryhub.helper.Helper;
import org.queryhub.helper.Variadic;

/**
 * {@code Field} specialization which allows to return multiple fields.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Multiple extends Field {

  /**
   * Produces multiple fields.
   *
   * @param value  Field's ordinal reference (for column parameters) or a single value (for {@code
   *               SET} / {@code WHERE} clauses).
   * @param values Field's following ordinal references, with the same aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #process(Function)
   * @since 0.1.0
   */
  static Multiple of(final Integer value, final Integer... values) {
    return process(String::valueOf).apply(value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param value  Field's boolean reference (for column parameters) or a single value (for {@code
   *               SET} / {@code WHERE} clauses).
   * @param values Field's following boolean references, with the same aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #process(Function)
   * @since 0.1.0
   */
  static Multiple of(final boolean value, final Boolean... values) {
    return process(String::valueOf).apply(value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param value  Field's <i>temporal</i> (date) reference (for column parameters) or a single
   *               value (for {@code SET} / {@code WHERE} clauses).
   * @param values Field's following <i>temporal</i> (date) references, with the same aforementioned
   *               references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #process(Function)
   * @since 0.1.0
   */
  @SafeVarargs
  static <C extends ChronoLocalDate> Multiple of(final C value, final C... values) {
    return process(String::valueOf).apply(value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param value  Field's <i>temporal</i> (date/time) reference (for column parameters) or a single
   *               value (for {@code SET} / {@code WHERE} clauses).
   * @param values Field's following <i>temporal</i> (date/time) references, with the same
   *               aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #process(Function)
   * @since 0.1.0
   */
  @SafeVarargs
  static <C extends ChronoLocalDateTime> Multiple of(final C value, final C... values) {
    return process(Helper.LOCAL_DATE_TIME::format).apply(value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param value  Field's reference (for column parameters) or a single value (for {@code SET} /
   *               {@code WHERE} clauses).
   * @param values Field's following references, with the same aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #process(Function)
   * @since 0.1.0
   */
  static Multiple of(final String value, final String... values) {
    return process(Function.identity()).apply(value, values);
  }

  // privates

  /**
   * Helper method to handle multiple parameters for the public methods.
   *
   * @param mapper A mapper function to convert into a string representation.
   * @return A bi-function to apply on variadic parameters which are eventually handled by some
   * other method. This intends to widen function composition.
   * @since 0.1.0
   */
  private static <T> BiFunction<T, T[], Multiple> process(final Function<T, String> mapper) {
    return (a, b) -> () -> Variadic.asString(mapper.andThen(Mutator.PUT_SIMPLE_QUOTE)).apply(a, b);
  }
}
