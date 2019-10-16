package org.queryhub;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.queryhub.field.Field;
import org.queryhub.field.Field.Single;
import org.queryhub.steps.Insert;
import org.queryhub.steps.Limit;
import org.queryhub.steps.Sort;
import org.queryhub.steps.Terminal;
import org.queryhub.steps.Terminal.Select;
import org.queryhub.steps.Update;
import org.queryhub.steps.Where;

/**
 * Library's entry point. Every building step contract should be implemented here and, preferably,
 * the same instance should conduct the statement's string building end-to-end.
 *
 * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
 * @see <a href="https://martinfowler.com/dslCatalog/expressionBuilder.html">Expression Builder</a>
 * @since 0.1.0
 */
public final class Query implements Insert, Update, Update.Mixin,
    Where, Where.Mixin, Sort, Limit, Terminal, Select {

  /**
   * SQL syntax keywords. Should be used privately.
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  private enum Keys implements KeyWord {
    INSERT, INTO, VALUES, SELECT, DELETE, FROM, UPDATE, SET, WHERE, LIMIT;

    @Override
    public final String keyWord() {
      return name();
    }
  }

  private static final String COMMA = ",";
  private static final String SPACE = " ";
  private static final String SPACED_COMMA = ", ";

  private static final char END = ';';
  private static final char OPEN = '(';
  private static final char CLOSE = ')';

  private final Stream.Builder<String> builder = Stream.builder();

  private Query() {
  }

  // Factories

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
   *     VALUES (SELECT 'value_1','value_2','value_3'
   *               FROM 'table_1'
   *               WHERE 'field_1' <= 'value_1');
   *
   * }</pre>
   *
   * @param table Table's name which values are going to be inserted.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  public static Insert insert(final Single table) {
    return new Query().add(Keys.INSERT).add(Keys.INTO).add(table).add(Keys.VALUES);
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
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  public static Select select(final Single from, final Field fields) {
    return new Query().add(Keys.SELECT).add(fields).add(Keys.FROM).add(from);
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
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  public static Update update(final Field.Single table) {
    return new Query().add(Keys.UPDATE).add(table);
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
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  public static Where delete(final Field.Single table) {
    return new Query().add(Keys.DELETE).add(Keys.FROM).add(table);
  }

  // Implementations

  // Values

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final Terminal values(final Field fields) {
    return this.enclosed(fields.get());
  }

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final Terminal values(final Select where) {
    return this.enclosed(where.build(Boolean.FALSE));
  }

  // Where

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Field.Single f1, final Relation rel, final Field.Single f2) {
    return this.add(Keys.WHERE).add(f1).add(rel).add(f2);
  }

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin
  where(final Condition cnd, final Field.Single f1, final Relation rel, final Field.Single f2) {
    return this.add(cnd).add(f1).add(rel).add(f2);
  }

  // Update

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final Update.Mixin set(final Field.Single field, final Field.Single value) {
    return this.add(Keys.SET).add(field).add(Relation.EQ).add(value);
  }

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final Update.Mixin and(final Field.Single field, final Field.Single value) {
    return this.add(COMMA).add(field).add(Relation.EQ).add(value);
  }

  // Sort

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final Sort sort(final Sort.Type type, final Aggregate one, final Aggregate... ones) {
    final var b = Stream.<Aggregate>builder().add(one);
    Stream.of(ones).forEach(b);
    return this.add(type).add(b.build().map(Supplier::get)
        .collect(Collectors.joining(SPACED_COMMA)));
  }

  // Limit

  @Override
  public final Terminal limit(final long skip, long offset) {
    throwIf(IllegalArgumentException::new, skip < 0 || skip > offset);
    return this.add(Keys.LIMIT).add(skip + SPACED_COMMA + offset);
  }

  // Terminal

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final String build() {
    return this.build(Boolean.TRUE);
  }

  /**
   * {@inheritDoc}
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Override
  public final String build(final boolean withSemiColon) {
    final var s = this.builder.build().collect(Collectors.joining(SPACE));
    return withSemiColon ? s.concat(String.valueOf(END)) : s;
  }

  // Privates

  /**
   * Adds the given {@code Field}'s string representation into the {@code Stream.Builder}.
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @see Query#add(String)
   * @since 0.1.0
   */
  private Query add(final Field value) {
    return add(value.get());
  }

  /**
   * Adds the given {@code Keyword}'s string representation into the {@code Stream.Builder}.
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @see Query#add(String)
   * @since 0.1.0
   */
  private Query add(final KeyWord keyWord) {
    return add(keyWord.keyWord());
  }

  /**
   * Adds the given value into the {@code Stream.Builder}.
   *
   * @param value The value to be added.
   * @return Own query's instance.
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  private Query add(final String value) {
    this.builder.add(requireNonNull(value));
    return this;
  }

  /**
   * Encloses the given parameters with an enclosing parenthesis.
   *
   * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
   * @see Query#add(String)
   * @since 0.1.0
   */
  private Query enclosed(final String value) {
    return add(OPEN + value + CLOSE);
  }

  private static void throwIf(final Supplier<RuntimeException> e, final boolean predicate) {
    if (predicate) {
      throw e.get();
    }
  }

  // Object

  @Override
  public final String toString() {
    return build();
  }
}
