package org.queryhub.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.queryhub.Query;
import org.queryhub.field.Single;
import org.queryhub.steps.Terminal;

/**
 * Defines {@link Terminal}-related test cases.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
@Tag(BaseTest.TERMINAL_TAG)
@DisplayName("Terminal methods test cases.")
final class TerminalTest extends BaseTest {

  private Terminal subject;

  /**
   * @since 0.1.0
   */
  @BeforeEach
  final void prepareScenario() {
    // Arrange
    subject = Query.select(Single.of(TABLE_1), Single.of(FIELD_1));
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("Build method should append semicolon.")
  final void buildMethod_shouldAppend_semiColon() {
    // Act / Assert
    Assertions.assertEquals("SELECT 'field_1' FROM 'table_1';", subject.build());
  }

  /**
   * @since 0.1.0
   */
  @Test
  @DisplayName("false boolean Build method should not semicolon.")
  final void falseBooleanBuildMethod_shouldNotAppend_semiColon() {
    // Act / Assert
    Assertions.assertEquals("SELECT 'field_1' FROM 'table_1'", subject.build(Boolean.FALSE));
  }

  /**
   * @since 0.1.0
   */
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
    final var q = Query.select(Single.of(TABLE_1), Single.of(FIELD_1));
    // Act / Assert
    Assertions.assertEquals(q.build(), subject.toString());
  }
}
