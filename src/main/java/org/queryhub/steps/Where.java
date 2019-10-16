package org.queryhub.steps;

import org.queryhub.KeyWord;
import org.queryhub.field.Field;

/**
 * Represents {@code WHERE}-related building steps.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Where extends Terminal {

  /**
   * Builds the first {@code WHERE} string segment. Also, it should append the {@code WHERE} clause
   * to the statement under construction.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .where(Field.of("field_1"), Relation.EQ, Field.of("field_2"))
   *   (...)
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   WHERE 'field_1' = 'field_2'
   *   (...)
   *
   * }</pre>
   *
   * @param field1   The leading field to be set.
   * @param relation A relation between the first and second given fields.
   * @param field2   The trailing field to be set.
   * @return The builder's instance, which allows
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  Mixin where(final Field.Single field1, final Relation relation, final Field.Single field2);

  // TODO: Composite

  /**
   * Step that allows keep using a {@code WHERE} contract after a first call or finish the statement
   * building with a {@link Terminal} method call.
   *
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Mixin extends Terminal, Sort, Limit {

    /**
     * A {@code WHERE} step that can be used after a first clause composition.
     * <p>
     * The following implementation example:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   .where(Relation.AND, Field.of("field_1"), Relation.EQ, Field.of("field_2"))
     *   (...)
     *
     * }</pre>
     * <p>
     * should produce the output:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   AND 'field_1' = 'field_2'
     *   (...)
     *
     * }</pre>
     *
     * @param condition Logical condition to lead the segment under to be set.
     * @param field1    The leading field to be set.
     * @param relation  A relation between the first and second given fields.
     * @param field2    The trailing field to be set.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
     * @since 0.1.0
     */
    Where.Mixin
    where(final Condition condition, final Field.Single field1,
        final Relation relation, final Field.Single field2);
  }

  /**
   * Represents logical keywords that can be set between two segments in a {@code WHERE} statement.
   *
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Condition implements KeyWord {

    AND, OR;

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
   * Represents the keywords which can be used between two {@link Field fields} in the {@code WHERE}
   * clauses.
   *
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Relation implements KeyWord {

    /**
     * {@code LIKE} operator.
     */
    LIKE("LIKE"),
    /**
     * {@code =}: Equal operator.
     */
    EQ("="),
    /**
     * {@code >}: Greater than operator.
     */
    GT(">"),
    /**
     * {@code >=}: Greater than or equal operator.
     */
    GTE(">="),
    /**
     * {@code <}: Less than operator.
     */
    LT("<"),
    /**
     * {@code <=}: Less than or equal operator.
     */
    LTE("<="),
    /**
     * {@code !=}: Not equal operator.
     */
    NEQ("!=");

    final String symbol;

    Relation(final String symbol) {
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
}
