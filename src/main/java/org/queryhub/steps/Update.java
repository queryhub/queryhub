package org.queryhub.steps;

import org.queryhub.field.Single;

/**
 * Represents the SQL building steps which occur in a {@code UPDATE} operation.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Update {

  /**
   * Appends a {@code SET} operation's the string segment in a {@code UPDATE} statement building.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .set(Field.of("field_1"), Field.of("field_2"))
   *   (...)
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   'field_1' = 'field_2'
   *   (...)
   *
   * }</pre>
   *
   * @param field The field to be update.
   * @param value A value to be set.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @since 0.1.0
   */
  Update.Mixin set(final Single field, final Single value);

  /**
   * Step that allows keep using a {@code WHERE} contract after the {@code SET} operation or finish
   * the statement building with a {@link Terminal} method call.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Mixin extends Where, Terminal {

    /**
     * Concatenates another string segment to {@code SET} operation in a {@code UPDATE} statement
     * building.
     * <p>
     * The following implementation example:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   .and(Field.of("field_1"), Field.of("field_2"))
     *   (...)
     *
     * }</pre>
     * <p>
     * should produce the output:
     * <p>
     * <pre>{@code
     *
     *   (...)
     *   , 'field_1' = 'field_2'
     *   (...)
     *
     * }</pre>
     *
     * @param field The field to be update.
     * @param value A value to be set.
     * @return Current statement building instance, intended to be chained to the next building
     * calls.
     * @since 0.1.0
     */
    Update.Mixin and(final Single field, final Single value);
  }
}
