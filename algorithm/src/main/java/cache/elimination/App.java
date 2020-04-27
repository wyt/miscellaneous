package cache.elimination;

public class App {

  public static void main(String[] args) {
    testLFUCache();
    testLRUCache();
  }

  private static void testLRUCache() {
    LRUCache<Integer, Integer> cache = new LRUCache<>(3);

    cache.put(1, 1);
    cache.put(2, 2);
    cache.put(3, 3);

    System.out.println(cache.toString());

    cache.get(1);

    System.out.println(cache.toString());

    cache.put(4, 4);

    System.out.println(cache.toString());

    cache.get(3);

    System.out.println(cache.toString());

    cache.put(1, 11);

    System.out.println(cache.toString());
  }

  private static void testLFUCache() {
    // LFUCache<String,String> cache = new LFUCache<>();
    // LFUCache cache = new LFUCache(120);
    LFUCache cache = new LFUCache(120, 2 * 60);

    for (int i = 0; i < 10; i++) {
      cache.put("0" + i, "mayi" + i, 10);
    }

    System.out.println(cache.get("00"));
    System.out.println(cache.get("01"));
    System.out.println(cache.get("00"));

    cache.put("011", "lisi", 130);

    for (int i = 0; i < 12; i++) {
      System.out.println(cache.get("0" + i));
    }
  }
}
