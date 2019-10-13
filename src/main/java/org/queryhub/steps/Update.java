package org.queryhub.steps;

import org.queryhub.field.Field.Single;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
public interface Update {

  Update.Mixin set(final Single field, final Single value);

  interface Mixin extends Where, Terminal {

    Update.Mixin and(final Single field, final Single value);
  }
}
