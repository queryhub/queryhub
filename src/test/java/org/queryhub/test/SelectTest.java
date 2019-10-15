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
 * @since 0.1.0
 */
@Tag(BaseTest.SELECT_TAG)
@DisplayName("SELECT-related test cases.")
final class SelectTest extends BaseTest {

  @Test
  @DisplayName("Should build select query.")
  final void shouldBuild_selectQuery() {
    // Arrange
    final String QUERY = "SELECT 'field_1' FROM 'table_1';";
    // Act
    final String result = Query
        .select(Field.of(TABLE_1), Field.of(FIELD_1))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should append WHERE clause to SELECT query..")
  final void shouldAppend_whereClause_toSelectQuery() {
    // Arrange
    final String QUERY = "SELECT 'field_1' FROM 'table_1' WHERE 'field_1' <= 'field_2';";
    // Act
    final String result = Query
        .select(Field.of(TABLE_1), Field.of(FIELD_1))
        .where(Field.of(FIELD_1), Relation.LTE, Field.of(FIELD_2))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should append WHERE clause sequentially to SELECT query..")
  final void shouldAppend_whereClauseSequentially_toSelectQuery() {
    // Arrange
    final String QUERY = "SELECT 'field_1' FROM 'table_1' WHERE 'field_1' < 'field_2' AND 'field_2' LIKE 'field_1';";
    // Act
    final String result = Query
        .select(Field.of(TABLE_1), Field.of(FIELD_1))
        .where(Field.of(FIELD_1), Relation.LT, Field.of(FIELD_2))
        .where(Condition.AND, Field.of(FIELD_2), Relation.LIKE, Field.of(FIELD_1))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }
}
