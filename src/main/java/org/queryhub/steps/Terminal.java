package org.queryhub.steps;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
public interface Terminal {

  interface Select extends Where, Sort {

  }

  String build();

  String build(final boolean withSemiColon);
}
