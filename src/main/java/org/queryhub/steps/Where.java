package org.queryhub.steps;

import org.queryhub.KeyWord;
import org.queryhub.field.Field;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
public interface Where extends Terminal {

  Mixin where(final Field.Single field1, final Relation relation, final Field.Single field2);

  // TODO: Composite

  // TODO: LIKE CLAUSE

  interface Mixin extends Terminal {

    Where.Mixin
    where(final Condition condition, final Field.Single field1,
        final Relation relation, final Field.Single field2);
  }

  enum Condition implements KeyWord {

    AND, OR;

    @Override
    public final String keyWord() {
      return name();
    }
  }

  enum Relation implements KeyWord {

    EQ("="), GT(">"), GTE(">="),
    LT("<"), LTE("<="), NEQ("!=");

    final String symbol;

    Relation(final String symbol) {
      this.symbol = symbol;
    }

    @Override
    public final String keyWord() {
      return symbol;
    }
  }
}
