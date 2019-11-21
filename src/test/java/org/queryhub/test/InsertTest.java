package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Single;
import org.queryhub.steps.Insert;

/**
 * Defines {@link Insert {@code INSERT}}`s test cases.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.INSERT_TAG)
@DisplayName("INSERT-related test cases.")
final class InsertTest extends BaseTest {

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should build insert query with values.")
  final void shouldBuild_insertQuery_withValues() {
    // Arrange
    // FIXME: Column count does not match; SQL statement:
    final var expected = "INSERT INTO `table_1` VALUES (`value_1`);";
    // Act
    final var result = Query
        .insert(Single.of(TABLE_1))
        .values(Single.of(VALUE_1))
        .build();
    // Assert
    Assertions.assertAll(
        () -> Statement.QUERY.test(expected),
        () -> Assertions.assertEquals(expected, result));
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should build insert query based on select query.")
  final void shouldBuild_insertQuery_basedOn_selectQuery() {
    // Arrange
    // FIXME: Column count does not match; SQL statement:
    final var expected = "INSERT INTO `table_1` VALUES (SELECT `field_2` FROM `table_2`);";
    // Act
    final var result = Query
        .insert(Single.of(TABLE_1))
        .values(Query.select(Single.of(TABLE_2), Single.of(FIELD_2)))
        // The line below should not compile
        // .values(Query.update(Field.of(TABLE_2)).set(Field.of(FIELD_2), Field.of(FIELD_2)))
        .build();
    // Assert
    Assertions.assertAll(
        () -> Statement.QUERY.test(expected),
        () -> Assertions.assertEquals(expected, result));
  }
}