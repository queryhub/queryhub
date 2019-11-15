package org.queryhub.field;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import org.queryhub.helper.Helper;

/**
 * {@code Field} specialization which allows to return a individual field.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Single extends Field {

  /**
   * Produces a single field.
   *
   * @param value Field's ordinal reference (for column parameters) or a single value (for {@code
   *              SET} / {@code WHERE} clauses). Leading quotes is going to be removed. Cannot be
   *              {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(String)
   * @since 0.1.0
   */
  static Single of(final Long value) {
    return of(Long.toString(value));
  }

  /**
   * Produces a single field.
   *
   * @param value Field's boolean reference (for column parameters) or a single value (for {@code
   *              SET} / {@code WHERE} clauses). Leading quotes is going to be removed. Cannot be
   *              {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(String)
   * @since 0.1.0
   */
  static Single of(final boolean value) {
    return of(String.valueOf(value));
  }

  /**
   * Produces a single field.
   *
   * @param value Field's <i>temporal</i> (date) reference (for column parameters) or a single value
   *              (for {@code SET} / {@code WHERE} clauses). Leading quotes is going to be removed.
   *              Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(String)
   * @since 0.1.0
   */
  static Single of(final ChronoLocalDate value) {
    return of(String.valueOf(value));
  }

  /**
   * Produces a single field.
   *
   * @param value Field's <i>temporal</i> (date/time) reference (for column parameters) or a single
   *              value (for {@code SET} / {@code WHERE} clauses). Leading quotes is going to be
   *              removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(String)
   * @since 0.1.0
   */
  static Single of(final ChronoLocalDateTime value) {
    return of(Helper.LOCAL_DATE_TIME.format(value));
  }

  /**
   * Produces a single field.
   *
   * @param value Field's reference (for column parameters) or a single value (for {@code SET} /
   *              {@code WHERE} clauses). Leading quotes is going to be removed. Cannot be {@code
   *              null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @since 0.1.0
   */
  static Single of(final String value) {
    return () -> Mutator.PUT_SIMPLE_QUOTE
        .andThen(Mutator.REMOVE_REDUNDANT_DOUBLE_QUOTES)
        .apply(value);
  }
}
