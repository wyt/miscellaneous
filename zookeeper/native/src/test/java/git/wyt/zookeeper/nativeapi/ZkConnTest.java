package git.wyt.zookeeper.nativeapi;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * @author wangyongtao
 * @date 2019-7-24
 */
@DisplayName("Zookeeper Connection测试用例")
public class ZkConnTest {

  @Test
  @DisplayName("获取一个Zookeeper链接")
  void connectTest() throws IOException, InterruptedException, KeeperException {
    ZkConn zkConn = new ZkConn();
    ZooKeeper zooKeeper = zkConn.connect("localhost:2181");
    System.out.println(zooKeeper.toString());

    Assertions.assertNotNull(zooKeeper.toString());
  }
}
