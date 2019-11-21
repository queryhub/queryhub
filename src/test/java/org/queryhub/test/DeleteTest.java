package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Multiple;
import org.queryhub.field.Single;
import org.queryhub.steps.Where.Relation;

/**
 * Defines {@code DELETE}-related test cases.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.DELETE_TAG)
@DisplayName("DELETE-related test cases.")
final class DeleteTest extends BaseTest {

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should build DELETE query.")
  final void shouldBuild_deleteQuery() {
    // Arrange
    final var expected = "DELETE FROM `table_1`;";
    // Act
    final var result = Query.delete(Single.of(TABLE_1)).build();
    // Assert
    Assertions.assertAll(
        () -> Statement.DELETE_UPDATE.test(expected),
        () -> Assertions.assertEquals(expected, result));
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should build DELETE query with condition.")
  final void shouldBuild_deleteQuery_withCondition() {
    // Arrange
    final var expected =
        "DELETE FROM `table_1` WHERE `field_1` IN (`field_2`);";
    // Act
    final var result = Query
        .delete(Single.of(TABLE_1))
        .where(Single.of(FIELD_1), Single.of(FIELD_2))
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
  @DisplayName("Should build delete query with additive conditions.")
  final void shouldBuild_deleteQuery_withAdditiveConditions() {
    // Arrange
    final var expected =
        "DELETE FROM `table_1` WHERE `field_1` >= `field_2` AND `field_1` > `field_2`;";
    // Act
    final var result = Query
        .delete(Single.of(TABLE_1))
        .where(Single.of(FIELD_1), Relation.GTE, Single.of(FIELD_2))
        .and(Single.of(FIELD_1), Relation.GT, Single.of(FIELD_2))
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
  @DisplayName("Should build delte query with alternative conditions.")
  final void shouldBuild_deleteQuery_withAlternativeConditions() {
    // Arrange
    final var expected =
        "DELETE FROM `table_1` WHERE `field_1` != `field_2` OR `field_1` IN (`field_1`, `field_2`);";
    // Act
    final var result = Query
        .delete(Single.of(TABLE_1))
        .where(Single.of(FIELD_1), Relation.NEQ, Single.of(FIELD_2))
        .or(Single.of(FIELD_1), Multiple.of(FIELD_1, FIELD_2))
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
  @DisplayName("Should build delete query with composite fragments")
  final void shouldBuild_deleteQuery_withCompositeFragments() {
    // Arrange
    final var expected =
        "DELETE FROM `table_1` WHERE `field_1` != `field_2` OR `field_1` IN (SELECT `field_2` FROM `table_2`);";
    // Act
    final var result = Query
        .delete(Single.of(TABLE_1))
        .where(Single.of(FIELD_1), Relation.NEQ, Single.of(FIELD_2))
        .or(Single.of(FIELD_1), Query.select(Single.of(TABLE_2), Single.of(FIELD_2)))
        .build();
    // Assert
    Assertions.assertAll(
        () -> Statement.DELETE_UPDATE.test(expected),
        () -> Assertions.assertEquals(expected, result));
  }
}
