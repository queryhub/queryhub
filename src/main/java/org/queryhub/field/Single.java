package org.queryhub.field;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;
import java.util.function.BooleanSupplier;

/**
 * {@code Field} specialization which allows to return a individual field.
 *
 * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
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
   * @see #of(boolean, Integer)
   * @since 0.1.0
   */
  static Single of(final Integer value) {
    return of(Boolean.FALSE, value);
  }

  /**
   * Produces a single field.
   *
   * @param value Field's boolean reference (for column parameters) or a single value (for {@code
   *              SET} / {@code WHERE} clauses). Leading quotes is going to be removed. Cannot be
   *              {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, BooleanSupplier)
   * @since 0.1.0
   */
  static Single of(final BooleanSupplier value) {
    return of(Boolean.FALSE, value);
  }

  /**
   * Produces a single field.
   *
   * @param value Field's <i>temporal</i> (date) reference (for column parameters) or a single
   *              value (for {@code SET} / {@code WHERE} clauses). Leading quotes is going to be
   *              removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, ChronoLocalDate)
   * @since 0.1.0
   */
  static Single of(final ChronoLocalDate value) {
    return of(Boolean.FALSE, value);
  }

  /**
   * Produces a single field.
   *
   * @param value Field's <i>temporal</i> (date/time) reference (for column parameters) or a
   *              single value (for {@code SET} / {@code WHERE} clauses). Leading quotes is going
   *              to be removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, ChronoLocalDateTime)
   * @since 0.1.0
   */
  static Single of(final ChronoLocalDateTime value) {
    return of(Boolean.FALSE, value);
  }

  /**
   * Produces a single field.
   *
   * @param value Field's reference (for column parameters) or a single value (for {@code SET} /
   *              {@code WHERE} clauses). Leading quotes is going to be removed. Cannot be {@code
   *              null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, String)
   * @since 0.1.0
   */
  static Single of(final String value) {
    return of(Boolean.FALSE, value);
  }

  // Singles

  /**
   * Produces a single field.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the
   *                   operation result.
   * @param value      Field's ordinal reference (for column parameters) or a single value (for
   *                   {@code SET} / {@code WHERE} clauses). Leading quotes is going to be
   *                   removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, String)
   * @since 0.1.0
   */
  static Single of(final boolean isDistinct, final Integer value) {
    return of(isDistinct, Integer.toString(value));
  }

  /**
   * Produces a single field.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the
   *                   operation result.
   * @param value      Field's boolean reference (for column parameters) or a single value (for
   *                   {@code SET} / {@code WHERE} clauses). Leading quotes is going to be
   *                   removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, String)
   * @since 0.1.0
   */
  static Single of(final boolean isDistinct, final BooleanSupplier value) {
    return of(isDistinct, Boolean.toString(value.getAsBoolean()));
  }

  /**
   * Produces a single field.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the
   *                   operation result.
   * @param value      Field's <i>temporal</i> (date) reference (for column parameters) or a
   *                   single value (for {@code SET} / {@code WHERE} clauses). Leading quotes is
   *                   going to be removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, String)
   * @since 0.1.0
   */
  static Single of(final boolean isDistinct, final ChronoLocalDate value) {
    return of(isDistinct, Static.LOCAL_DATE.format(value));
  }

  /**
   * Produces a single field.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the
   *                   operation result.
   * @param value      Field's <i>temporal</i> (date/time) reference (for column parameters) or a
   *                   single value (for {@code SET} / {@code WHERE} clauses). Leading quotes is
   *                   going to be removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, String)
   * @since 0.1.0
   */
  static Single of(final boolean isDistinct, final ChronoLocalDateTime value) {
    return of(isDistinct, Static.LOCAL_DATE_TIME.format(value));
  }

  /**
   * Produces a single field.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the
   *                   operation result.
   * @param value      Field's reference (for column parameters) or a single value (for {@code
   *                   SET} / {@code WHERE} clauses). Leading quotes is going to be removed.
   *                   Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @since 0.1.0
   */
  static Single of(final boolean isDistinct, final String value) {
    return () -> Optional.of(value)
        .map(String::valueOf).map(Static.QUOTED).map(s -> Static.DISTINCT.apply(isDistinct, s))
        .map(Static.DOUBLE_QUOTE::matcher).map(m -> m.replaceAll(Static.EMPTY)).orElseThrow();
  }
}
