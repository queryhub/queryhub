package org.queryhub.field;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
public interface Field extends Supplier<String> {

  interface Single extends Field {

  }

  interface Multiple extends Field {

  }

  Multiple ALL = () -> "'*'";
  Single VARIABLE = () -> "'?'";

  // Overloaded Singles

  static Single of(final Integer value) {
    return of(Boolean.FALSE, value);
  }

  static Single of(final Supplier<Boolean> value) {
    return of(Boolean.FALSE, value);
  }

  static Single of(final ChronoLocalDate value) {
    return of(Boolean.FALSE, value);
  }

  static Single of(final ChronoLocalDateTime value) {
    return of(Boolean.FALSE, value);
  }

  static Single of(final String value) {
    return of(Boolean.FALSE, value);
  }

  // Singles

  static Single of(final boolean isDistinct, final Integer value) {
    return of(isDistinct, Integer.toString(value));
  }

  static Single of(final boolean isDistinct, final Supplier<Boolean> value) {
    return of(isDistinct, Boolean.toString(value.get()));
  }

  static Single of(final boolean isDistinct, final ChronoLocalDate value) {
    return of(isDistinct, Compiled.LOCAL_DATE.format(value));
  }

  static Single of(final boolean isDistinct, final ChronoLocalDateTime value) {
    return of(isDistinct, Compiled.LOCAL_DATE_TIME.format(value));
  }

  static Single of(final boolean isDistinct, final String value) {
    return () -> Optional.of(value)
        .map(String::valueOf).map(Compiled.QUOTED).map(s -> Compiled.DISTINCT.apply(isDistinct, s))
        .map(Compiled.DOUBLE_QUOTE::matcher).map(m -> m.replaceAll(Compiled.EMPTY)).orElseThrow();
  }

  // Overloaded Multiples

  static Multiple of(final Integer value, final Integer... values) {
    return of(Boolean.FALSE, value, values);
  }

  @SafeVarargs
  static Multiple of(final Supplier<Boolean> value, final Supplier<Boolean>... values) {
    return of(Boolean.FALSE, value, values);
  }

  static Multiple of(final ChronoLocalDate value, final ChronoLocalDate... values) {
    return of(Boolean.FALSE, value, values);
  }

  static Multiple of(final ChronoLocalDateTime value, final ChronoLocalDateTime... values) {
    return of(Boolean.FALSE, value, values);
  }

  static Multiple of(final String value, final String... values) {
    return of(Boolean.FALSE, value, values);
  }

  // Multiples

  static Multiple of(final boolean isDistinct, final Integer value, final Integer... values) {
    return process(isDistinct, stream(value, values).map(String::valueOf));
  }

  @SafeVarargs
  static Multiple
  of(final boolean isDistinct, final Supplier<Boolean> value, final Supplier<Boolean>... values) {
    return process(isDistinct, stream(value, values).map(Supplier::get).map(String::valueOf));
  }

  @SafeVarargs
  static <C extends ChronoLocalDate> Multiple
  of(final boolean isDistinct, final C value, final C... values) {
    return process(isDistinct, stream(value, values)
        .map(Compiled.LOCAL_DATE::format).map(String::valueOf));
  }

  @SafeVarargs
  static <C extends ChronoLocalDateTime> Multiple
  of(final boolean isDistinct, final C value, final C... values) {
    return process(isDistinct, stream(value, values)
        .map(Compiled.LOCAL_DATE_TIME::format).map(String::valueOf));
  }

  static Multiple of(final boolean isDistinct, final String value, final String... values) {
    return process(isDistinct, stream(value, values));
  }

  // privates

  @SafeVarargs
  private static <T> Stream<T> stream(final T first, final T... following) {
    final var build = Stream.<T>builder().add(first);
    Stream.of(following).forEach(build);
    return build.build();
  }

  private static <T> Multiple process(final boolean isDistinct, final Stream<T> stream) {
    return () -> Compiled.DISTINCT.apply(isDistinct, stream
        .map(String::valueOf).map(Compiled.QUOTED)
        .collect(Collectors.joining(Compiled.COMMA)));
  }
}
