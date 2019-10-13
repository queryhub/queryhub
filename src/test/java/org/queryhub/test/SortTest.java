package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Field;
import org.queryhub.steps.Sort.Aggregate;
import org.queryhub.steps.Sort.Order;
import org.queryhub.steps.Sort.Type;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
@Tag(BaseTest.SORT_TAG)
@DisplayName("GROUP BY / ORDER BY-related test cases.")
final class SortTest extends BaseTest {

  @Test
  @DisplayName("Should append implicitly keyword ASC to GROUP BY clause.")
  final void shouldAppendImplicitly_keywordAsc_toGroupByClause() {
    // Arrange
    final var EXPECTED = "SELECT 'field_1' FROM 'table_1' GROUP BY 'field_1' ASC;";
    // Act
    final var RESULT = Query
        .select(Field.of(TABLE_1), Field.of(FIELD_1))
        .sort(Type.GROUP_BY, Aggregate.of(FIELD_1))
        .build();
    // Assert
    Assertions.assertEquals(EXPECTED, RESULT);
  }

  @Test
  @DisplayName("Should append keyword DESC to ORDER BY clause.")
  final void shouldAppend_keywordDesc_toOrderByClause() {
    // Arrange
    final var EXPECTED = "SELECT 'field_1' FROM 'table_1' ORDER BY 'field_1' DESC;";
    // Act
    final var RESULT = Query
        .select(Field.of(TABLE_1), Field.of(FIELD_1))
        .sort(Type.ORDER_BY, Aggregate.of(FIELD_1, Order.DESC))
        .build();
    // Assert
    Assertions.assertEquals(EXPECTED, RESULT);
  }

  @Test
  @DisplayName("Should append sequentially ORDER BY and GROUP BY clause.")
  final void shouldAppendSequentially_toOrderByAndGroupByClause() {
    // Arrange
    final var EXPECTED = "SELECT 'field_2' FROM 'table_1' ORDER BY 'field_2' DESC GROUP BY 'field_1' ASC;";
    // Act
    final var RESULT = Query
        .select(Field.of(TABLE_1), Field.of(FIELD_2))
        .sort(Type.ORDER_BY, Aggregate.of(FIELD_2, Order.DESC))
        .sort(Type.GROUP_BY, Aggregate.of(FIELD_1))
        .build();
    // Assert
    Assertions.assertEquals(EXPECTED, RESULT);
  }

  @Test
  @DisplayName("Should append compositely field.")
  final void shouldAppendCompositely_toOrderByAndGroupByClause() {
    // Arrange
    final var EXPECTED = "SELECT 'field_2' FROM 'table_1' ORDER BY 'field_2' ASC, 'field_1' DESC, 'field_2' ASC;";
    // Act
    final var RESULT = Query
        .select(Field.of(TABLE_1), Field.of(FIELD_2))
        .sort(Type.ORDER_BY,
            Aggregate.of(FIELD_2),
            Aggregate.of(FIELD_1, Order.DESC),
            Aggregate.of(FIELD_2, Order.ASC))
        .build();
    // Assert
    Assertions.assertEquals(EXPECTED, RESULT);
  }
}
