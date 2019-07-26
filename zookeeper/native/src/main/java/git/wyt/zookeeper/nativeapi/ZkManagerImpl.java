package git.wyt.zookeeper.nativeapi;

import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @author wangyongtao
 * @date 2019-7-24
 */
@Slf4j
public class ZkManagerImpl implements ZkManager {

  private static ZooKeeper zooKeeper;

  private static ZkConn zkConn;

  public ZkManagerImpl() {
    try {
      init();
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
    }
  }

  private void init() throws IOException, InterruptedException {
    zkConn = new ZkConn();
    zooKeeper = zkConn.connect();
  }

  public void closeConn() {
    try {
      zkConn.close();
    } catch (InterruptedException e) {
      log.error(e.getMessage(), e);
    }
  }

  @Override
  public void create(String path, byte[] data) throws KeeperException, InterruptedException {
    zooKeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
  }

  @Override
  public Object getZNodeData(String path, boolean watchFlag)
      throws KeeperException, InterruptedException, UnsupportedEncodingException {

    byte[] b = null;
    b = zooKeeper.getData(path, watchFlag, null);
    return new String(b, "UTF-8");
  }

  @Override
  public void update(String path, byte[] data) throws KeeperException, InterruptedException {
    int version = zooKeeper.exists(path, true).getVersion();
    zooKeeper.setData(path, data, version);
  }

  @Override
  public void delete(String path) throws KeeperException, InterruptedException {
    int version = zooKeeper.exists(path, true).getVersion();
    zooKeeper.delete(path, version);
  }
}
