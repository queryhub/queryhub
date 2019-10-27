package org.queryhub;

/**
 * Represents SQL built-in keywords. Intended to stand a common contract among {@link Enum
 * enumerations} which items represent the various SQL's keywords.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface KeyWord {

  /**
   * Gets the keyword's string representation
   *
   * @return The string representation.
   * @since 0.1.0
   */
  default String keyWord() {
    return name();
  }

  /**
   * Convenience contract to prevent {@link #keyWord()} rewriting over implementing enums when
   * applicable.
   *
   * @return Enum's name.
   * @since 0.1.0
   */
  String name();
}
