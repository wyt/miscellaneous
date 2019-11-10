package mybatis.plus.samples;

import mybatis.plus.samples.entity.User;
import mybatis.plus.samples.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author wangyongtao
 * @date 2019/11/10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class QuickStartApplicationTests {

  @Autowired private UserMapper userMapper;

  @Test
  public void testSelect() {
    System.out.println(("----- selectAll method test ------"));
    List<User> userList = userMapper.selectList(null);
    Assert.assertEquals(5, userList.size());
    userList.forEach(System.out::println);
  }
}
