package org.queryhub;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.queryhub.field.Field;
import org.queryhub.steps.Terminal;

/**
 * General abstraction for {@link Query}'s implementations. Provides resources for implementations
 * to manipulate the ongoing statement build though its methods.
 *
 * @param <B> Implementation type. Must extend this class.
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
abstract class Base<B extends Base<B>> implements Query, Terminal {

  private static final String SPACE = " ";
  private static final String SPACED_COMMA = ", ";

  private static final String END = ";";
  private static final String OPEN = "(";
  private static final String CLOSE = ")";

  private final StringJoiner joiner = new StringJoiner(SPACE);

  private boolean isClosed = Boolean.FALSE;

  /**
   * The first string to be set into the statement builder should always be a {@link KeyWord}.
   *
   * @param keyword A keyword.
   * @since 0.1.0
   */
  Base(final Keys keyword) {
    this.add(keyword);
  }

  /**
   * Utility method to provide own instance. Useful for using in combination with this class'
   * another utility methods.
   *
   * @return Own instance. Intended to return {@code this} in the implemented methods.
   * @since 0.1.0
   */
  abstract B self();

  /**
   * @since 0.1.0
   */
  @Override
  public final boolean equals(final Object o) {
    if (o == this) {
      return Boolean.TRUE;
    } else if (!(o instanceof Base)) {
      return Boolean.FALSE;
    }
    return this.joiner.equals(((Base) o).joiner);
  }

  /**
   * @since 0.1.0
   */
  @Override
  public final int hashCode() {
    return Objects.hashCode(joiner);
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
    throwIf(IllegalStateException::new, isClosed);
    this.isClosed = Boolean.TRUE;
    return withSemiColon ? this.joiner.toString() + END : this.joiner.toString();
  }

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

  // Package-private

  /**
   * Adds the given {@code Field}'s string representation into the {@code Stream.Builder}.
   *
   * @param field A field to be set.
   * @return Current statement building instance.
   * @see #add(Field, boolean)
   * @since 0.1.0
   */
  final B add(final Field field) {
    return this.add(field, Boolean.FALSE);
  }

  /**
   * Adds the given {@code Field}'s string representation into the {@code Stream.Builder}.
   *
   * @param field      A field to be set.
   * @param isEnclosed Indicates if the {@link Field}'s value is going to be enclosed by
   *                   parenthesis.
   * @return Current statement building instance.
   * @see #add(String, boolean)
   * @since 0.1.0
   */
  final B add(final Field field, final boolean isEnclosed) {
    return this.add(field.get(), isEnclosed);
  }

  /**
   * Adds a {@code SELECT} clause into the statement building. The statement is going to be
   * implicitly enclosed by parenthesis.
   *
   * @param clause A {@code SELECT} clause
   * @return Current statement building instance.
   * @see #add(String, boolean)
   * @since 0.1.0
   */
  final B add(final Select clause) {
    return this.add(clause.build(Boolean.FALSE), Boolean.TRUE);
  }

  /**
   * Adds the given {@link KeyWord}'s string representation into the {@link #joiner statement
   * builder}.
   *
   * @param keyWord A keyword to be set.
   * @return Current statement building instance.
   * @see #add(String, boolean)
   * @since 0.1.0
   */
  final <K extends Enum & KeyWord> B add(final K keyWord) {
    return this.add(keyWord.keyWord(), Boolean.FALSE);
  }

  /**
   * Adds the given value into the {@code Stream.Builder}.
   *
   * @param value The value to be added.
   * @return Current statement building instance.
   * @see #self()
   * @since 0.1.0
   */
  private B add(final String value, final boolean isEnclosed) {
    this.joiner.add(isEnclosed ? OPEN + requireNonNull(value) + CLOSE : requireNonNull(value));
    return self();
  }

  /**
   * Adds {@link Stream}'s elements joined with spaced commas.
   *
   * @param stream Ongoing stream.
   * @param mapper A mapper function (functor) to string.
   * @return Current statement building instance.
   * @see #add(String, boolean)
   * @since 0.1.0
   */
  final <T> B withComma(final Stream<T> stream, final Function<T, String> mapper) {
    return add(stream.map(mapper).collect(Collectors.joining(SPACED_COMMA)), Boolean.FALSE);
  }

  /**
   * Throws an exception conditionally to a given condition.
   *
   * @param exception A supplied exception.
   * @param condition The logical condition which may trigger the supplied exception.
   * @throws RuntimeException if the given condition is not satisfied.
   * @since 0.1.0
   */
  static void throwIf(final Supplier<RuntimeException> exception, final boolean condition) {
    if (condition) {
      throw exception.get();
    }
  }

  // Utilities

  /**
   * Utility method to handle varargs arguments.
   *
   * @param first The first parameter.
   * @param next  The next parameters.
   * @param <T>   Inferred type shared between parameters.
   * @return A ongoing stream of the given parameters' type.
   * @since 0.1.0
   */
  @SafeVarargs
  static <T> Stream<T> combine(final T first, final T... next) {
    return Stream.concat(Stream.of(first), Stream.of(next));
  }
}
