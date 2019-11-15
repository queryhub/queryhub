package org.queryhub.helper;

import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * Groups static methods which their return cna be used as functors.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public final class Functor {

  private static final String SPACED_COMMA = ", ";

  private Functor() {
  }

  /**
   * Combines variadic arguments to the string form of theirs.
   *
   * @param mapper A mapping function which converts one type to a string representation.
   * @param <T>    Inferred type shared between parameters.
   * @return A string function which accepts an array.
   * @since 0.1.0
   */
  public static <T> Function<T[], String> mapToString(final Function<T, String> mapper) {
    return arr -> {
      final var joiner = new StringJoiner(SPACED_COMMA);
      for (final var o : arr) {
        mapper.andThen(joiner::add).apply(o);
      }
      return joiner.toString();
    };
  }

  /**
   * Combines variadic arguments into one array wrapper.
   *
   * @param <T>       Inferred type for the variadic parameters.
   * @param <U>       Inferred type for the returning array.
   * @param mapper    A mapping function which converts one type to another.
   * @param generator A generator function to create a wrapping array for the handled items.
   * @return A bi-function to apply on variadic parameters which are eventually going to be handled
   * by some other method. This intends to widen function composition.
   * @since 0.1.0
   */
  public static <T, U> BiFunction<T, T[], U[]>
  variadicOf(final Function<T, U> mapper, final IntFunction<U[]> generator) {
    return (t, ts) -> {
      final var arr = generator.apply(ts.length + 1);
      for (var i = 0; i < ts.length + 1; i++) {
        arr[i] = mapper.apply(i == 0 ? t : ts[i - 1]);
      }
      return arr;
    };
  }

  /**
   * Combines variadic arguments into a string representation.
   *
   * @param <T>    Inferred type for the variadic parameters.
   * @param mapper A mapping function which converts one type to another.
   * @return A bi-function to apply on variadic parameters which are eventually going to be handled
   * by some other method. This intends to widen function composition.
   * @see #variadicOf(Function, IntFunction)
   * @since 0.1.0
   */
  public static <T> BiFunction<T, T[], String> variadicOf(final Function<T, String> mapper) {
    return Functor.variadicOf(mapper, String[]::new).andThen(mapToString(Function.identity()));
  }
}
