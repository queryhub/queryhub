package org.queryhub;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.queryhub.field.Field;
import org.queryhub.field.Field.Single;
import org.queryhub.steps.Insert;
import org.queryhub.steps.Sort;
import org.queryhub.steps.Terminal;
import org.queryhub.steps.Update;
import org.queryhub.steps.Where;

/**
 * Library's entry point.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @see <a href="https://martinfowler.com/dslCatalog/expressionBuilder.html">Expression Builder</a>
 */
public final class Query implements Insert, Update, Update.Mixin,
    Where, Where.Select, Where.Mixin, Sort, Terminal {

  private enum Keys implements KeyWord {
    INSERT, INTO, VALUES, SELECT, DELETE, FROM, UPDATE, SET, WHERE,
    ;

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

  public static Insert insert(final Single table) {
    return new Query().add(Keys.INSERT).add(Keys.INTO).add(table).add(Keys.VALUES);
  }

  public static Where.Select select(final Single from, final Field fields) {
    return new Query().add(Keys.SELECT).add(fields).add(Keys.FROM).add(from);
  }

  // TODO: Composite SELECT query

  public static Update update(final Field.Single table) {
    return new Query().add(Keys.UPDATE).add(table);
  }

  // TODO: Upsert

  public static Where delete(final Field.Single table) {
    return new Query().add(Keys.DELETE).add(Keys.FROM).add(table);
  }

  // Implementations

  // Values

  @Override
  public final Terminal values(final Field fields) {
    return this.enclosed(fields.get());
  }

  @Override
  public final Terminal values(final Where.Select where) {
    return this.enclosed(where.build(Boolean.FALSE));
  }

  // Where

  @Override
  public final Where.Mixin where(final Field.Single f1, final Relation rel, final Field.Single f2) {
    return this.add(Keys.WHERE).add(f1).add(rel).add(f2);
  }

  @Override
  public final Where.Mixin
  where(final Condition cnd, final Field.Single f1, final Relation rel, final Field.Single f2) {
    return this.add(cnd).add(f1).add(rel).add(f2);
  }

  // Update

  @Override
  public final Update.Mixin set(final Field.Single field, final Field.Single value) {
    return this.add(Keys.SET).add(field).add(Relation.EQ).add(value);
  }

  @Override
  public final Update.Mixin and(final Field.Single field, final Field.Single value) {
    return this.add(COMMA).add(field).add(Relation.EQ).add(value);
  }

  // Sort

  @Override
  public final Sort sort(final Sort.Type type, final Aggregate one, final Aggregate... ones) {
    final var b = Stream.<Aggregate>builder().add(one);
    Stream.of(ones).forEach(b);
    return this.add(type).add(b.build().map(Supplier::get)
        .collect(Collectors.joining(SPACED_COMMA)));
  }

  // Terminal

  @Override
  public final String build() {
    return this.build(Boolean.TRUE);
  }

  @Override
  public final String build(final boolean withSemiColon) {
    final var s = this.builder.build().collect(Collectors.joining(SPACE));
    return withSemiColon ? s.concat(String.valueOf(END)) : s;
  }

  // Privates

  private Query add(final Field value) {
    return add(value.get());
  }

  private Query add(final KeyWord keyWord) {
    return add(keyWord.keyWord());
  }

  private Query add(final String value) {
    this.builder.add(requireNonNull(value));
    return this;
  }

  private Query enclosed(final String value) {
    return add(OPEN + value + CLOSE);
  }
}
