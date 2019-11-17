package org.queryhub;

import org.queryhub.field.Field;
import org.queryhub.field.Single;
import org.queryhub.helper.Helper;
import org.queryhub.steps.Insert;
import org.queryhub.steps.Limit;
import org.queryhub.steps.Sort;
import org.queryhub.steps.Terminal;
import org.queryhub.steps.Terminal.Select;
import org.queryhub.steps.Update;
import org.queryhub.steps.Update.After;
import org.queryhub.steps.Update.Mixin;

/**
 * Method implementations for the statement building steps.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
final class Impl<I extends Impl<I>> extends WhereBase<Impl<I>> implements
    Insert, Update, After, Mixin, Sort, Limit, Terminal, Select {

  private static final Field COMMA = () -> ",";
  private static final Field EQUAL = () -> "=";

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  Impl(final Keys keyWord) {
    super(keyWord);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  final Impl<I> self() {
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
    return this.enclose(fields, Field::get);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Terminal values(final Select clause) {
    return this.enclose(clause, s -> s.build(Boolean.FALSE));
  }

  // Update

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Update.After set(final Single field, final Single value) {
    return this.add(Keys.SET).add(field).add(EQUAL).add(value);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Update.Mixin and(final Single field, final Single value) {
    return this.add(COMMA).add(field).add(EQUAL).add(value);
  }

  // Sort

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Sort sort(final Sort.Type type, final Aggregate one, final Aggregate... ones) {
    return this.add(type).add(one, ones);
  }

  // Limit

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Terminal limit(final long s, final long o) {
    Helper.throwIf(IllegalArgumentException::new, s < 0 || s > o);
    return this.add(Keys.LIMIT).add(Helper.asField(s)).add(COMMA).add(Helper.asField(o));
  }
}
