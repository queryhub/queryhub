package org.queryhub.field;

import java.util.function.UnaryOperator;
import org.queryhub.helper.Helper;

/**
 * Assembles String mutator logic.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
enum Mutator implements UnaryOperator<String> {
  PUT_DOUBLE_QUOTE(s -> "\'" + s + "\'"), // TODO: back-ticks
  REMOVE_REDUNDANT_DOUBLE_QUOTES(s -> Helper.DOUBLE_QUOTE.matcher(s).replaceAll(Helper.EMPTY)),
  ;
  private final UnaryOperator<String> fun;

  Mutator(final UnaryOperator<String> fun) {
    this.fun = fun;
  }

  @Override
  public final String apply(final String s) {
    return fun.apply(s);
  }
}
