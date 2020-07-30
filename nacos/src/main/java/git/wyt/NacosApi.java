package git.wyt;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyongtao
 * @date 2020/7/21
 */
public class NacosApi {

  public static void main(String[] args) throws NacosException, InterruptedException {

    String serverAddr = "http://192.168.200.63:8848";
    String nameSpace = "94a413cd-1709-4373-8dc5-312c04986b0e";
    String dataId = "mapi-app-carmodel-sleuth.properties";
    String group = "DEFAULT_GROUP";

    Properties properties = new Properties();

    properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
    if (nameSpace != null) {
      properties.put(PropertyKeyConst.NAMESPACE, nameSpace);
    }

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
            System.out.println("配置变更:");
            System.out.println(configInfo);
          }
        });

    String content = configService.getConfig(dataId, group, 5000);
    System.out.println("获取配置:");
    System.out.println(content);

    TimeUnit.SECONDS.sleep(60 * 10);
  }
}
