package org.queryhub.steps;

import org.queryhub.KeyWord;
import org.queryhub.field.Field;
import org.queryhub.field.Single;

/**
 * Represents the SQL building steps for sorting and grouping operations.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Sort extends Terminal {

  /**
   * Appends a string segment to the statement building which corresponds to a sorting / grouping
   * operation.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .sort(Type.GROUP_BY, Aggregate.of("field_1", Order.ASC))
   *   .sort(Type.ORDER_BY, Aggregate.of("field_2"), Aggregate.of("field_3", Order.DESC))
   *   (...)
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   GROUP BY 'field_1' ASC
   *   ORDER BY 'field_2' ASC, 'field_3' DESC
   *   (...)
   *
   * }</pre>
   *
   * @param type       Operation type.
   * @param aggregate  First sorting aggregate.
   * @param aggregates Other sorting aggregates. Optional.
   * @return Current statement building instance, intended to be chained to next building calls.
   * @since 0.1.0
   */
  Sort sort(final Type type, final Aggregate aggregate, final Aggregate... aggregates);

  /**
   * Represents sorting keywords that can be set after fields in a grouping/sorting operation.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
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
     * @since 0.1.0
     */
    @Override
    public final String keyWord() {
      return symbol;
    }
  }

  /**
   * Represents a field associated to a ordering in a aggregation operation.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Aggregate extends Field {

    /**
     * Produces an single field set in an grouping / ordering aggregation operation.
     *
     * @param single The field reference.
     * @param order  The operation's direction, in ascending or descending order.
     * @return String representation of a single field, enclosed by a grouping / sorting operation.
     * @since 0.1.0
     */
    static Aggregate of(final String single, final Order order) {
      return () -> Single.of(single).get() + Static.SPACE + order.keyWord();
    }

    /**
     * Produces an single field set in an grouping / ordering aggregation operation. Ascending
     * ordering is going to be set implicitly.
     *
     * @param single The field reference.
     * @return String representation of a single field, enclosed by a grouping operation and
     * ascending order.
     * @see Aggregate#of(String, Order)
     * @since 0.1.0
     */
    static Aggregate of(final String single) {
      return of(single, Order.ASC);
    }
  }
}
