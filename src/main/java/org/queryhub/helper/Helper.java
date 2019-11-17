package org.queryhub.helper;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.format.DateTimeFormatter;
import java.util.function.Supplier;
import java.util.regex.Pattern;

/**
 * Stateless class for grouping the package's utility resources.
 * <p>
 * Preferably, for design purposes, only {@code static} members should be written here.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public final class Helper {

  static final String EMPTY = "";
  public static final String SPACE = " ";

  public static final DateTimeFormatter LOCAL_DATE_TIME = ofPattern("YYYY-MM-dd hh:mm:ss");
  static final Pattern DOUBLE_QUOTE = Pattern.compile("\"");

  /**
   * Non-visible constructor.
   *
   * @since 0.1.0
   */
  private Helper() {
  }

  /**
   * Throws an exception conditionally to a given condition.
   *
   * @param exception A supplied exception.
   * @param condition The logical condition which may trigger the supplied exception.
   * @throws RuntimeException if the given condition is not satisfied.
   * @since 0.1.0
   */
  public static void throwIf(final Supplier<RuntimeException> exception, final boolean condition) {
    if (condition) {
      throw exception.get();
    }
  }
}
