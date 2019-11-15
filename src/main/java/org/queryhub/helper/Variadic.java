package org.queryhub.helper;

import java.util.StringJoiner;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Groups static methods which their return cna be used as functors.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public final class Variadic {

  private static final String SPACED_COMMA = ", ";

  private Variadic() {
  }

  /**
   * Combines variadic arguments into a string representation.
   *
   * @param <T>    Inferred type for the variadic parameters.
   * @param mapper A mapping function which converts one type to another.
   * @return A bi-function to apply on variadic parameters which are eventually going to be handled
   * by some other method. This intends to widen function composition.
   * @since 0.1.0
   */
  public static <T> BiFunction<T, T[], String> asString(final Function<T, String> mapper) {
    return (t, tt) -> {
      final var joiner = new StringJoiner(SPACED_COMMA);
      for (var i = 0; i < tt.length + 1; i++) {
        mapper.andThen(joiner::add).apply(i == 0 ? t : tt[i - 1]);
      }
      return joiner.toString();
    };
  }

  //  private static <T, U> BiFunction<T, T[], U[]>
  //  asArray(final Function<T, U> mapper, final IntFunction<U[]> generator) {
  //    return (t, ts) -> {
  //      final var arr = generator.apply(ts.length + 1);
  //      for (var i = 0; i < ts.length + 1; i++) {
  //        arr[i] = mapper.apply(i == 0 ? t : ts[i - 1]);
  //      }
  //      return arr;
  //    };
  //  }
}
