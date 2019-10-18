package org.queryhub.field;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * General representation for a SQL field.
 * <p>
 * The abstraction is aimed to fill the gaps between native keywords in the SQL statements. This
 * means that it should produce valid string segments according to the place where it should be set.
 * Consequently, there will be methods which can accept instances to represent individual or
 * multiple fields, such as {@link org.queryhub.Query#select(Single, Field)} or {@link
 * org.queryhub.Query#update(Single)}.
 * <p>
 * Factories should accept exclusively the cro types in SQL specification, such as {@link String},
 * {@link Integer}, {@link Boolean} and JDK' API present in {@code java.time} package such as {@link
 * ChronoLocalDate} and {@link ChronoLocalDateTime}. By the fact of this abstraction should be a
 * {@link Supplier} specialization, it should return  {@link String} instances be lazily evaluated,
 * as the lambda structures usually does.
 *
 * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Field extends Supplier<String> {

  /**
   * {@code Field} specialization which allows to return a individual field.
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Single extends Field {

  }

  /**
   * {@code Field} specialization which allows to return multiple fields.
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Multiple extends Field {

  }

  enum Constants {

    /**
     * Short-hand for wildcard statement parameters (specifically for SELECT statements). Should be
     * a constant in order to save memory.
     */
    ALL(() -> "'*'"),

    /**
     * Short-hand for input-based statement parameters. Should be a constant in order to save
     * memory.
     */
    VARIABLE(() -> "'?'");

    private final Field field;

    Constants(final Field field) {
      this.field = field;
    }

    public final Field getField() {
      return field;
    }
  }

  // Overloaded Singles

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
   * @param value Field's <i>temporal</i> (date) reference (for column parameters) or a single value
   *              (for {@code SET} / {@code WHERE} clauses). Leading quotes is going to be removed.
   *              Cannot be {@code null}.
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
   * @param value Field's <i>temporal</i> (date/time) reference (for column parameters) or a single
   *              value (for {@code SET} / {@code WHERE} clauses). Leading quotes is going to be
   *              removed. Cannot be {@code null}.
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
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   result.
   * @param value      Field's ordinal reference (for column parameters) or a single value (for
   *                   {@code SET} / {@code WHERE} clauses). Leading quotes is going to be removed.
   *                   Cannot be {@code null}.
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
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   result.
   * @param value      Field's boolean reference (for column parameters) or a single value (for
   *                   {@code SET} / {@code WHERE} clauses). Leading quotes is going to be removed.
   *                   Cannot be {@code null}.
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
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   result.
   * @param value      Field's <i>temporal</i> (date) reference (for column parameters) or a single
   *                   value (for {@code SET} / {@code WHERE} clauses). Leading quotes is going to
   *                   be removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, String)
   * @since 0.1.0
   */
  static Single of(final boolean isDistinct, final ChronoLocalDate value) {
    return of(isDistinct, Compiled.LOCAL_DATE.format(value));
  }

  /**
   * Produces a single field.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   result.
   * @param value      Field's <i>temporal</i> (date/time) reference (for column parameters) or a
   *                   single value (for {@code SET} / {@code WHERE} clauses). Leading quotes is
   *                   going to be removed. Cannot be {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @see #of(boolean, String)
   * @since 0.1.0
   */
  static Single of(final boolean isDistinct, final ChronoLocalDateTime value) {
    return of(isDistinct, Compiled.LOCAL_DATE_TIME.format(value));
  }

  /**
   * Produces a single field.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   result.
   * @param value      Field's reference (for column parameters) or a single value (for {@code SET}
   *                   / {@code WHERE} clauses). Leading quotes is going to be removed. Cannot be
   *                   {@code null}.
   * @return String representation of a single field, enclosed by single quotes.
   * @since 0.1.0
   */
  static Single of(final boolean isDistinct, final String value) {
    return () -> Optional.of(value)
        .map(String::valueOf).map(Compiled.QUOTED).map(s -> Compiled.DISTINCT.apply(isDistinct, s))
        .map(Compiled.DOUBLE_QUOTE::matcher).map(m -> m.replaceAll(Compiled.EMPTY)).orElseThrow();
  }

  // Overloaded Multiples

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

  // Multiples

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
        .map(Compiled.LOCAL_DATE::format).map(String::valueOf));
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
        .map(Compiled.LOCAL_DATE_TIME::format).map(String::valueOf));
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
    return () -> Compiled.DISTINCT.apply(isDistinct, stream
        .map(String::valueOf).map(Compiled.QUOTED)
        .collect(Collectors.joining(Compiled.COMMA)));
  }
}
