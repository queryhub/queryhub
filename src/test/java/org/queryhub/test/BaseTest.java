package org.queryhub.test;

import java.sql.DriverManager;
import org.junit.jupiter.api.Assertions;

/**
 * Defines general resources for the implementing test cases' classes.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
abstract class BaseTest {

  // Test Tags

  static final String EQUALS_HASHCODE_TAG = "equality_tag";
  static final String FIELD_TAG = "field_tag";
  static final String AGGREGATE_TAG = "aggregate_tag";
  static final String SELECT_TAG = "select_tag";
  static final String INSERT_TAG = "insert_tag";
  static final String UPDATE_TAG = "update_tag";
  static final String DELETE_TAG = "delete_tag";
  static final String SORT_TAG = "sort_tag";
  static final String LIMIT_TAG = "limit_tag";
  static final String TERMINAL_TAG = "terminal_tag";

  // Test schema values

  static final String TABLE_1 = "table_1";
  static final String TABLE_2 = "table_2";
  static final String FIELD_1 = "field_1";
  static final String FIELD_2 = "field_2";
  static final String VALUE_1 = "value_1";
  static final String VALUE_2 = "value_2";

  /**
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  static final class Client {

    private static final String URL = "jdbc:h2:mem:";
    // TODO Should replace for a QueryHub statement building application.
    private static final String DEFAULT_SCHEMA = "CREATE TABLE \"table_1\" (\"field_1\" VARCHAR, \"field_2\" VARCHAR );";

    /**
     * @since 0.1.0
     */
    private Client() {
    }

    // TODO Implement DDL syntax assertion.

    /**
     * Evaluates a data manipulation language query syntax based on a default schema
     * representation.
     * <p>
     * Might cause failure if the provided query syntax is invalid under {@link org.h2} terms.
     *
     * @param query A query to be evaluated.
     * @return The given parameter, after passing through {@link org.h2} evaluation.
     * @see #assertDML(String, String)
     * @since 0.1.0
     */
    static String assertDML(final String query) {
      return assertDML(query, DEFAULT_SCHEMA);
    }

    /**
     * Evaluates a data manipulation language query syntax based on a given schema representation.
     * <p>
     * Might cause failure if the provided query syntax is invalid under {@link org.h2} terms.
     *
     * @param query  A query to be evaluated.
     * @param schema A DDL query representing which schema {@code query} should be based on.
     * @return A query string representation, same as given parameter, after passing through {@link
     * org.h2} evaluation.
     * @since 0.1.0
     */
    private static String assertDML(final String query, final String schema) {
      Assertions.assertDoesNotThrow(() -> {
        final var stmt = DriverManager.getConnection(URL).createStatement();
        stmt.execute(schema);
        return stmt.executeQuery(query);
      }, "Syntax not acceptable.");
      return query;
    }
  }
}
