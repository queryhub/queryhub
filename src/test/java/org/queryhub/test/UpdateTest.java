package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Single;
import org.queryhub.steps.Where.Condition;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.UPDATE_TAG)
@DisplayName("UPDATE-related test cases.")
final class UpdateTest extends BaseTest {

  @Test
  @DisplayName("Should write UPDATE query.")
  final void shouldWrite_updateQuery() {
    // Arrange
    final String QUERY = "UPDATE 'table_1' SET 'field_1' = 'value_1' , 'field_2' = 'value_2';";
    // Act
    final String result = Query
        .update(Single.of(TABLE_1))
        .set(Single.of(FIELD_1), Single.of(VALUE_1))
        .and(Single.of(FIELD_2), Single.of(VALUE_2))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }

  @Test
  @DisplayName("Should write UPDATE query with filter clause.")
  final void shouldWrite_updateQuery_withClause() {
    // Arrange
    final String QUERY = "UPDATE 'table_1' SET 'field_1' = 'value_1' , 'field_2' = 'value_2' WHERE 'field_1' IN ('field_2') AND 'field_1' IN ('field_2');";
    // Act
    final String result = Query
        .update(Single.of(TABLE_1))
        .set(Single.of(FIELD_1), Single.of(VALUE_1))
        .and(Single.of(FIELD_2), Single.of(VALUE_2))
        .where(Single.of(FIELD_1), Single.of(FIELD_2))
        .where(Condition.AND, Single.of(FIELD_1), Single.of(FIELD_2))
        .build();
    // Assert
    Assertions.assertEquals(QUERY, result);
  }
}
