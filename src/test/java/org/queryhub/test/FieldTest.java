package org.queryhub.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.field.Field;
import org.queryhub.field.Field.Constants;
import org.queryhub.field.Multiple;
import org.queryhub.field.Single;

/**
 * Defines {@link Field}'s test cases.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.FIELD_TAG)
@DisplayName("Field test cases.")
final class FieldTest extends BaseTest {

  /* Evaluate parameter nullity is not necessary. */
  /*@Test
  final void shouldNotReturn_null() {
    // Act / Assert
    Assertions.assertThrows(NullPointerException.class, () -> Field.of(null).get());
  }*/

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should expose all as asterisk.")
  final void shouldExpose_all_as_asterisk() {
    // Act / Assert
    Assertions.assertEquals("*", Constants.ALL.get());
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should expose variable as question mark.")
  final void shouldExpose_variable_as_questionMark() {
    // Act / Assert
    Assertions.assertEquals("?", Constants.VARIABLE.get());
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should ignore escaped double quotes.")
  final void shouldIgnore_escapedDoubleQuotes() {
    // Act / Assert
    Assertions.assertEquals("'1'", Single.of("\"1\"").get());

    Assertions.assertEquals("'1', '2'", Multiple.of("\"1\"", "\"2\"").get());
  }

  /**
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @SuppressWarnings("unused")
  static abstract class BaseFieldTest {

    final String SINGLE_DESCRIPTION = "Should accept only value.";

    abstract void shouldAccept_onlyOneValue();

    final String MULTIPLE_DESCRIPTION = "Should concatenate multiple parameters with comma.";

    abstract void shouldConcat_multipleParameters_with_comma();
  }

  /**
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Nested
  @DisplayName("SQL NUMBER-related field test cases.")
  final class IntegerField extends BaseFieldTest {

    /**
     * @since 0.1.0
     */
    @Test
    @Override
    @DisplayName(SINGLE_DESCRIPTION)
    final void shouldAccept_onlyOneValue() {
      // Act /  Assert
      Assertions.assertEquals("1", Single.of(1).get());
    }

    /**
     * @since 0.1.0
     */
    @Test
    @Override
    @DisplayName(MULTIPLE_DESCRIPTION)
    final void shouldConcat_multipleParameters_with_comma() {
      // Act
      final var SUBJECT = Multiple.of(1, 2, 3);
      // Assert
      Assertions.assertEquals("1, 2, 3", SUBJECT.get());
    }
  }

  /**
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Nested
  @DisplayName("SQL BOOLEAN-related field test cases.")
  final class BooleanField extends BaseFieldTest {

    /**
     * @since 0.1.0
     */
    @Test
    @Override
    @DisplayName(SINGLE_DESCRIPTION)
    final void shouldAccept_onlyOneValue() {
      // Act / Assert
      Assertions.assertEquals("true", Single.of(true).get());
    }

    /**
     * @since 0.1.0
     */
    @Test
    @Override
    @DisplayName(MULTIPLE_DESCRIPTION)
    final void shouldConcat_multipleParameters_with_comma() {
      // Act
      final var SUBJECT = Multiple.of(Boolean.TRUE, Boolean.FALSE, Boolean.TRUE);
      // Assert
      Assertions.assertEquals("true, false, true", SUBJECT.get());
    }
  }

  /**
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  @Nested
  @DisplayName("SQL DATE-related field test cases.")
  final class DateField extends BaseFieldTest {

    /**
     * @since 0.1.0
     */
    @Test
    @Override
    @DisplayName(SINGLE_DESCRIPTION)
    final void shouldAccept_onlyOneValue() {
      // Act / Assert
      Assertions.assertEquals("'2019-10-18'", Multiple.of(LocalDate.of(2019, 10, 18)).get());
    }

    /**
     * @since 0.1.0
     */
    @Test
    @Override
    @DisplayName(MULTIPLE_DESCRIPTION)
    final void shouldConcat_multipleParameters_with_comma() {
      // Arrange
      final var ld = LocalDate.of(2019, 10, 18);
      // Act
      final var SUBJECT = Multiple.of(ld, ld, ld);
      // Assert
      Assertions.assertEquals("'2019-10-18', '2019-10-18', '2019-10-18'", SUBJECT.get());
    }

    /**
     * @since 0.1.0
     */
    @Test
    @DisplayName("Should format local date to DATE field.")
    final void shouldFormat_localDate_toDateField() {
      // Arrange
      final var LD = LocalDate.of(2019, 10, 18);
      // Act
      final var SUBJECT = Single.of(LD);

      final var SUBJECT_2 = Multiple.of(LD, LD);
      // Assert
      Assertions.assertEquals("'2019-10-18'", SUBJECT.get());

      Assertions.assertEquals("'2019-10-18', '2019-10-18'", SUBJECT_2.get());
    }

    /**
     * @since 0.1.0
     */
    @Test
    @DisplayName("Should format LocalDateTime to DATE field.")
    final void shouldFormat_localDateTime_toDateField() {
      // Arrange
      final var LDT = LocalDateTime.of(2019, 12, 11, 0, 0);
      // Act
      final var SUBJECT = Single.of(LDT);

      final var SUBJECT_2 = Multiple.of(LDT, LDT);
      // Assert
      Assertions.assertEquals("'2019-12-11 12:00:00'", SUBJECT.get());

      Assertions.assertEquals("'2019-12-11 12:00:00', '2019-12-11 12:00:00'", SUBJECT_2.get());
    }
  }
}
