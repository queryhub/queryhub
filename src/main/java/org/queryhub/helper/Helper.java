package org.queryhub;

import java.util.Objects;
import java.util.StringJoiner;
import java.util.function.Function;
import java.util.function.Supplier;
import org.queryhub.field.Field;

/**
 * Stateless class for grouping the package's utility resources.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
final class Helper {

  private static final String SPACED_COMMA = ", ";

  /**
   * Non-visible constructor.
   *
   * @since 0.1.0
   */
  private Helper() {
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

  /**
   * Combines variadic arguments' string forms.
   *
   * @param ts  Parameters to be joined.
   * @param <T> Inferred type shared between parameters.
   * @return A ongoing stream of the given parameters' type.
   * @since 0.1.0
   */
  static <T> String combine(final T t, final T[] ts, final Function<T, String> mapper) {
    final var joiner = new StringJoiner(SPACED_COMMA).add(mapper.apply(t));
    for (final T o : ts) {
      joiner.add(mapper.apply(o));
    }
    return joiner.toString();
  }

  /**
   * Converts into a field, with no special modification.
   *
   * @param o A object.
   * @return A field containing the given object in the string value.
   * @since 0.1.0
   */
  static Field asField(final Object o) {
    return () -> Objects.toString(o);
  }
}
