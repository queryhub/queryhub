package org.queryhub.steps;

import org.queryhub.KeyWord;
import org.queryhub.field.Field;

/**
 * Represents the SQL building steps for a {@code WHERE} operation.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Where extends Terminal {

  /**
   * Appends the first {@code WHERE} operation's string segment to the statement building.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .where(Field.of("field_1"), Field.of("field_2", "field_3", "field_4"))
   *   (...)
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   WHERE 'field_1' IN ('field_2', 'field_'3, 'field_4')
   *   (...)
   *
   * }</pre>
   *
   * @param field  The leading field to be set.
   * @param fields Trailing fields to be set.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @since 0.1.0
   */
  Mixin where(final Field.Single field, final Field fields);

  /**
   * Appends the first {@code WHERE} operation's string segment to the statement building.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .where(Field.of("field_1"), Relation.LTE, Field.of("field_2"))
   *   (...)
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   WHERE 'field_1' <= 'field_2'
   *   (...)
   *
   * }</pre>
   *
   * @param field1   The leading field to be set.
   * @param relation A relation between the first and second given fields.
   * @param field2   The trailing field to be set.
   * @return The builder's instance, which allows
   * @since 0.1.0
   */
  Mixin where(final Field.Single field1, final Relation relation, final Field.Single field2);

  /**
   * Appends the first {@code WHERE} operation's string segment to the statement building.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .where(Field.of("field_1"), Query.select(Field.of("table_1"), Field.of("field_2", "field_3")))
   *   (...)
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   WHERE 'field_1' IN (SELECT 'field_2', 'field_3', FROM 'table_1')
   *   (...)
   *
   * }</pre>
   *
   * @param reference The reference field in the comparison.
   * @param clause    {@code SELECT} statement which the returned columns will be compared to the
   *                  given filed parameter.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @since 0.1.0
   */
  Mixin where(final Field.Single reference, final Select clause);

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
     * Appends another {@code WHERE} operation's string segment to the statement building.
     * <p>
     * A {@code WHERE} step that can be used after a first clause composition.
     * <p>
     * The following implementation example:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   .where(Condition.OR, Field.of("field_1"), Field.of("field_2", "field_3", "field_4"))
     *   (...)
     *
     * }</pre>
     * <p>
     * should produce the output:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   OR 'field_1' IN ('field_2', 'field_'3, 'field_4')
     *   (...)
     *
     * }</pre>
     *
     * @param condition Logical condition to lead the segment.
     * @param field     The leading field in statement's segment.
     * @param fields    Trailing fields in statement's segment.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Where.Mixin where(final Condition condition, final Field.Single field, final Field fields);

    /**
     * Appends another {@code WHERE} operation's string segment to the statement building.
     * <p>
     * The following implementation example:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   .where(Relation.AND, Field.of("field_1"), Relation.GTE, Field.of("field_2"))
     *   (...)
     *
     * }</pre>
     * <p>
     * should produce the output:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   AND 'field_1' >= 'field_2'
     *   (...)
     *
     * }</pre>
     *
     * @param condition Logical condition to lead the segment under to be set.
     * @param field1    The leading field in statement's segment.
     * @param relation  A relation between the first and second given fields.
     * @param field2    The trailing field in statement's segment.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Where.Mixin
    where(final Condition condition, final Field.Single field1,
        final Relation relation, final Field.Single field2);

    /**
     * Appends another {@code WHERE} operation's string segment to the statement building.
     * <p>
     * The following implementation example:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   .where(Condition.AND, Field.of("field_1"),
     *     Query.select(Field.of("table_2"), Field.of("field_2")))
     *   (...)
     *
     * }</pre>
     * <p>
     * should produce the output:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   AND 'field_1' IN (SELECT 'field_2' FROM 'table_2')
     *   (...)
     *
     * }</pre>
     *
     * @param condition Logical condition to lead the segment under to be set.
     * @param reference The reference field in the comparison.
     * @param clause    {@code SELECT} statement which the returned columns will be compared to the
     *                  given filed parameter.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Where.Mixin where(final Condition condition, final Field.Single reference, final Select clause);
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
   * @since 0.1.0
   */
  enum Relation implements KeyWord {

    /**
     * {@code LIKE} operator.
     */
    LIKE("LIKE"),
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
     * @since 0.1.0
     */
    @Override
    public final String keyWord() {
      return symbol;
    }
  }
}
