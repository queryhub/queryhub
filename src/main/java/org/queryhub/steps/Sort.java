package org.queryhub.steps;

import org.queryhub.KeyWord;
import org.queryhub.field.Field;

/**
 * Represents sorting and grouping-related building steps.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Sort extends Terminal {

  /**
   * Applies a sorting / grouping operation to statement building.
   *
   * @param type       Operation type.
   * @param aggregate  First sorting aggregate.
   * @param aggregates Other sorting aggregates. Optional.
   * @return Current statement building instance, intended to be chained to next building calls.
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  Sort sort(final Type type, final Aggregate aggregate, final Aggregate... aggregates);

  /**
   * Represents sorting keywords that can be set after fields in a grouping/sorting operation.
   *
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Order implements KeyWord {

    /**
     * Ascending order. Corresponds to the keyword with the same name.
     */
    ASC,

    /**
     * Descending order. Corresponds to the keyword with the same name.
     */
    DESC;

    /**
     * {@inheritDoc}
     *
     * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
     * @since 0.1.0
     */
    @Override
    public final String keyWord() {
      return name();
    }
  }

  /**
   * Represents sorting keywords that can be set after a {@code WHERE} statement segment.
   *
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Type implements KeyWord {

    /**
     * {@code GROUP BY} operator.
     */
    GROUP_BY("GROUP BY"),

    /**
     * {@code ORDER BY} operator.
     */
    ORDER_BY("ORDER BY");

    final String symbol;

    Type(final String symbol) {
      this.symbol = symbol;
    }

    /**
     * {@inheritDoc}
     *
     * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
     * @since 0.1.0
     */
    @Override
    public final String keyWord() {
      return symbol;
    }
  }

  /**
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Aggregate extends Field {

    /**
     * Produces an single field set in an aggregation operation.
     *
     * @return String representation of a single field, enclosed by a grouping / sorting operation.
     * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
     * @since 0.1.0
     */
    static Aggregate of(final String single, final Order order) {
      return () -> Field.of(single).get() + Compiled.SPACE + order.name();
    }

    /**
     * Produces an single field set in an aggregation operation.
     *
     * @return String representation of a single field, enclosed by a grouping operation and
     * ascending order.
     * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
     * @see Aggregate#of(String, Order)
     * @since 0.1.0
     */
    static Aggregate of(final String single) {
      return of(single, Order.ASC);
    }
  }
}
