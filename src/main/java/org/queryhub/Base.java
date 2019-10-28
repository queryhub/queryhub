package org.queryhub;

import static java.util.Objects.requireNonNull;

import java.util.Objects;
import java.util.StringJoiner;
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
   * @see #add(boolean, Field, Field...)
   * @since 0.1.0
   */
  final B add(final Field field, final Field... fields) {
    return this.add(Boolean.FALSE, field, fields);
  }

  /**
   * Adds the given {@link Field}'s string representation into the {@link #joiner statement
   * builder}.
   *
   * @param field      A field to be set.
   * @param isEnclosed Indicates if the {@link Field}'s value is going to be enclosed by
   *                   parenthesis.
   * @return Current statement building instance.
   * @see #add(String, boolean)
   * @since 0.1.0
   */
  final B add(final boolean isEnclosed, final Field field, final Field... fields) {
    return this.add(Helper.combine(field, fields, Field::get), isEnclosed);
  }

  /**
   * Adds a {@code SELECT} clause into the statement building. The statement is going to be
   * implicitly enclosed by parenthesis.
   *
   * @param clause A {@code SELECT} clause.
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

  // Privates

  /**
   * Adds the given value into the {@link #joiner statement builder}.
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
}
