package org.queryhub.steps;

/**
 * Represents a SQL {@code LIMIT} operation.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Limit {

  /**
   * @param offset
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @see #limit(long, long)
   * @since 0.1.0
   */
  default Terminal limit(final long offset) {
    return limit(0L, offset);
  }

  /**
   * @param skip
   * @param upTo
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  Terminal limit(final long skip, final long upTo);
}
