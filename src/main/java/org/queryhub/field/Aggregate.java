package org.queryhub.field;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstraction for statement parts which can receive the result of an aggregation operation in place
 * of a {@link Field.Single column field} reference directly.
 *
 * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Aggregate extends Field.Single {

  // Composite

  /**
   * Produces an aggregation operation to a given field.
   *
   * @param type  Aggregation's type.
   * @param value Field's reference (for column parameters) or a single value.
   * @return String representation of a single field, enclosed by single quotes, passed as parameter
   * to the prepended aggregation function.
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @see Aggregate#of(Type, boolean, String)
   * @since 0.1.0
   */
  static Aggregate of(final Type type, final String value) {
    return of(type, Boolean.FALSE, value);
  }

  /**
   * Produces an aggregation operation to a given field.
   *
   * @param type       Aggregation's type.
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   first result.
   * @param value      Field's reference (for column parameters) or a single value.
   * @return String representation of a single field, enclosed by single quotes, passed as parameter
   * to the prepended aggregation function.
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  static Aggregate of(final Type type, final boolean isDistinct, final String value) {
    return () -> type.fun.apply(Compiled.DISTINCT.apply(isDistinct, Field.of(value).get()));
  }

  // Composition

  /**
   * Produces an aggregation operation to a given field.
   *
   * @param type      Aggregation's type.
   * @param aggregate Another aggregate to be prepended, then allowing functional composition.
   * @return String representation of a single field, enclosed by single quotes, passed as parameter
   * to the prepended aggregation function.
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @see #of(Type, Aggregate)
   * @since 0.1.0
   */
  static Aggregate of(final Type type, final Aggregate aggregate) {
    return of(type, Boolean.FALSE, aggregate);
  }

  /**
   * Produces an aggregation operation to a given field.
   *
   * @param type       Aggregation's type.
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   first result.
   * @param aggregate  Another aggregate to be prepended, then allowing functional composition.
   * @return String representation of a single field, enclosed by single quotes, passed as parameter
   * to the prepended aggregation function.
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  static Aggregate of(final Type type, final boolean isDistinct, final Aggregate aggregate) {
    return () -> type.fun.apply(Compiled.DISTINCT.apply(isDistinct, aggregate.get()));
  }

  /**
   * Produces aggregation operations to a given fields.
   *
   * @param type   Aggregation's type.
   * @param first  Another aggregate to be prepended, then allowing functional composition.
   * @param others Other aggregates to be prepended, then allowing functional composition.
   * @return String representation of multiple fields, each one with enclosed by single quotes and
   * passed as parameter to the prepended aggregation function.
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @see #of(Type, boolean, Aggregate, Aggregate...)
   * @since 0.1.0
   */
  static Multiple of(final Type type, final Aggregate first, final Aggregate... others) {
    return of(type, Boolean.FALSE, first, others);
  }

  /**
   * Produces aggregation operations to a given fields.
   *
   * @param isDistinct Indicates if a keyword {@code DISTINCT} should be placed before the operation
   *                   first result.
   * @param first      Another aggregate to be prepended, then allowing functional composition.
   * @param others     Other aggregates to be prepended, then allowing functional composition.
   * @return String representation of multiple fields, each one with enclosed by single quotes and
   * passed as parameter to the prepended aggregation function.
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  static Multiple
  of(final Type type, final boolean isDistinct, final Aggregate first, final Aggregate... others) {
    return () -> type.fun.apply(Compiled.DISTINCT.apply(isDistinct, Stream
        .concat(Stream.of(first), Stream.of(others)).map(Supplier::get)
        .collect(Collectors.joining(Compiled.COMMA))));
  }

  /**
   * Represents the SQL aggregation operations. Each index should have a {@link String} enclosing
   * logic to the corresponding SQL function.
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Type {
    COUNT(s -> String.format("COUNT(%s)", s)),
    AVG(s -> String.format("AVG(%s)", s)),
    MIN(s -> String.format("MIN(%s)", s)),
    MAX(s -> String.format("MAX(%s)", s));
    private final Function<String, String> fun;

    Type(final Function<String, String> fun) {
      this.fun = fun;
    }
  }
}
