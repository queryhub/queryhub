package org.queryhub.helper;

import java.util.Objects;
import java.util.function.UnaryOperator;
import org.queryhub.helper.Helper;

/**
 * Applies a string transformation logic.
 * <p>
 * Items' names pattern indicates what the operation does.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public enum Mutator implements UnaryOperator<String> {
  ADD_SIMPLE_QUOTE(s -> "\'" + s + "\'"), // TODO: back-ticks
  ADD_PARENTHESIS(s -> "(" + s + ")"),
  REMOVE_REDUNDANT_DOUBLE_QUOTES(s -> Helper.DOUBLE_QUOTE.matcher(s).replaceAll(Helper.EMPTY)),
  ;
  private final UnaryOperator<String> fun;

  /**
   * {@inheritDoc}
   *
   * @param fun A transformation logic.
   * @since 0.1.0
   */
  Mutator(final UnaryOperator<String> fun) {
    this.fun = fun;
  }

  /**
   * Mutates a string value. Requited.
   *
   * @param value String representation of a value.
   * @return The mutates value according to the operation.
   * @since 0.1.0
   */
  @Override
  public final String apply(final String value) {
    return fun.andThen(Objects::requireNonNull).apply(value);
  }
}
