package org.queryhub;

import static java.util.Objects.requireNonNull;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.queryhub.field.Field;
import org.queryhub.field.Field.Single;
import org.queryhub.steps.Sort;
import org.queryhub.steps.Terminal;
import org.queryhub.steps.Update;
import org.queryhub.steps.Where;

/**
 * Method implementations for the building steps.
 *
 * @author <a href="queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
final class Impl implements Query {

  private static final String COMMA = ",";
  private static final String SPACE = " ";
  private static final String SPACED_COMMA = ", ";

  private static final char EQUAL = '=';
  private static final char END = ';';
  private static final char OPEN = '(';
  private static final char CLOSE = ')';

  private final Stream.Builder<String> builder = Stream.builder();

  // Values

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Terminal values(final Field fields) {
    return this.enclosed(fields.get());
  }

  /**
   * {@inheritDoc}
   *
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
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Single field, final Field fields) {
    return this.add(Keys.WHERE).add(field).add(Keys.IN).enclosed(fields.get());
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Field.Single f1, final Relation rel, final Field.Single f2) {
    return this.add(Keys.WHERE).add(f1).add(rel).add(f2);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Single reference, final Select clause) {
    return this.add(Keys.WHERE).add(reference).add(Keys.IN).enclosed(clause.build(Boolean.FALSE));
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Condition cond, final Single field, final Field fields) {
    return this.add(cond).add(field).add(Keys.IN).enclosed(fields.get());
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin
  where(final Condition cnd, final Field.Single f1, final Relation rel, final Field.Single f2) {
    return this.add(cnd).add(f1).add(rel).add(f2);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Condition cond, final Single reference,
      final Select clause) {
    return this.add(cond).add(reference).add(Keys.IN).enclosed(clause.build(Boolean.FALSE));
  }

  // Update

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Update.Mixin set(final Field.Single field, final Field.Single value) {
    return this.add(Keys.SET).add(field).add(String.valueOf(EQUAL)).add(value);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Update.Mixin and(final Field.Single field, final Field.Single value) {
    return this.add(COMMA).add(field).add(String.valueOf(EQUAL)).add(value);
  }

  // Sort

  /**
   * {@inheritDoc}
   *
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

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Terminal limit(final long skip, final long offset) {
    throwIf(IllegalArgumentException::new, skip < 0 || skip > offset);
    return this.add(Keys.LIMIT).add(skip + SPACED_COMMA + offset);
  }

  // Terminal

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final String build() {
    return this.build(Boolean.TRUE);
  }

  /**
   * {@inheritDoc}
   *
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
   * @see Impl#add(String)
   * @since 0.1.0
   */
  Impl add(final Field value) {
    return add(value.get());
  }

  /**
   * Adds the given {@code Keyword}'s string representation into the {@code Stream.Builder}.
   *
   * @see Impl#add(String)
   * @since 0.1.0
   */
  Impl add(final KeyWord keyWord) {
    return add(keyWord.keyWord());
  }

  /**
   * Adds the given value into the {@code Stream.Builder}.
   *
   * @param value The value to be added.
   * @return Own Impl's instance.
   * @since 0.1.0
   */
  private Impl add(final String value) {
    this.builder.add(requireNonNull(value));
    return this;
  }

  /**
   * Encloses the given parameters with an enclosing parenthesis.
   *
   * @see Impl#add(String)
   * @since 0.1.0
   */
  private Impl enclosed(final String value) {
    return add(OPEN + value + CLOSE);
  }

  /**
   * Throws an exception conditionally to a given condition.
   *
   * @param exception A supplied exception.
   * @param condition The logical condition which may trigger the supplied exception.
   * @throws RuntimeException if the given condition is not satisfied.
   * @since 0.1.0
   */
  private static void throwIf(final Supplier<RuntimeException> exception, final boolean condition) {
    if (condition) {
      throw exception.get();
    }
  }

  // Object

  /**
   * Prints out the statement's state under the current state.
   *
   * @return SQL statement's current state. Then, ends the statement.
   * @throws IllegalStateException if called a second time from the same instance.
   * @see #build()
   * @since 0.1.0
   */
  @Override
  public final String toString() {
    return build();
  }
}
