package org.queryhub;

import org.queryhub.field.Field;
import org.queryhub.field.Single;
import org.queryhub.steps.Insert;
import org.queryhub.steps.Terminal.Select;
import org.queryhub.steps.Update;
import org.queryhub.steps.Where;

/**
 * Library's entry point. Every building step contract should be implemented here and, preferably,
 * the same instance should conduct the statement's string building end-to-end.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @see <a href="https://martinfowler.com/dslCatalog/expressionBuilder.html">Expression Builder</a>
 * @since 0.1.0
 */
public interface Query {

  /**
   * SQL syntax keywords. Should be used privately.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Keys implements KeyWord {
    INSERT, INTO, VALUES, SELECT, DELETE, FROM, UPDATE, SET, WHERE, IN, LIMIT;
  }

  /**
   * Produces an {@code INSERT} statement.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *  Query.insert(Field.of("table_1"))
   *    .values(Field.of("value_1", "value_2", "value_3"))
   *    .build();
   *
   *  Query.insert(Field.of("table_1"))
   *    .values(Query.select(Field.of("table_1"), Field.of("field_1", "field_2", "field_3"))
   *      .where(Field.of("field_1"), Relation.LTE, Field.of("value_1")))
   *    .build();
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   INSERT INTO 'table_1' VALUES ('value_1', 'value_2', 'value_3');
   *
   *   INSERT INTO 'table_1'
   *     VALUES (SELECT 'value_ 1','value_2','value_3'
   *               FROM 'table_1'
   *               WHERE 'field_1' <= 'value_1');
   *
   * }</pre>
   *
   * @param table Table's name which values are going to be inserted.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @since 0.1.0
   */
  static Insert insert(final Single table) {
    return new Impl<>(Keys.INSERT).add(Keys.INTO).add(table).add(Keys.VALUES);
  }

  /**
   * Produces an {@code SELECT} statement.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   Query.select(Field.of("table_1"), Field.of("field_1", "field_2", "field_3")
   *     .where(Field.of("filed_1"), Relation.EQ, Field.of("value_1"))
   *     .build();
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *  SELECT 'field_1', 'field_2', 'field_3' FROM 'table_1' WHERE 'field_1' = 'value_1';
   *
   * }</pre>
   *
   * @param from   Table's name which the selection is retrieved from.
   * @param fields The selection's columns.
   * @return Current statement building instance, intended to be chained to next building calls.
   * @since 0.1.0
   */
  static Select select(final Single from, final Field fields) {
    return new Impl<>(Keys.SELECT).add(fields).add(Keys.FROM).add(from);
  }

  // TODO: Composite SELECT query

  /**
   * Produces an {@code UPDATE} statement.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *  Query.update(Field.of("table_1"))
   *    .set(Field.of("field_1"), Field.of("value_1"))
   *    .and(Field.of("field_2"), Field.of("value_2"))
   *    .build();
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   UPDATE 'table_1' SET 'field_1' = 'value_1', 'field_2' = 'value_2';
   *
   * }</pre>
   *
   * @param table Table's name which the update is going to be set.
   * @return Current statement building instance, intended to be chained to next building calls.
   * @since 0.1.0
   */
  static Update update(final Single table) {
    return new Impl<>(Keys.UPDATE).add(table);
  }

  // TODO: Upsert

  /**
   * Produces an {@code DELETE} statement.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   Query.delete(Field.of("table_1"))
   *     .where(Field.of("field_1"), Relation.EQ, Field.of("value_1"))
   *     .build();
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *  DELETE FROM 'table_1' WHERE 'field_1' = 'value_1';
   *
   * }</pre>
   *
   * @return Current statement building instance, intended to be chained to next building calls.
   * @since 0.1.0
   */
  static Where delete(final Single table) {
    return new Impl<>(Keys.DELETE).add(Keys.FROM).add(table);
  }
}
