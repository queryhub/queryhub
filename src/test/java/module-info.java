/**
 * @author <a href="mailto:queryhub.pub@gmail.com">Diego Rocha</a>
 * @since 0.1.0
 */
module queryhub.test {

  requires queryhub.core;

  requires java.net.http;
  requires java.sql;

  requires h2;

  requires org.junit.jupiter.api;

  opens org.queryhub.test to org.junit.jupiter.api;
}