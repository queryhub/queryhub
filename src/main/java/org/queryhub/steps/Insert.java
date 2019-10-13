package org.queryhub.steps;

import org.queryhub.field.Field;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
public interface Insert {

  Terminal values(final Field fields);

  Terminal values(final Where.Select clause);
}
