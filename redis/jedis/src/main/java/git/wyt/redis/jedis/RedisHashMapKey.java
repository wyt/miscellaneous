package git.wyt.redis.jedis;

import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class RedisHashMapKey {

  public static void main(String[] args) throws IOException, ClassNotFoundException {

    Jedis redis = new Jedis("localhost", 6379);

    Person p1 = new Person();
    p1.setUserName("user1");
    p1.setPassword("password1");
    p1.setAge(10);

    Person p2 = new Person();
    p2.setUserName("user2");
    p2.setPassword("password2");
    p2.setAge(12);

    Person p3 = new Person();
    p3.setUserName("user3");
    p3.setPassword("password3");
    p3.setAge(13);

    Map<String, Person> map = new HashMap<>();
    map.put("p1", p1);
    map.put("p2", p2);
    map.put("p3", p3);

    // 转成输出字节流
    ByteArrayOutputStream bai = new ByteArrayOutputStream();
    ObjectOutputStream obi = new ObjectOutputStream(bai);
    obi.writeObject(map);
    byte[] byt = bai.toByteArray();

    // 将map存入redis中
    redis.set("p".getBytes(), byt);

    // 获取map
    byte[] personByte = redis.get("p".getBytes());
    ObjectInputStream oii = null;
    ByteArrayInputStream bis = null;

    // 转换成输入字节流
    bis = new ByteArrayInputStream(personByte);
    oii = new ObjectInputStream(bis);
    Map<String, Person> result = (Map<String, Person>) oii.readObject();
    for (Entry<String, Person> entry : result.entrySet()) {
      System.out.println("key= " + entry.getKey());
      Person obj = (Person) entry.getValue();
      System.out.println("getUserName: " + obj.getUserName());
      System.out.println("getPassword= " + obj.getPassword());
      System.out.println("getAge= " + obj.getAge());
    }
  }
}
