package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Single;
import org.queryhub.steps.Update;

/**
 * Defines {@link Update {@code UPDATE}}-related test cases.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.UPDATE_TAG)
@DisplayName("UPDATE-related test cases.")
final class UpdateTest extends BaseTest {

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should write UPDATE query.")
  final void shouldWrite_updateQuery() {
    // Arrange
    final var expected =
        // FIXME: Values must have single quote and field/table names must have back-ticks
        "UPDATE `table_1` SET `field_1` = `value_1` , `field_2` = `value_2`;";
    // Act
    final var result = Query
        .update(Single.of(TABLE_1))
        .set(Single.of(FIELD_1), Single.of(VALUE_1))
        .and(Single.of(FIELD_2), Single.of(VALUE_2))
        .build();
    // Assert
    Assertions.assertAll(
        () -> Statement.DELETE_UPDATE.test(expected),
        () -> Assertions.assertEquals(expected, result));
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should write UPDATE query with filter clause.")
  final void shouldWrite_updateQuery_withClause() {
    // Arrange
    final var expected = ""
        // FIXME: Values must have single quote and field/table names must have back-ticks
        + "UPDATE `table_1` SET `field_1` = `value_1` , `field_2` = `value_2` WHERE `field_1` IN "
        + "(`field_2`) AND `field_1` IN (`field_2`);";
    // Act
    final var result = Query
        .update(Single.of(TABLE_1))
        .set(Single.of(FIELD_1), Single.of(VALUE_1))
        .and(Single.of(FIELD_2), Single.of(VALUE_2))
        .where(Single.of(FIELD_1), Single.of(VALUE_2))
        .and(Single.of(FIELD_2), Single.of(VALUE_1))
        .build();
    // Assert
    Assertions.assertAll(
        () -> Statement.DELETE_UPDATE.test(expected),
        () -> Assertions.assertEquals(expected, result));
  }
}
