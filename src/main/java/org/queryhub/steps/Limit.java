package org.queryhub.steps;

/**
 * Represents the SQL building steps for a {@code LIMIT} operation.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Limit {

  /**
   * Appends a {@code LIMIT} operation's string segment to the statement building. The starting
   * point is implicitly set.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .limit(2, 5)
   *   .build()
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   LIMIT 0, 5;
   *
   * }</pre>
   *
   * @param offset The number of rows after the starting row to finish the limiting.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @see #limit(long, long)
   * @since 0.1.0
   */
  default Terminal limit(final long offset) {
    return limit(0L, offset);
  }

  /**
   * Appends a {@code LIMIT} operation's string segment to the statement building.
   * <p>
   * The following implementation example:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   .limit(2, 5)
   *   .build()
   *
   * }</pre>
   * <p>
   * should produce the output:
   * <p>
   * <pre>{@code
   *
   *   (...)
   *   LIMIT 2, 5;
   *
   * }</pre>
   *
   * @param skip   The number of rows after the first to start the limiting
   * @param offset The number of rows after the set by {@code skip} parameter to finish the
   *               limiting.
   * @return Current statement building instance, intended to be chained to the next building calls.
   * @throws IllegalArgumentException if the {@code skip} is greater than {@code offset};
   * @since 0.1.0
   */
  Terminal limit(final long skip, final long offset);
}
