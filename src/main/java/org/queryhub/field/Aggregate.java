package org.queryhub.field;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
public interface Aggregate extends Field.Single {

  // Composite

  static Aggregate of(final Type type, final String value) {
    return of(type, Boolean.FALSE, value);
  }

  static Aggregate of(final Type type, final boolean isDistinct, final String value) {
    return () -> type.fun.apply(Compiled.DISTINCT.apply(isDistinct, Field.of(value).get()));
  }

  // Composition

  static Aggregate of(final Type type, final Aggregate aggregate) {
    return of(type, Boolean.FALSE, aggregate);
  }

  static Aggregate of(final Type type, final boolean isDistinct, final Aggregate aggregate) {
    return () -> type.fun.apply(Compiled.DISTINCT.apply(isDistinct, aggregate.get()));
  }

  static Multiple of(final Type type, final Aggregate aggregate, final Aggregate... aggregates) {
    return of(type, Boolean.FALSE, aggregate, aggregates);
  }

  static Multiple
  of(final Type type, final boolean isDistinct, final Aggregate one, final Aggregate... ones) {
    return () -> type.fun.apply(Compiled.DISTINCT.apply(isDistinct, Stream
        .concat(Stream.of(one), Stream.of(ones)).map(Supplier::get)
        .collect(Collectors.joining(Compiled.COMMA))));
  }

  enum Type {
    COUNT(s -> String.format("COUNT(%s)", s)),
    AVG(s -> String.format("AVG(%s)", s)),
    MIN(s -> String.format("MIN(%s)", s)),
    MAX(s -> String.format("MAX(%s)", s));
    private final Function<String, String> fun;

    Type(final Function<String, String> fun) {
      this.fun = fun;
    }
  }
}
