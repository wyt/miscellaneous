package cache.elimination;

import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * LFU Cache
 *
 * @param <K>
 * @param <V>
 */
public class LFUCache<K, V> {

  /*
   * 缓存容器
   */
  private ConcurrentHashMap<Object, Cache> concurrentHashMap;

  /*
   * 默认缓存容器的容量
   */
  private int MAX_CACHE_SIZE = 66;

  public LFUCache() {
    this.concurrentHashMap = new ConcurrentHashMap<>(MAX_CACHE_SIZE);
    // 定时任务 清空过期缓存
    new Thread(new ExpireThread()).start();
  }

  /**
   * @title
   * @description
   * @param capacity 缓存容器容量大小
   */
  public LFUCache(int capacity) {
    this.MAX_CACHE_SIZE = capacity;
    this.concurrentHashMap = new ConcurrentHashMap<>(capacity);
    // 定时任务 清空过期缓存
    new Thread(new ExpireThread()).start();
  }

  /**
   * @title
   * @description
   * @param capacity 缓存容器容量大小
   * @param expireTime 定时清除过期缓存时间（秒）
   */
  public LFUCache(int capacity, long expireTime) {
    this.MAX_CACHE_SIZE = capacity;
    this.concurrentHashMap = new ConcurrentHashMap<>(capacity);
    // 定时任务 清空过期缓存
    new Thread(new ExpireThread(expireTime)).start();
  }

  /**
   * @title get
   * @description 获取缓存
   * @param key 缓存键
   * @return
   */
  public Object get(K key) {
    if (concurrentHashMap.isEmpty()) {
      return null;
    }
    Cache cache = concurrentHashMap.get(key);
    if (cache == null) {
      return null;
    }

    cache.setHitCount(cache.getHitCount() + 1); // 击中次数+1
    cache.setAccessTime(System.nanoTime()); // 更新最后一次访问时间

    return cache.getValue();
  }

  /**
   * @title put
   * @description 添加缓存
   * @param key 键
   * @param value 值
   * @param expire 存活时间
   */
  public void put(K key, V value, long expire) {
    // 存在缓存时，则更新缓存
    if (concurrentHashMap.contains(key)) {
      LFUCache<K, V>.Cache cache = concurrentHashMap.get(key);
      cache.setHitCount(cache.getHitCount() + 1);
      cache.setCreateTime(System.nanoTime());
      cache.setAccessTime(System.nanoTime());
      cache.setExpireTime(expire);
      cache.setValue(value);
      return;
    }

    // 是否达到最大缓存容量 若达到最大容量，清除使用次数最少的缓存
    if (isFull()) {
      Object kickedKey = getKickedKey(); // 获取使用次数最少的缓存键
      if (kickedKey != null) {
        concurrentHashMap.remove(kickedKey);
      }
    }

    Cache cache = new Cache();
    cache.setKey(key);
    cache.setValue(value);
    cache.setCreateTime(System.nanoTime());
    cache.setAccessTime(System.nanoTime());
    cache.setHitCount(1);
    cache.setExpireTime(expire);

    concurrentHashMap.put(key, cache);
  }

  /**
   * @title getKickedKey
   * @description 获取使用次数最少的缓存键
   * @return
   */
  private Object getKickedKey() {
    Cache cache = Collections.min(concurrentHashMap.values());
    return cache.getKey();
  }

  /**
   * @title isFull
   * @description 缓存是否达到最大容量
   * @return
   */
  private boolean isFull() {
    return concurrentHashMap.size() == MAX_CACHE_SIZE;
  }

  /**
   * @title ExpireThread
   * @description 处理过期的缓存 线程
   * @author yacong_liu Email:2682505646@qq.com
   * @date 2019年12月26日
   */
  class ExpireThread implements Runnable {
    /*
     * 定时清除过期缓存时间（秒）
     */
    private long EXPIRE_TIME = 5 * 60;

    public ExpireThread() {}

    public ExpireThread(long expireTime) {
      EXPIRE_TIME = expireTime;
    }

    @Override
    public void run() {
      // 按照设定时间 定时清除过期缓存
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(EXPIRE_TIME);
          System.out.println(
              String.format("开始进行过期缓存的检测清除工作！定时时间: %s（s）", String.valueOf(EXPIRE_TIME)));

          expireCache();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }

    /**
     * @title expireCache
     * @description 清楚过期缓存
     */
    private void expireCache() {
      for (Object key : concurrentHashMap.keySet()) {
        Cache cache = concurrentHashMap.get(key);
        long timoutTime = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime() - cache.getCreateTime());
        if (cache.getExpireTime() > timoutTime) {
          continue;
        }
        System.out.println("******清除过期缓存 ： " + key);
        concurrentHashMap.remove(key);
      }
    }
  }

  /**
   * @title Cache
   * @description 缓存类
   * @author yacong_liu Email:2682505646@qq.com
   * @date 2019年12月26日
   */
  class Cache implements Comparable<Cache> {

    // 键
    private Object key;

    // 缓存值
    private Object value;

    // 最后一次访问时间
    private long accessTime;

    // 创建时间
    private long createTime;

    // 存活时间
    private long expireTime;

    // 命中次数
    private Integer hitCount;

    public Object getValue() {
      return value;
    }

    public void setValue(Object value) {
      this.value = value;
    }

    public long getAccessTime() {
      return accessTime;
    }

    public void setAccessTime(long accessTime) {
      this.accessTime = accessTime;
    }

    public long getCreateTime() {
      return createTime;
    }

    public void setCreateTime(long createTime) {
      this.createTime = createTime;
    }

    public Integer getHitCount() {
      return hitCount;
    }

    public void setHitCount(Integer hitCount) {
      this.hitCount = hitCount;
    }

    public Object getKey() {
      return key;
    }

    public void setKey(Object key) {
      this.key = key;
    }

    public long getExpireTime() {
      return expireTime;
    }

    public void setExpireTime(long expireTime) {
      this.expireTime = expireTime;
    }

    @Override
    public int compareTo(Cache o) {
      return hitCount.compareTo(o.hitCount);
    }
  }
}
