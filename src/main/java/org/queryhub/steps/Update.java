package org.queryhub.steps;

import org.queryhub.field.Field.Single;

/**
 * Represents {@code UPDATE}-related building steps.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Update {

  /**
   * The building step to append a {@code SET} segment {@code UPDATE} statement building.
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
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  Update.Mixin set(final Single field, final Single value);

  /**
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  interface Mixin extends Where, Terminal {

    /**
     * A {@code SET} step that can be used after a first clause composition.
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
     * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
     * @since 0.1.0
     */
    Update.Mixin and(final Single field, final Single value);
  }
}
