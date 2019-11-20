package org.queryhub.test;

import java.util.HashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Single;
import org.queryhub.steps.Terminal.Select;

/**
 * Defines equality-related test cases.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.EQUALS_HASHCODE_TAG)
@DisplayName("Equality test cases.")
final class EqualityTest extends BaseTest {

  private static final Select ONE = Query.select(Single.of(TABLE_1), Single.of(FIELD_1));

  private static final Select SAME = ONE;

  private static final Select OTHER_SAME = ONE;

  private static final Select DIFFERENT = Query.select(Single.of(TABLE_2), Single.of(FIELD_2));

  private static final Select CLOSED = Query.select(Single.of(TABLE_2), Single.of(FIELD_2));

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should return false for null parameter.")
  final void shouldReturn_false_for_nullParameter() {
    // Assert
    Assertions.assertEquals(Boolean.FALSE, ONE.equals(null));
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should return false for another class' parameter.")
  final void shouldReturn_false_for_otherClassParameter() {
    // Assert
    Assertions.assertEquals(Boolean.FALSE, ONE.equals(""));
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should be reflexive.")
  final void shouldBe_reflexive() {
    // Assert
    Assertions.assertEquals(Boolean.TRUE, ONE.equals(SAME));
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should be transitive.")
  final void shouldBe_transitive() {
    // Assert
    Assertions.assertEquals(Boolean.TRUE, ONE.equals(OTHER_SAME));
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should be symmetric.")
  final void shouldBe_symmetric() {
    // Assert
    Assertions.assertEquals(Boolean.TRUE, ONE.equals(SAME));

    Assertions.assertEquals(Boolean.TRUE, SAME.equals(ONE));
  }


  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should evaluate closed instances as different.")
  final void shouldEvaluate_closedInstances_asDifferent() {
    // Arrange
    CLOSED.build();
    // Assert
    Assertions.assertNotEquals(Boolean.TRUE, CLOSED.equals(ONE));
  }


  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Should be symmetric.")
  final void shouldNot_collide() {
    // Arrange
    final var set = new HashSet<>();
    set.add(ONE);
    set.add(SAME);
    set.add(DIFFERENT);
    // Assert
    Assertions.assertEquals(2, set.size());
  }
}
