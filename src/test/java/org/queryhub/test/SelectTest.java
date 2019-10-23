package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Multiple;
import org.queryhub.field.Single;
import org.queryhub.steps.Where.Condition;
import org.queryhub.steps.Where.Relation;

/**
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
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
        .select(Single.of(TABLE_1), Single.of(FIELD_1))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should append WHERE clause to SELECT query.")
  final void shouldAppend_whereClause_toSelectQuery() {
    // Arrange
    final String QUERY = "SELECT 'field_1' FROM 'table_1' WHERE 'field_1' <= 'field_2';";
    // Act
    final String result = Query
        .select(Single.of(TABLE_1), Single.of(FIELD_1))
        .where(Single.of(FIELD_1), Relation.LTE, Single.of(FIELD_2))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should append WHERE clause sequentially to SELECT query.")
  final void shouldAppend_whereClauseSequentially_toSelectQuery() {
    // Arrange
    final String QUERY = "SELECT 'field_1' FROM 'table_1' WHERE 'field_1' < 'field_2' AND 'field_2' LIKE 'field_1';";
    // Act
    final String result = Query
        .select(Single.of(TABLE_1), Single.of(FIELD_1))
        .where(Single.of(FIELD_1), Relation.LT, Single.of(FIELD_2))
        .where(Condition.AND, Single.of(FIELD_2), Relation.LIKE, Single.of(FIELD_1))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should append SELECT query to WHERE clause compositely.")
  final void shouldAppend_selectQuery_toWhereClause_compositely() {
    // Arrange
    final String QUERY = "SELECT 'field_1' FROM 'table_1' WHERE 'field_1' IN (SELECT 'field_1', "
        + "'field_2' FROM 'table_1') AND 'field_2' IN (SELECT 'field_1' FROM 'table_2');";
    // Act
    final String result = Query
        .select(Single.of(TABLE_1), Single.of(FIELD_1))
        .where(Single.of(FIELD_1), Query.select(Single.of(TABLE_1), Multiple.of(FIELD_1, FIELD_2)))
        .where(Condition.AND, Single.of(FIELD_2), Query.select(Single.of(TABLE_2), Single.of(FIELD_1)))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }
}
