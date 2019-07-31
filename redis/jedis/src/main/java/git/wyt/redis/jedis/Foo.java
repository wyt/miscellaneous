package git.wyt.redis.jedis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

public class Foo {

  JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");

  public static void main(String[] args) {
    Foo foo = new Foo();
    foo.usePool();
  }

  public void basic() {
    Jedis jedis = new Jedis("localhost");
    jedis.set("foo", "bar");
    String value = jedis.get("foo");
    System.out.println(value);
  }

  public void usePool() {
    try (Jedis jedis = pool.getResource()) {
      /// ... do stuff here ... for example
      jedis.set("foo", "bar");
      String foobar = jedis.get("foo");
      jedis.zadd("sose", 0, "car");
      jedis.zadd("sose", 0, "bike");
      Set<String> sose = jedis.zrange("sose", 0, -1);
    }
    /// ... when closing your application:
    pool.close();
  }
}
