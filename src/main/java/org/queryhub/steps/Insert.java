package org.queryhub.steps;

import org.queryhub.field.Field;
import org.queryhub.steps.Terminal.Select;

/**
 * Represents {@code INSERT}-related building steps.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Insert {

  /**
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  Terminal values(final Field fields);

  /**
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  Terminal values(final Select clause);
}
