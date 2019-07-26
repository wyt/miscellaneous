package git.wyt.zookeeper.nativeapi;

import org.apache.zookeeper.KeeperException;

import java.io.UnsupportedEncodingException;

/**
 * @author wangyongtao
 * @date 2019-7-24
 */
public interface ZkManager {

  void create(String path, byte[] data) throws KeeperException, InterruptedException;

  Object getZNodeData(String path, boolean watchFlag)
      throws KeeperException, InterruptedException, UnsupportedEncodingException;

  void update(String path, byte[] data) throws KeeperException, InterruptedException;

  void delete(String path) throws KeeperException, InterruptedException;

  void closeConn();
}
