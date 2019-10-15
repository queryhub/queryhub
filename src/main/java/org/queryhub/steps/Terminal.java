package org.queryhub.steps;

/**
 * Represents ending operations.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Terminal {

  /**
   * This abstraction is intended to represent {@code SELECT} statements for {@link Where}
   * implementations.
   *
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Select extends Where, Sort {

  }

  /**
   * Finishes the SQL statement building operation.
   *
   * @return Complete and finished SQL string representation so far, with an appended semicolon.
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  String build();

  /**
   * Finishes the SQL statement building operation.
   *
   * @param withSemiColon Optionally appends trailing semicolon to statement under construction.
   * @return Complete and finished SQL string representation so far.
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  String build(final boolean withSemiColon);
}
