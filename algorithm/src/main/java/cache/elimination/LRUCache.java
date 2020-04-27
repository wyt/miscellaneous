package cache.elimination;

import java.util.LinkedHashMap;

/**
 * LRU Cache
 *
 * @param <K>
 * @param <V>
 */
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

  private int MAX_CACHE_SIZE;

  public LRUCache(int capacity) {
    // 扩容因子 0.75 +1 避免达到阈值时立刻扩容 accessOrder要设置为true，按访问排序
    super((int) (Math.ceil(capacity / 0.75) + 1), 0.75f, true);
    MAX_CACHE_SIZE = capacity;
  }

  @Override
  protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
    // 超过阈值返回true,执行LRU淘汰
    return size() > MAX_CACHE_SIZE;
  }

  @Override
  public V get(Object key) {
    return super.get(key);
  }

  @Override
  public V put(K key, V value) {
    return super.put(key, value);
  }
}
