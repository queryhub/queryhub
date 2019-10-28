package org.queryhub.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.BooleanSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.field.Field.Constants;
import org.queryhub.field.Multiple;
import org.queryhub.field.Single;

/**
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

  @Test
  @DisplayName("Should expose all as asterisk.")
  final void shouldExpose_all_as_asterisk() {
    // Act / Assert
    Assertions.assertEquals("*", Constants.ALL.getField().get());
  }

  @Test
  @DisplayName("Should expose variable as question mark.")
  final void shouldExpose_variable_as_questionMark() {
    // Act / Assert
    Assertions.assertEquals("?", Constants.VARIABLE.getField().get());
  }

  @Test
  @DisplayName("Should ignore escaped double quotes.")
  final void shouldIgnore_escapedDoubleQuotes() {
    // Act / Assert
    Assertions.assertEquals("'1'", Single.of("\"1\"").get());
  }

  @Test
  @DisplayName("Should not append 'DISTINCT' keyword.")
  final void shouldNotAppend_distinctKeyword() {
    // Act / Assert
    Assertions.assertEquals("'field_1'", Single.of(FIELD_1).get());

    Assertions.assertEquals("'field_1', 'field_2'", Multiple.of(FIELD_1, FIELD_2).get());
  }

  @Test
  @DisplayName("Should append 'DISTINCT' keyword.")
  final void shouldAppend_distinctKeyword() {
    // Act / Assert
    Assertions.assertEquals("DISTINCT 'field_1'", Single.of(Boolean.TRUE, FIELD_1).get());
    Assertions.assertEquals("DISTINCT 'field_1', 'field_2'",
        Multiple.of(Boolean.TRUE, FIELD_1, FIELD_2).get());
  }

  @SuppressWarnings("unused")
  static abstract class BaseFieldTest {

    final String SINGLE_DESCRIPTION = "Should accept only value.";

    abstract void shouldAccept_onlyOneValue();

    final String MULTIPLE_DESCRIPTION = "Should concatenate multiple parameters with comma.";

    abstract void shouldConcat_multipleParameters_with_comma();
  }

  @Nested
  @DisplayName("SQL NUMBER-related field test cases.")
  final class IntegerField extends BaseFieldTest {

    @Test
    @Override
    @DisplayName(SINGLE_DESCRIPTION)
    final void shouldAccept_onlyOneValue() {
      // Act /  Assert
      Assertions.assertEquals("'1'", Single.of(1L).get());
    }

    @Test
    @Override
    @DisplayName(MULTIPLE_DESCRIPTION)
    final void shouldConcat_multipleParameters_with_comma() {
      // Act
      final var SUBJECT = Multiple.of(1, 2, 3);
      // Assert
      Assertions.assertEquals("'1', '2', '3'", SUBJECT.get());
    }
  }

  @Nested
  @DisplayName("SQL BOOLEAN-related field test cases.")
  final class BooleanField extends BaseFieldTest {

    @Test
    @Override
    @DisplayName(SINGLE_DESCRIPTION)
    final void shouldAccept_onlyOneValue() {
      // Act / Assert
      Assertions.assertEquals("'true'", Single.of(() -> true).get());
    }

    @Test
    @Override
    @DisplayName(MULTIPLE_DESCRIPTION)
    final void shouldConcat_multipleParameters_with_comma() {
      // Arrange
      final BooleanSupplier SUP_TRUE = () -> Boolean.TRUE;
      final BooleanSupplier SUP_FALSE = () -> Boolean.FALSE;
      // Act
      final var SUBJECT = Multiple.of(SUP_TRUE, SUP_FALSE, SUP_TRUE);
      // Assert
      Assertions.assertEquals("'true', 'false', 'true'", SUBJECT.get());
    }
  }

  @Nested
  @DisplayName("SQL DATE-related field test cases.")
  final class DateField extends BaseFieldTest {

    @Test
    @Override
    @DisplayName(SINGLE_DESCRIPTION)
    final void shouldAccept_onlyOneValue() {
      // Act / Assert
      Assertions.assertEquals("'2019-10-18'", Multiple.of(LocalDate.of(2019, 10, 18)).get());
    }

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
