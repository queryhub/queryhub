package org.queryhub.test.helper;

import java.lang.System.Logger;
import java.lang.System.Logger.Level;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * SQL client for testing purposes, based on <a href="http://www.h2database.com">H2 technology</a>.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public final class TestClient {

  /**
   * Utility type to ease exception handling in SQL execution.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  public interface SQlHandler {

    /**
     * Handles a SQL statement that throws an corresponding exception.
     *
     * @param statement A prepared statement instance.
     * @param query     The query to be parsed.
     * @throws SQLException if the query is not valid.
     * @since 0.1.0
     */
    void handle(final Statement statement, final String query) throws SQLException;
  }

  /**
   * Indicates the available compatibility modes supported by <a href="http://www.h2database.com">H2
   * technology</a>.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @see <a href="http://www.h2database.com/html/features.html#compatibility">H2's compability
   * modes</a>
   * @since 0.1.0
   */
  private enum Mode {
    DB2("DB2"),
    DERBY("Derby"),
    HSQLDB("HSQLDB"),
    MSSQL("MSSQLServer"),
    MYSQL("MySQL"),
    ORACLE("Oracle"),
    POSTGRES("PostgreSQL"),
    // Although is present in H2 documentation. it seems it does not work here.
    // IGNITE("Ignite"),
    ;
    private final String name;

    private static final String PREFIX = "jdbc:h2:mem:queryhub;MODE=";

    /**
     * Non-visible constructor.
     *
     * @since 0.1.0
     */
    Mode(final String name) {
      this.name = name;
    }

    /**
     * Gets the complete H2 url with compatibility mode.
     *
     * @return Valid url for connecting ti H2 databases' engines.
     * @since 0.1.0
     */
    private String getUrl() {
      return PREFIX + name;
    }
  }

  static {
    System.setProperty(
        "java.util.logging.SimpleFormatter.format",
        "%1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS %4$s %2$s %5$s%6$s%n");
  }

  private static final Logger LOGGER = System.getLogger(TestClient.class.getSimpleName());

  /**
   * Non-visible constructor.
   *
   * @since 0.1.0
   */
  private TestClient() {
  }

  /**
   * Parses out a query and collects thrown exceptions if any. This is intended to evaluate syntax
   * and SQL constraints mediated by H2's engine, according to its {@link Mode provided modes}.
   *
   * @param schema  A DDL query which is going to serve as reference to evaluate the given query.
   * @param query   The query string to be parsed.
   * @param handler A {@link Statement SQL prepared statement} to handle the provided reference
   *                schema and query to be validate.
   * @return Stream of thrown {@link Throwable exceptions}, if any.
   * @since 0.1.0
   */
  public static Stream<Throwable>
  parse(final String schema, final String query, final SQlHandler handler) {
    return Arrays.stream(Mode.values()).flatMap(comp -> {
      try (final var conn = DriverManager.getConnection(comp.getUrl());
          final var stmt = conn.createStatement()) {
        LOGGER.log(Level.INFO, () -> String.format("%s: Connected.", comp));
        conn.setAutoCommit(Boolean.FALSE);
        conn.createStatement().execute(schema);
        handler.handle(stmt, query);
        conn.setAutoCommit(Boolean.TRUE);
        LOGGER.log(Level.INFO, () -> String.format
            ("%s: Interacted with %S successfully.\\n", comp, query));
        return Stream.empty();
      } catch (final SQLException e) {
        LOGGER.log(Level.ERROR, () -> String.format("%s: %s.\\n", comp, e.getMessage()), e);
        return StreamSupport.stream
            (new SQLException(comp + ": " + e.getMessage()).spliterator(), Boolean.FALSE);
      }
    });
  }
}