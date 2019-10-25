package org.queryhub;

import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.queryhub.field.Field;
import org.queryhub.field.Single;
import org.queryhub.steps.Insert;
import org.queryhub.steps.Limit;
import org.queryhub.steps.Sort;
import org.queryhub.steps.Terminal;
import org.queryhub.steps.Terminal.Select;
import org.queryhub.steps.Update;
import org.queryhub.steps.Where;

/**
 * Method implementations for the statement building steps.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
final class Impl extends Base<Impl> implements
    Insert, Update, Update.Mixin, Where, Where.Mixin, Sort, Limit, Terminal, Select {

  private static final char EQUAL = '=';
  private static final String COMMA = ",";
  private static final String SPACED_COMMA = ", ";

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  final Impl self() {
    return this;
  }

  // Values

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Terminal values(final Field fields) {
    return this.enclosed(fields.get());
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Terminal values(final Select where) {
    return this.enclosed(where.build(Boolean.FALSE));
  }

  // Where

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Single field, final Field fields) {
    return this.add(Keys.WHERE).add(field).add(Keys.IN).enclosed(fields.get());
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Single f1, final Relation rel, final Single f2) {
    return this.add(Keys.WHERE).add(f1).add(rel).add(f2);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Single reference, final Select clause) {
    return this.add(Keys.WHERE).add(reference).add(Keys.IN).enclosed(clause.build(Boolean.FALSE));
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Condition cond, final Single field, final Field fields) {
    return this.add(cond).add(field).add(Keys.IN).enclosed(fields.get());
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin
  where(final Condition cnd, final Single f1, final Relation rel, final Single f2) {
    return this.add(cnd).add(f1).add(rel).add(f2);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Condition cond, final Single ref, final Select clause) {
    return this.add(cond).add(ref).add(Keys.IN).enclosed(clause.build(Boolean.FALSE));
  }

  // Update

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Update.Mixin set(final Single field, final Single value) {
    return this.add(Keys.SET).add(field).add(String.valueOf(EQUAL)).add(value);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Update.Mixin and(final Single field, final Single value) {
    return this.add(COMMA).add(field).add(String.valueOf(EQUAL)).add(value);
  }

  // Sort

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Sort sort(final Sort.Type type, final Aggregate one, final Aggregate... ones) {
    final var b = Stream.<Aggregate>builder().add(one);
    Stream.of(ones).forEach(b);
    final var s = b.build().map(Supplier::get).collect(Collectors.joining(SPACED_COMMA));
    return this.add(type).add(s);
  }

  // Limit

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Terminal limit(final long skip, final long offset) {
    throwIf(IllegalArgumentException::new, skip < 0 || skip > offset);
    return this.add(Keys.LIMIT).add(skip + SPACED_COMMA + offset);
  }
}
