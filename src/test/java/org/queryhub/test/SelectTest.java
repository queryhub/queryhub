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
}
