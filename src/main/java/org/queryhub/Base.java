package org.queryhub;

import static java.util.Objects.requireNonNull;

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

  private static final char END = ';';
  private static final char OPEN = '(';
  private static final char CLOSE = ')';

  private final Stream.Builder<String> builder = Stream.builder();

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
  final B add(final Field value) {
    return this.add(value.get());
  }

  /**
   * Adds the given {@code Keyword}'s string representation into the {@code Stream.Builder}.
   *
   * @see Impl#add(String)
   * @since 0.1.0
   */
  final B add(final KeyWord keyWord) {
    return this.add(keyWord.keyWord());
  }

  /**
   * Adds the given value into the {@code Stream.Builder}.
   *
   * @param value The value to be added.
   * @return Own Impl's instance.
   * @since 0.1.0
   */
  final B add(final String value) {
    this.builder.add(requireNonNull(value));
    return self();
  }

  /**
   * Encloses the given parameters with an enclosing parenthesis.
   *
   * @see Impl#add(String)
   * @since 0.1.0
   */
  final B enclosed(final String value) {
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
  static void throwIf(final Supplier<RuntimeException> exception, final boolean condition) {
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
