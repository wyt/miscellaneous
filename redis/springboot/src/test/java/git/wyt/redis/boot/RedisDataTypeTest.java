package git.wyt.redis.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

/**
 * @author wangyongtao
 * @date 2021/03/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@SuppressWarnings("all")
public class RedisDataTypeTest {

  @Autowired
  @Qualifier("strRedisTemplate")
  private RedisTemplate<String, String> redisTemplate;

  /** zset示例 */
  @Test
  public void zsetTestCase01() {

    String key = "z.s.t.abc";
    ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
    zSetOperations.add(key, "a", 2.32);
    zSetOperations.add(key, "b", 3.12);
    zSetOperations.add(key, "c", 1.25);
    zSetOperations.add(key, "d", 0.42);
    zSetOperations.add(key, "f", 5.05);

    //    zSetOperations.reverseRangeByScoreWithScores(key, 2.0, 5.0, 2, 3);

    // 1. 先逆序
    // 2. 过滤符合分数区间的元素 min <= result <= max
    // 3. 对2步过滤出的元素从索引offset位置开始取，一共取count个
    Set<ZSetOperations.TypedTuple<String>> zset01 =
        zSetOperations.reverseRangeByScoreWithScores(key, 0, 5, 2, 3);

    zset01.forEach(
        act -> {
          String value = act.getValue();
          Double score = act.getScore();
          System.out.println("val: " + value + ", " + "score: " + score);
        });
  }
}
