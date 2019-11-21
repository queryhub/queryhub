package org.queryhub.field;

import static org.queryhub.helper.Variadic.asString;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.function.Function;
import org.queryhub.helper.Helper;
import org.queryhub.helper.Mutator;

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
   * @since 0.1.0
   */
  static Multiple of(final long value, final Number... values) {
    return () -> asString((Function<Number, String>) String::valueOf).apply(value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param value  Field's boolean reference (for column parameters) or a single value (for {@code
   *               SET} / {@code WHERE} clauses).
   * @param values Field's following boolean references, with the same aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @since 0.1.0
   */
  static Multiple of(final boolean value, final Boolean... values) {
    return () -> asString((Function<Boolean, String>) String::valueOf).apply(value, values);
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
   * @since 0.1.0
   */
  static Multiple of(final ChronoLocalDate value, final ChronoLocalDate... values) {
    return () -> asString(Mutator.ADD_BACK_TICKS.compose(String::valueOf)).apply(value, values);
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
   * @since 0.1.0
   */
  static Multiple of(final ChronoLocalDateTime value, final ChronoLocalDateTime... values) {
    return () -> asString(Mutator.ADD_BACK_TICKS.compose(Helper.LOCAL_DATE_TIME::format))
        .apply(value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param value  Field's reference (for column parameters) or a single value (for {@code SET} /
   *               {@code WHERE} clauses).
   * @param values Field's following references, with the same aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @since 0.1.0
   */
  static Multiple of(final CharSequence value, final CharSequence... values) {
    return () -> asString(Mutator.ADD_BACK_TICKS.compose(String::valueOf))
        .andThen(Mutator.REMOVE_REDUNDANT_DOUBLE_QUOTES).apply(value, values);
  }
}
