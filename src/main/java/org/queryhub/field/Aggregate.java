package org.queryhub.field;

import java.util.function.Function;
import java.util.function.UnaryOperator;
import org.queryhub.helper.Helper;

/**
 * Abstraction for statement parts which can receive the result of an aggregation operation in place
 * of a {@link Single column field} reference directly.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Aggregate extends Single {

  // Composite

  /**
   * Produces an aggregation operation to a given field.
   *
   * @param type  Aggregation's type.
   * @param value Field's reference (for column parameters) or a single value.
   * @return String representation of a single field, enclosed by single quotes, passed as parameter
   * to the prepended aggregation function.
   * @since 0.1.0
   */
  static Aggregate of(final Type type, final String value) {
    return () -> type.fun.apply(Single.of(value).get());
  }

  // Composition

  /**
   * Produces an aggregation operation to a given field.
   *
   * @param type      Aggregation's type.
   * @param aggregate Another aggregate to be prepended, then allowing functional composition.
   * @return String representation of a single field, enclosed by single quotes, passed as parameter
   * to the prepended aggregation function.
   * @since 0.1.0
   */
  static Aggregate of(final Type type, final Aggregate aggregate) {
    return () -> type.fun.apply(aggregate.get());
  }

  /**
   * Produces aggregation operations to a given fields.
   *
   * @param type  Aggregation's type.
   * @param first Another aggregate to be prepended, then allowing functional composition.
   * @param next  Other aggregates to be prepended, then allowing functional composition.
   * @return String representation of multiple fields, each one with enclosed by single quotes and
   * passed as parameter to the prepended aggregation function.
   * @since 0.1.0
   */
  static Multiple of(final Type type, final Aggregate first, final Aggregate... next) {
    return () -> Helper
        .variadicOf(Aggregate::get, String[]::new)
        .andThen(Helper.mapToString(Function.identity()))
        .andThen(type.fun)
        .apply(first, next);
  }

  /**
   * Represents the SQL aggregation operations. Each index should have a {@link String} enclosing
   * logic to the corresponding SQL function.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Type {
    COUNT(s -> "COUNT(" + s + ")"),
    AVG(s -> "AVG(" + s + ")"),
    MIN(s -> "MIN(" + s + ")"),
    MAX(s -> "MAX(" + s + ")"),
    DISTINCT(s -> "DISTINCT(" + s + ")"),
    ;
    private final UnaryOperator<String> fun;

    /**
     * Default constructor.
     *
     * @since 0.1.0
     */
    Type(final UnaryOperator<String> fun) {
      this.fun = fun;
    }
  }
}
