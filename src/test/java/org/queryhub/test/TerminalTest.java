package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Field;
import org.queryhub.steps.Terminal;

@Tag(BaseTest.TERMINAL_TAG)
@DisplayName("Terminal methods test cases.")
final class TerminalTest extends BaseTest {

  private Terminal subject;

  @BeforeEach
  final void prepareScenario() {
    // Arrange
    subject = Query.select(Field.of(TABLE_1), Field.of(FIELD_1));
  }

  @Test
  @DisplayName("Build method should append semicolon.")
  final void buildMethod_shouldAppend_semiColon() {
    // Act / Assert
    Assertions.assertEquals("SELECT 'field_1' FROM 'table_1';", subject.build());
  }

  @Test
  @DisplayName("false boolean Build method should not semicolon.")
  final void falseBooleanBuildMethod_shouldNotAppend_semiColon() {
    // Act / Assert
    Assertions.assertEquals("SELECT 'field_1' FROM 'table_1'", subject.build(Boolean.FALSE));
  }

  @Test
  @DisplayName("Build method should finish statement building after being called.")
  final void buildMethod_shouldFinish_statementBuilding_afterBeingCalled() {
    // Act
    subject.build();
    // Assert
    Assertions.assertThrows(IllegalStateException.class, subject::build);

    Assertions.assertThrows(IllegalStateException.class, subject::toString);
  }

  @Test
  @DisplayName("toString() method should have build()'s same behavior.")
  final void toStringMethod_shouldHave_buildSameBehavior() {
    // Arrange
    final var q = Query.select(Field.of(TABLE_1), Field.of(FIELD_1));
    // Act / Assert
    Assertions.assertEquals(q.build(), subject.toString());
  }
}
