package org.queryhub.steps;

import org.queryhub.field.Field;
import org.queryhub.steps.Terminal.Select;

/**
 * Represents the SQL building steps for a {@code VALUES} operation.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Insert {

  /**
   * Appends the string segment to the {@code VALUES} operation to the statement building.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .values(Field.of("value_1"), Field.of("value_2"), Field.of("value_3"))
   *   .build();
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   VALUES 'value_1', 'value_2', 'value_3';
   *
   * }</pre>
   *
   * @param fields The fields to be inserted into the table.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @since 0.1.0
   */
  Terminal values(final Field fields);

  /**
   * Appends a {@code VALUES} operation's string segment to the statement building.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .values(Query.select(Field.of("table_1"), Field.of("field_1"), Field.of("field_2")))
   *   .build();
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   VALUES (SELECT 'field_1', 'field_2' FROM 'table_1');
   *
   * }</pre>
   *
   * @param clause {@code SELECT} statement which the returned columns will be inserted into the
   *               table's main query.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @since 0.1.0
   */
  Terminal values(final Select clause);
}
