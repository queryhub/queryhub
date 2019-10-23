package org.queryhub.steps;

/**
 * Represents the SQL building steps which end the statement building.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Terminal {

  /**
   * Represents {@code SELECT} statements for {@link Where} implementations.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Select extends Where, Sort, Limit {

  }

  /**
   * Finishes the SQL statement building operation. Implicitly appends a semicolon to the
   * statement's end.
   *
   * @return Complete and finished SQL string representation so far, with an appended semicolon.
   * @since 0.1.0
   */
  String build();

  /**
   * Finishes the SQL statement building operation.
   *
   * @param withSemiColon Optionally appends trailing semicolon to statement under construction.
   * @return Complete and finished SQL string representation so far.
   * @since 0.1.0
   */
  String build(final boolean withSemiColon);
}
