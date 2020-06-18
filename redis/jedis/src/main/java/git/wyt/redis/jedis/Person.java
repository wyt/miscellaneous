package git.wyt.redis.jedis;

import java.io.Serializable;

public class Person implements Serializable {

  private static final long serialVersionUID = 1L;

  public String userName;

  public String password;

  public int age;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}
