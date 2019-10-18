package org.queryhub;

/**
 * Represents SQL built-in keywords. Intended to stand a common contract among {@link Enum
 * enumarations} which items represent the various SQL's keywords.
 *
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface KeyWord {

  /**
   * Gets the keyword's string representation
   *
   * @return The string representation.
   * @since 0.1.0
   */
  String keyWord();
}
