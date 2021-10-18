package git.wyt.redis.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyongtao
 * @date 2020/6/22
 */
@Service
public class FoobarService {

  @Autowired private RedisTemplate<Serializable, byte[]> redisTemplate;

  public void listDemo() {
    List<Map<String, Object>> list = new ArrayList<>();
    list.add(genMap(new Object[] {"APP端", 10.23, 8.56, 7.01, 8.23}));
    list.add(genMap(new Object[] {"前 端", 23.52, 0.72, 30.32, 6.7}));
    list.add(genMap(new Object[] {"服务端", 8.09, Double.NaN, 23.01, 8.23}));
    list.add(genMap(new Object[] {"中后台", 1.46, 9.98, 7.03, 82}));

    ValueOperations<Serializable, byte[]> operations = redisTemplate.opsForValue();
    operations.set(
        "holmes.biz.display:reportMap003", ObjectByteUtils.toByteArray(list), 30, TimeUnit.SECONDS);

    byte[] myListByte = operations.get("holmes.biz.display:reportMap003");
    List<Map<String, Object>> l = (List<Map<String, Object>>) ObjectByteUtils.toObject(myListByte);

    l.forEach(
        map -> {
          map.forEach(
              (k, v) -> {
                System.out.print(k + ", " + v + "; ");
              });
          System.out.println();
        });
  }

  private static Map<String, Object> genMap(Object[] args) {

    Map<String, Object> m = new LinkedHashMap<>();

    for (int i = 0; i < args.length; i++) {
      m.put("col_" + i, args[i]);
    }
    return m;
  }
}
