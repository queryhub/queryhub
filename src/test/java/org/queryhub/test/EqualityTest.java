package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Single;

@Tag(BaseTest.EQUALS_HASHCODE_TAG)
@DisplayName("Equality test cases.")
final class EqualityTest extends BaseTest {

  @Test
  @DisplayName("Should be equal.")
  final void shouldBe_equal() {
    // Arrange
    final var ONE = Query.select(Single.of(TABLE_1), Single.of(FIELD_1));

    final var OTHER = ONE;

    final var ANOTHER = Query.select(Single.of(TABLE_1), Single.of(FIELD_1));
    // Act / Assert
    Assertions.assertEquals(ONE, ONE);

    Assertions.assertEquals(ONE, OTHER);

    Assertions.assertNotEquals("", ONE);

    Assertions.assertNotEquals(OTHER, ANOTHER);
  }

  @Test
  @DisplayName("Hashcode should be equal.")
  final void hashCode_shouldBe_equal() {
    // Arrange
    final var ONE = Query.select(Single.of(TABLE_1), Single.of(FIELD_1));

    final var ANOTHER = Query.select(Single.of(TABLE_1), Single.of(FIELD_1));
    // Act / Assert
    Assertions.assertSame(ONE, ONE);

    Assertions.assertNotSame(ONE, ANOTHER);
  }
}
