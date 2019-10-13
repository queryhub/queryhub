package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Field;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
@Tag(BaseTest.INSERT_TAG)
@DisplayName("INSERT-related test cases.")
final class InsertTest extends BaseTest {

  @Test
  @DisplayName("Should build insert query with values.")
  final void shouldBuild_insertQuery_withValues() {
    // Arrange
    final String QUERY = "INSERT INTO 'table_1' VALUES ('value_1');";
    // Act
    final String result = Query
        .insert(Field.of(TABLE_1))
        .values(Field.of(VALUE_1))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should build insert query based on select query.")
  final void shouldBuild_insertQuery_basedOn_selectQuery() {
    // Arrange
    final String QUERY = "INSERT INTO 'table_1' VALUES (SELECT 'field_2' FROM 'table_2');";
    // Act
    final String result = Query
        .insert(Field.of(TABLE_1))
        .values(Query.select(Field.of(TABLE_2), Field.of(FIELD_2)))
        // The line below should not compile
        // .values(Query.update(Field.of(TABLE_2)).set(Field.of(FIELD_2), Field.of(FIELD_2)))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }
}