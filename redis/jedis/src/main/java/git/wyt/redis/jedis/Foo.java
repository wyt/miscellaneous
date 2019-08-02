package git.wyt.redis.jedis;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

public class Foo {

  private static final String REDIS_LOCAL_HOST = "localhost";
  private static final int REDIS_LOCAL_PORT = 6379;

  JedisPool pool = new JedisPool(new JedisPoolConfig(), REDIS_LOCAL_HOST, REDIS_LOCAL_PORT);

  public static void main(String[] args) {
    Foo foo = new Foo();
    foo.useCluster();
  }

  /** 直接使用单节点Redis */
  public void basic() {
    Jedis jedis = new Jedis(REDIS_LOCAL_HOST, REDIS_LOCAL_PORT);
    jedis.set("foo", "bar");
    String value = jedis.get("foo");
    System.out.println(value);
  }

  /** 对单节点Redis应用连接池 */
  public void usePool() {
    try (Jedis jedis = pool.getResource()) {
      jedis.set("foo", "bar001");
      String value = jedis.get("foo");
      System.out.println(value);
    }
    pool.close();
  }

  /** 使用Redis集群 */
  public void useCluster() {
    Set<HostAndPort> jedisClusterNodes = new HashSet<>();
    // Jedis Cluster will attempt to discover cluster nodes automatically
    jedisClusterNodes.add(new HostAndPort("192.168.91.146", 7000));
    jedisClusterNodes.add(new HostAndPort("192.168.91.146", 7001));
    jedisClusterNodes.add(new HostAndPort("192.168.91.146", 7002));
    jedisClusterNodes.add(new HostAndPort("192.168.91.146", 7003));
    jedisClusterNodes.add(new HostAndPort("192.168.91.146", 7004));
    jedisClusterNodes.add(new HostAndPort("192.168.91.146", 7005));

    JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes);
    jedisCluster.set("foo", "bar002");
    String value = jedisCluster.get("foo");
    System.out.println(value);
  }
}
