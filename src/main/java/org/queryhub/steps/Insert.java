package org.queryhub.steps;

import org.queryhub.field.Field;
import org.queryhub.steps.Terminal.Select;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
public interface Insert {

  Terminal values(final Field fields);

  Terminal values(final Select clause);
}
