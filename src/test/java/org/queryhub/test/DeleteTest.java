package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Field;
import org.queryhub.steps.Where.Condition;
import org.queryhub.steps.Where.Relation;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
@Tag(BaseTest.DELETE_TAG)
@DisplayName("DELETE-related test cases.")
final class DeleteTest extends BaseTest {

  @Test
  @DisplayName("Should build DELETE query.")
  final void shouldBuild_deleteQuery() {
    // Arrange
    final String QUERY = "DELETE FROM 'table_1';";
    // Act
    final String result = Query
        .delete(Field.of(TABLE_1))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should build DELETE query with condition.")
  final void shouldBuild_deleteQuery_withCondition() {
    // Arrange
    final String QUERY = "DELETE FROM 'table_1' WHERE 'field_1' = 'field_2';";
    // Act
    final String result = Query
        .delete(Field.of(TABLE_1))
        .where(Field.of(FIELD_1), Relation.EQ, Field.of(FIELD_2))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should build delete query with additive conditions.")
  final void shouldBuild_deleteQuery_withAdditiveConditions() {
    // Arrange
    final String QUERY = "DELETE FROM 'table_1' WHERE 'field_1' >= 'field_2' AND 'field_1' > 'field_2';";
    // Act
    final String result = Query
        .delete(Field.of(TABLE_1))
        .where(Field.of(FIELD_1), Relation.GTE, Field.of(FIELD_2))
        .where(Condition.AND, Field.of(FIELD_1), Relation.GT, Field.of(FIELD_2))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should build delte query with alternative conditions.")
  final void shouldBuild_deleteQuery_withAlternativeConditions() {
    // Arrange
    final String QUERY = "DELETE FROM 'table_1' WHERE 'field_1' != 'field_2' OR 'field_1' <= 'field_2';";
    // Act
    final String result = Query
        .delete(Field.of(TABLE_1))
        .where(Field.of(FIELD_1), Relation.NEQ, Field.of(FIELD_2))
        .where(Condition.OR, Field.of(FIELD_1), Relation.LTE, Field.of(FIELD_2))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should build delete query with composite fragments")
  final void shouldBuild_deleteQuery_withCompositeFragments() {
    // Arrange
    final String QUERY = "DELETE FROM 'table_1' WHERE 'field_1' != 'field_2' OR 'field_1' <= 'field_2';";
    // Act
    final String result = Query
        .delete(Field.of(TABLE_1))
        .where(Field.of(FIELD_1), Relation.NEQ, Field.of(FIELD_2))
        .where(Condition.OR, Field.of(FIELD_1), Relation.LTE, Field.of(FIELD_2))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }
}
