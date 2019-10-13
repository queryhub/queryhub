package org.queryhub;

import static java.util.Objects.requireNonNull;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.queryhub.field.Field;
import org.queryhub.field.Field.Single;
import org.queryhub.steps.Terminal;
import org.queryhub.steps.Where;

/**
 * Library's entry point.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @see <a href="https://martinfowler.com/dslCatalog/expressionBuilder.html">Expression Builder</a>
 */
public final class Query implements  Where, Where.Select, Where.Mixin, Terminal {

  private enum Keys implements KeyWord {
    SELECT, FROM, WHERE,
    ;

    @Override
    public final String keyWord() {
      return name();
    }
  }

  private static final String SPACE = " ";

  private static final char END = ';';

  private final Stream.Builder<String> builder = Stream.builder();

  private Query() {
  }

  // Factories

  public static Where.Select select(final Single from, final Field fields) {
    return new Query().add(Keys.SELECT).add(fields).add(Keys.FROM).add(from);
  }

  // TODO: Composite SELECT query

  // Implementations

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
}
