package git.wyt.zookeeper.nativeapi;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @author wangyongtao
 * @date 2019-7-24
 */
public class ZkConn {

  private static final String ZOO_HOST = "localhost:2181";

  private ZooKeeper zooKeeper;
  CountDownLatch zkConnLatch = new CountDownLatch(1);

  public ZooKeeper connect() throws IOException, InterruptedException {
    zooKeeper =
        new ZooKeeper(
            ZOO_HOST,
            2000,
            new Watcher() {
              @Override
              public void process(WatchedEvent event) {
                if (event.getState() == Event.KeeperState.SyncConnected) {
                  zkConnLatch.countDown();
                }
              }
            });

    zkConnLatch.await();
    return zooKeeper;
  }

  public void close() throws InterruptedException {
    zooKeeper.close();
  }
}
