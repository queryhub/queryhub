package org.queryhub.field;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
   * @see #of(boolean, Integer, Integer...)
   * @since 0.1.0
   */
  static Multiple of(final Integer value, final Integer... values) {
    return of(Boolean.FALSE, value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param value  Field's boolean reference (for column parameters) or a single value (for {@code
   *               SET} / {@code WHERE} clauses).
   * @param values Field's following boolean references, with the same aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #of(boolean, BooleanSupplier, BooleanSupplier...)
   * @since 0.1.0
   */
  static Multiple of(final BooleanSupplier value, final BooleanSupplier... values) {
    return of(Boolean.FALSE, value, values);
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
   * @see #of(boolean, ChronoLocalDate, ChronoLocalDate...)
   * @since 0.1.0
   */
  static Multiple of(final ChronoLocalDate value, final ChronoLocalDate... values) {
    return of(Boolean.FALSE, value, values);
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
   * @see #of(boolean, ChronoLocalDateTime, ChronoLocalDateTime...)
   * @since 0.1.0
   */
  static Multiple of(final ChronoLocalDateTime value, final ChronoLocalDateTime... values) {
    return of(Boolean.FALSE, value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param value  Field's reference (for column parameters) or a single value (for {@code SET} /
   *               {@code WHERE} clauses).
   * @param values Field's following references, with the same aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #of(boolean, String, String...)
   * @since 0.1.0
   */
  static Multiple of(final String value, final String... values) {
    return of(Boolean.FALSE, value, values);
  }

  /**
   * Produces multiple fields.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   first result.
   * @param value      Field's ordinal reference (for column parameters) or a single value (for
   *                   {@code SET} / {@code WHERE} clauses).
   * @param values     Field's following ordinal references, with the same aforementioned
   *                   references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #stream(Object, Object[])
   * @see #process(boolean, Stream)
   * @since 0.1.0
   */
  static Multiple of(final boolean isDistinct, final Integer value, final Integer... values) {
    return process(isDistinct, stream(value, values).map(String::valueOf));
  }

  /**
   * Produces multiple fields.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   first result.
   * @param value      Field's boolean reference (for column parameters) or a single value (for
   *                   {@code SET} / {@code WHERE} clauses).
   * @param values     Field's following boolean references, with the same aforementioned
   *                   references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #stream(Object, Object[])
   * @see #process(boolean, Stream)
   * @since 0.1.0
   */
  static Multiple
  of(final boolean isDistinct, final BooleanSupplier value, final BooleanSupplier... values) {
    return process(isDistinct, stream(value, values).map(BooleanSupplier::getAsBoolean)
        .map(String::valueOf));
  }

  /**
   * Produces multiple fields.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   first result.
   * @param value      Field's <i>temporal</i> (date) reference (for column parameters) or a single
   *                   value (for {@code SET} / {@code WHERE} clauses).
   * @param values     Field's following <i>temporal</i> (date) references, with the same
   *                   aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #stream(Object, Object[])
   * @see #process(boolean, Stream)
   * @since 0.1.0
   */
  @SafeVarargs
  static <C extends ChronoLocalDate> Multiple
  of(final boolean isDistinct, final C value, final C... values) {
    return process(isDistinct, stream(value, values)
        .map(Static.LOCAL_DATE::format).map(String::valueOf));
  }

  /**
   * Produces multiple fields.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   first result.
   * @param value      Field's <i>temporal</i> (date/time) reference (for column parameters) or a
   *                   single value (for {@code SET} / {@code WHERE} clauses).
   * @param values     Field's following <i>temporal</i> (date/time) references, with the same
   *                   aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #stream(Object, Object[])
   * @see #process(boolean, Stream)
   * @since 0.1.0
   */
  @SafeVarargs
  static <C extends ChronoLocalDateTime> Multiple
  of(final boolean isDistinct, final C value, final C... values) {
    return process(isDistinct, stream(value, values)
        .map(Static.LOCAL_DATE_TIME::format).map(String::valueOf));
  }

  /**
   * Produces multiple fields.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   first result.
   * @param value      Field's reference (for column parameters) or a single value (for {@code SET}
   *                   / {@code WHERE} clauses).
   * @param values     Field's following references, with the same aforementioned references.
   * @return String representation of multiple fields, each one enclosed by single quotes, separated
   * by commas.
   * @see #stream(Object, Object[])
   * @see #process(boolean, Stream)
   * @since 0.1.0
   */
  static Multiple of(final boolean isDistinct, final String value, final String... values) {
    return process(isDistinct, stream(value, values));
  }

  // privates

  /**
   * Handles variadic parameters set into a stream of the given type.
   *
   * @param first     The first parameter. Required.
   * @param following The remaining variadic parameters. Optional.
   * @return the stream containing the given parameters, following the given parameters' input to
   * the method.
   * @see #process(boolean, Stream)
   * @since 0.1.0
   */
  @SafeVarargs
  private static <T> Stream<T> stream(final T first, final T... following) {
    final var build = Stream.<T>builder().add(first);
    Stream.of(following).forEach(build);
    return build.build();
  }

  /**
   * Process a stream of input of fields references.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed the operation
   *                   result.
   * @param stream     The {@link Stream} which contens is goging to be processed.
   * @return String representation of {@code Multiple} fields, each one enclosed by single quotes
   * and separated by commas,
   * @since 0.1.0
   */
  private static <T> Multiple process(final boolean isDistinct, final Stream<T> stream) {
    return () -> Static.DISTINCT.apply(isDistinct, stream
        .map(String::valueOf).map(Static.QUOTED)
        .collect(Collectors.joining(Static.COMMA)));
  }
}
