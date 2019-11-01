package org.queryhub.test;

import java.util.function.Predicate;
import org.junit.jupiter.api.Assertions;
import org.queryhub.test.helper.TestClient;
import org.queryhub.test.helper.TestClient.SQlHandler;

/**
 * Defines general resources and fixtures for the implementing test cases' classes.
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

  // Methods

  /**
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Statement implements Predicate<String> {
    QUERY(java.sql.Statement::executeQuery),
    DELETE_UPDATE(java.sql.Statement::executeUpdate),
    // DDL(Statement::execute),
    ;
    private final SQlHandler handler;

    // TODO Should replace for a QueryHub statement building application.
    private static final String DEFAULT_SCHEMA = ""
        + "CREATE TABLE `table_1` (`field_1` VARCHAR, `field_2` VARCHAR );"
        + "CREATE TABLE `table_2` (`field_1` VARCHAR, `field_2` VARCHAR );";

    /**
     * {@inheritDoc}
     *
     * @since 0.1.0
     */
    Statement(final SQlHandler handler) {
      this.handler = handler;
    }

    // TODO: DDL might need to implement another methods.

    /**
     * Parses a query. Might assert failure if any exception from the {@link TestClient testing
     * client} is thrown.
     *
     * @param query The query string to be parsed.
     * @since 0.1.0
     */
    @Override
    public final boolean test(final String query) {
      TestClient.parse(DEFAULT_SCHEMA, query, handler).findFirst().ifPresent(Assertions::fail);
      return Boolean.TRUE;
    }
  }
}