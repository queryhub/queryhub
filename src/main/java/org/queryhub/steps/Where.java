package org.queryhub.steps;

import org.queryhub.KeyWord;
import org.queryhub.field.Field;
import org.queryhub.field.Single;

/**
 * Represents the SQL building steps for a {@code WHERE} operation.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
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
  Mixin where(final Single field, final Field fields);

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
  Mixin where(final Single field1, final Relation relation, final Single field2);

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
  Mixin where(final Single reference, final Select clause);

  // TODO: Composite

  /**
   * Step that allows keep using a {@code WHERE} contract after a first call or finish the statement
   * building with a {@link Terminal} method call.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface After extends Terminal {

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
     *   .and(Condition.OR, Field.of("field_1"), Field.of("field_2", "field_3", "field_4"))
     *   (...)
     *
     * }</pre>
     * <p>
     * should produce the output:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   AND 'field_1' IN ('field_2', 'field_'3, 'field_4')
     *   (...)
     *
     * }</pre>
     *
     * @param field  The leading field in statement's segment.
     * @param fields Trailing fields in statement's segment.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Mixin and(final Single field, final Field fields);

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
     *   .or(Field.of("field_1"), Field.of("field_2", "field_3", "field_4"))
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
     * @param field  The leading field in statement's segment.
     * @param fields Trailing fields in statement's segment.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Mixin or(final Single field, final Field fields);

    /**
     * Appends another {@code WHERE} operation's string segment to the statement building.
     * <p>
     * The following implementation example:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   .and(Field.of("field_1"), Relation.GTE, Field.of("field_2"))
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
     * @param field1   The leading field in statement's segment.
     * @param relation A relation between the first and second given fields.
     * @param field2   The trailing field in statement's segment.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Mixin and(final Single field1, final Relation relation, final Single field2);

    /**
     * @param field1   The leading field in statement's segment.
     * @param relation A relation between the first and second given fields.
     * @param field2   The trailing field in statement's segment.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Mixin or(final Single field1, final Relation relation, final Single field2);

    /**
     * Appends an {@code AND} operation's string segment to the statement building.
     * <p>
     * The following implementation example:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   .and(Field.of("field_1"), Query.select(Field.of("table_2"), Field.of("field_2")))
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
     * @param reference The reference field in the comparison.
     * @param clause    {@code SELECT} statement which the returned columns will be compared to the
     *                  given filed parameter.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Mixin and(final Single reference, final Select clause);

    /**
     * Appends an {@code AND} operation's string segment to the statement building.
     * <p>
     * The following implementation example:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   .or(Field.of("field_1"), Query.select(Field.of("table_2"), Field.of("field_2")))
     *   (...)
     *
     * }</pre>
     * <p>
     * should produce the output:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   OR 'field_1' IN (SELECT 'field_2' FROM 'table_2')
     *   (...)
     *
     * }</pre>
     *
     * @param reference The reference field in the comparison.
     * @param clause    {@code SELECT} statement which the returned columns will be compared to the
     *                  given filed parameter.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Mixin or(final Single reference, final Select clause);
  }

  /**
   * Mixin steps for terminal {@link Where} interfaces.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Mixin extends After, Sort, Limit {

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

    private final String symbol;

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
