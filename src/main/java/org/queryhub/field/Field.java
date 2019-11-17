package org.queryhub.field;

import java.time.chrono.ChronoLocalDate;
import java.time.chrono.ChronoLocalDateTime;
import java.util.function.Supplier;

/**
 * General representation for a SQL field.
 * <p>
 * The abstraction is aimed to fill the gaps between native keywords in the SQL statements. This
 * means that it should produce valid string segments according to the place where it should be
 * set.
 * <p>
 * Consequently, there will be methods which can accept instances to represent individual or
 * multiple fields, such as {@link org.queryhub.Query#select(Single, Field)} or {@link
 * org.queryhub.Query#update(Single)}.
 * <p>
 * Factories should accept exclusively the cro types in SQL specification, such as {@link String},
 * {@link Integer}, {@link Boolean} and JDK' API present in {@code java.time} package such as {@link
 * ChronoLocalDate} and {@link ChronoLocalDateTime}.
 * <p>
 * By the fact of this abstraction should be a {@link Supplier} specialization, it should return
 * {@link String} instances be lazily evaluated, as the lambda structures usually does.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
public interface Field extends Supplier<String> {

  /**
   * {@link Field}'s utility constants.
   *
   * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
   * @since 0.1.0
   */
  enum Constants implements Field {

    /**
     * Short-hand for wildcard statement parameters (specifically for SELECT statements). Should be
     * a constant in order to save memory.
     */
    ALL(() -> "*"),

    /**
     * Short-hand for input-based statement parameters. Should be a constant in order to save
     * memory.
     */
    VARIABLE(() -> "?");

    private final Field field;

    /**
     * Default constructor.
     *
     * @since 0.1.0
     */
    Constants(final Field field) {
      this.field = field;
    }

    /**
     * Supplies with the corresponding field's value.
     *
     * @return The defined field's string representation.
     * @since 0.1.0
     */
    @Override
    public final String get() {
      return field.get();
    }
  }
}
