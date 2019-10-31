package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.field.Aggregate;
import org.queryhub.field.Aggregate.Type;

/**
 * Defines {@link Aggregate}'s test cases.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.AGGREGATE_TAG)
@DisplayName("Aggregate functions' test cases.")
final class AggregateTest extends BaseTest {

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should aggregate function to passed field.")
  final void shouldAppend_aggregateFunction_toPassedField() {
    // Act / Assert
    Assertions.assertEquals("COUNT('field_1')", Aggregate.of(Type.COUNT, FIELD_1).get());
    Assertions.assertEquals("AVG('field_2')", Aggregate.of(Type.AVG, FIELD_2).get());
    Assertions.assertEquals("MIN('field_1')", Aggregate.of(Type.MIN, FIELD_1).get());
    Assertions.assertEquals("MAX('field_2')", Aggregate.of(Type.MAX, FIELD_2).get());
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should append 'DISTINCT' keyword to aggregate function.")
  final void shouldAppend_distinctKeyword_toAggregateFunction() {
    // Act / Assert
    Assertions.assertEquals("MAX(DISTINCT COUNT(DISTINCT 'field_1'))",
        Aggregate.of(Type.MAX, Boolean.TRUE, Aggregate.of(Type.COUNT, Boolean.TRUE, FIELD_1))
            .get());

    Assertions.assertEquals("COUNT(MAX(DISTINCT 'field_2'))",
        Aggregate.of(Type.COUNT, Aggregate.of(Type.MAX, Boolean.TRUE, FIELD_2)).get());

    Assertions.assertEquals("MIN(DISTINCT AVG('field_1'))",
        Aggregate.of(Type.MIN, Boolean.TRUE, Aggregate.of(Type.AVG, FIELD_1)).get());

    Assertions.assertEquals("COUNT(MAX('field_2'))",
        Aggregate.of(Type.COUNT, Aggregate.of(Type.MAX, FIELD_2)).get());

    Assertions.assertEquals("AVG(MIN('field_1'), MAX('field_2'))",
        Aggregate.of(Type.AVG, Aggregate.of(Type.MIN, FIELD_1), Aggregate.of(Type.MAX, FIELD_2))
            .get());
  }
}
