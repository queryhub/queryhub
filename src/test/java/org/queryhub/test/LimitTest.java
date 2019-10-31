package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Aggregate;
import org.queryhub.field.Field.Constants;
import org.queryhub.field.Single;
import org.queryhub.steps.Limit;

/**
 * Defines {@link Limit {@code LIMIT}}'s test cases.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.LIMIT_TAG)
@DisplayName("LIMIT-related test cases.")
final class LimitTest extends BaseTest {

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should append skip parameter as zero and offset parameter.")
  final void shouldAppend_skipParameter_asZero_and_offsetParameter() {
    // Act / Assert
    Assertions.assertEquals("SELECT * FROM 'table_1' WHERE 'field_1' IN ('value_1') LIMIT 0 , 10;",
        Query.select(Single.of(TABLE_1), Constants.ALL.getField())
            .where(Single.of(FIELD_1), Single.of(VALUE_1))
            .limit(10).build());
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should append skip parameter and offset parameter.")
  final void shouldAppend_skipParameter_and_offsetParameter() {
    // Act / Assert
    Assertions.assertEquals("SELECT * FROM 'table_1' LIMIT 10 , 30;",
        Query.select(Single.of(TABLE_1), Constants.ALL.getField()).limit(10, 30).build());
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should throw IllegalArgumentException when passing negative values.")
  final void shouldThrow_illegalArgumentException_whenPassing_negativeParameter() {
    // Assert
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      // Act
      Query.select(Single.of(TABLE_1), Constants.ALL.getField()).limit(-1);
    });

    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      // Act
      Query.select(Single.of(TABLE_1), Constants.ALL.getField()).limit(-1, 0);
    });
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should not accept offset parameter lesser than skip parameter.")
  final void shouldNotAccept_offsetParameter_lesserThan_skipParameter() {
    // Assert
    Assertions.assertThrows(IllegalArgumentException.class, () -> {
      // Act
      Query.select(Single.of(TABLE_1), Constants.ALL.getField()).limit(10, 9);
    });
  }
}
