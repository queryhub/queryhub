package org.queryhub;

import org.queryhub.field.Field;
import org.queryhub.field.Single;
import org.queryhub.steps.Where;
import org.queryhub.steps.Where.After;
import org.queryhub.steps.Where.Mixin;

/**
 * Method implementations for {@code WHERE}-related statement building steps.
 *
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
abstract class WhereBase<I extends WhereBase<I>> extends Base<I> implements Where, After, Mixin {

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  WhereBase(final Keys keyWord) {
    super(keyWord);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Mixin where(final Single field1, final Relation relation, final Single field2) {
    return this.add(Keys.WHERE).add(field1).add(relation).add(field2);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Mixin where(final Single reference, final Select clause) {
    return this.add(Keys.WHERE).add(reference).add(Keys.IN).add(clause);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Where.Mixin where(final Single field, final Field fields) {
    return this.add(Keys.WHERE).add(field).add(Keys.IN).add(Boolean.TRUE, fields);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Mixin and(final Single field1, final Relation relation, final Single field2) {
    return this.add(Keys.AND).add(field1).add(relation).add(field2);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Mixin and(final Single reference, final Select clause) {
    return this.add(Keys.AND).add(reference).add(Keys.IN).add(clause);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Mixin and(final Single field, final Field fields) {
    return this.add(Keys.AND).add(field).add(Keys.IN).add(Boolean.TRUE, fields);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Mixin or(final Single field1, final Relation relation, final Single field2) {
    return this.add(Keys.OR).add(field1).add(relation).add(field2);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Mixin or(final Single reference, final Select clause) {
    return this.add(Keys.OR).add(reference).add(Keys.IN).add(clause);
  }

  /**
   * {@inheritDoc}
   *
   * @since 0.1.0
   */
  @Override
  public final Mixin or(final Single field, final Field fields) {
    return this.add(Keys.OR).add(field).add(Keys.IN).add(Boolean.TRUE, fields);
  }
}
