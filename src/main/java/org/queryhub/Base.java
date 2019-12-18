package org.queryhub;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import org.queryhub.field.Field;
import org.queryhub.helper.Helper;
import org.queryhub.helper.KeyWord;
import org.queryhub.helper.Mutator;
import org.queryhub.helper.Variadic;
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
  private static final String END = ";";

  private final StringJoiner joiner = new StringJoiner(SPACE);

  private boolean isClosed = Boolean.FALSE;
  private int hashCode;

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
  @SuppressWarnings("unchecked")
  public final boolean equals(final Object o) {
    if (o == null || getClass() != o.getClass()) {
      return Boolean.FALSE;
    }
    final var b = (Base<B>) o;
    return this.isClosed == b.isClosed && this.joiner.equals(b.joiner);
  }

  /**
   * @since 0.1.0
   */
  @Override
  public final int hashCode() {
    if (hashCode == 0) {
      hashCode = Objects.hash(joiner, isClosed);
    }
    return hashCode;
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
    Helper.throwIf(IllegalStateException::new, isClosed);
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
   * Adds the given {@link Field}'s string representation into the {@link #joiner statement
   * builder}.
   *
   * @param field A field to be set.
   * @return Current statement building instance.
   * @since 0.1.0
   */
  final B add(final Field field, final Field... fields) {
    Variadic.asString(Field::get).andThen(this.joiner::add).apply(field, fields);
    return self();
  }

  /**
   * Adds the given {@link KeyWord}'s string representation into the {@link #joiner statement
   * builder}.
   *
   * @param keyWord A keyword to be set.
   * @return Current statement building instance.
   * @since 0.1.0
   */
  final <K extends Enum<K> & KeyWord> B add(final K keyWord) {
    this.joiner.add(keyWord.keyWord());
    return self();
  }

  /**
   * Encloses the string representation from a value within parenthesis.
   *
   * @param val    A value to be set.
   * @param mapper A mapping function to supply a string representation.
   * @param <T>    A type to be shared between parameters.
   * @return Current statement building instance.
   * @since 0.1.0
   */
  final <T> B enclose(final T val, final Function<T, String> mapper) {
    Mutator.ADD_PARENTHESIS.compose(mapper).andThen(this.joiner::add).apply(val);
    return self();
  }
}
