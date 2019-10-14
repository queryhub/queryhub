package org.queryhub.field;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.format.DateTimeFormatter;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
final class Compiled {

  private Compiled() {
  }

  static final String COMMA = ", ";
  static final String EMPTY = "";

  static final DateTimeFormatter LOCAL_DATE = ofPattern("YYYY-MM-dd");
  static final DateTimeFormatter LOCAL_DATE_TIME = ofPattern("YYYY-MM-dd hh:mm:ss");
  static final Pattern DOUBLE_QUOTE = Pattern.compile("\"");

  static final BiFunction<Boolean, String, String> DISTINCT =
      (b, s) -> Boolean.TRUE.equals(b) ? "DISTINCT " + s : s;
  /* https://www.iso.org/obp/ui/#iso:std:iso-iec:9075:-11:ed-4:v1:en */
  static final UnaryOperator<String> QUOTED = s -> "'" + s + "'";
}
