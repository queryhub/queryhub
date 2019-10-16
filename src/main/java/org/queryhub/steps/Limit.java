package org.queryhub.steps;

/**
 * Represents a SQL {@code LIMIT} operation.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Limit {

  /**
   * Appends a {@code LIMIT} operation.
   *
   * @param offset Number of record rows to be limited to.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @see #limit(long, long)
   * @since 0.1.0
   */
  default Terminal limit(final long offset) {
    return limit(0L, offset);
  }

  /**
   * Appends a {@code LIMIT} operation.
   *
   * @param skip   Number to record rows to be skipped from the beginning.
   * @param offset Number of record rows to be limited to.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  Terminal limit(final long skip, final long offset);
}
