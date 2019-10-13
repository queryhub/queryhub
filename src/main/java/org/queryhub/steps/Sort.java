package org.queryhub.steps;

import org.queryhub.KeyWord;
import org.queryhub.field.Field;

/**
 * @author <a href="dhsrocha@gmail.com">Diego Rocha</a>
 */
public interface Sort extends Terminal{

  Sort sort(final Type type, final Aggregate aggregate, final Aggregate... aggregates);

  enum Order implements KeyWord {

    ASC, DESC;

    @Override
    public final String keyWord() {
      return name();
    }
  }

  enum Type implements KeyWord {

    GROUP_BY("GROUP BY"), ORDER_BY("ORDER BY");

    final String symbol;

    Type(final String symbol) {
      this.symbol = symbol;
    }

    @Override
    public final String keyWord() {
      return symbol;
    }
  }

  interface Aggregate extends Field {

    static Aggregate of(final String single, final Order order) {
      return () -> Field.of(single).get() + Compiled.SPACE + order.name();
    }

    static Aggregate of(final String single) {
      return of(single, Order.ASC);
    }
  }
}
