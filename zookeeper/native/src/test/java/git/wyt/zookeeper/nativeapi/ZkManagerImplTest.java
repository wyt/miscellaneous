package git.wyt.zookeeper.nativeapi;

import org.apache.zookeeper.KeeperException;
import org.junit.jupiter.api.*;

import java.io.UnsupportedEncodingException;

/**
 * @author wangyongtao
 * @date 2019-7-24
 */
@DisplayName("Zookeeper基础API测试用例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ZkManagerImplTest {

  private static final String TEST_PATH = "/foo";
  private static final String TEST_PATH_DATA = "foo";
  ZkManager zkManager = null;

  @BeforeEach
  void init() {
    zkManager = new ZkManagerImpl();
  }

  @Test
  @Order(1)
  void create() throws UnsupportedEncodingException, KeeperException, InterruptedException {
    zkManager.create(TEST_PATH, TEST_PATH_DATA.getBytes("UTF-8"));
  }

  @Test
  @Order(2)
  void getZNodeData() throws InterruptedException, UnsupportedEncodingException, KeeperException {
    String result = (String) zkManager.getZNodeData(TEST_PATH, false);
    Assertions.assertEquals("foo", result);
  }

  @Test
  @Order(3)
  void update() throws KeeperException, InterruptedException, UnsupportedEncodingException {
    zkManager.update(TEST_PATH, "foobar".getBytes("UTF-8"));
    Assertions.assertEquals("foobar", zkManager.getZNodeData(TEST_PATH, false));
  }

  @Test
  @Order(4)
  void delete() throws KeeperException, InterruptedException {
    zkManager.delete(TEST_PATH);
  }

  @AfterEach
  void destroy() {
    zkManager.closeConn();
  }
}
