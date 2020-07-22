package git.wyt;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyongtao
 * @date 2020/7/21
 */
public class NacosApi {

  public static void main(String[] args) throws NacosException, IOException, InterruptedException {

    String serverAddr = "http://127.0.0.1:8848";
    String nameSpace = "17b74835-9c78-404f-baae-3340a32e6a23";
    String dataId = "mapi-app-carmodel-sleuth.properties";
    String group = "MAPI_APP_GROUP";

    Properties properties = new Properties();

    properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
    properties.put(PropertyKeyConst.NAMESPACE, nameSpace);

    ConfigService configService = NacosFactory.createConfigService(properties);

    configService.addListener(
        dataId,
        group,
        new Listener() {
          @Override
          public Executor getExecutor() {
            return null;
          }

          @Override
          public void receiveConfigInfo(String configInfo) {
            System.out.println("configInfo: " + configInfo);
          }
        });

    String content = configService.getConfig(dataId, group, 5000);
    System.out.println("first time receive: " + content);

    TimeUnit.SECONDS.sleep(100);
  }
}
