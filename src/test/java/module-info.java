/**
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
module test.module {

  requires prod.module;

  requires org.junit.jupiter.api;

  opens org.queryhub.test to org.junit.jupiter.api;
}