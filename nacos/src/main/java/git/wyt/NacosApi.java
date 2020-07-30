package git.wyt;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.NacosConfigService;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author wangyongtao
 * @date 2020/7/21
 */
public class NacosApi {

  // -javaagent:E:\idea\projects\yiche\holmes-sniffer\holmes-agent\target\holmes-agent-0.0.1.jar
  // -Dholmes.sleuth.logstash.url=172.17.28.35:4570
  // -Dholmes.nacos.server_addr=http://192.168.200.63:8848
  // -Dholmes.nacos.name_space=94a413cd-1709-4373-8dc5-312c04986b0e
  // -Dholmes.nacos.data_id=mapi-app-carmodel-sleuth.properties
  // -Dholmes.nacos.group=DEFAULT_GROUP

  public static void main(String[] args) throws NacosException, IOException, InterruptedException {

    String serverAddr = "http://192.168.200.63:8848";
    String nameSpace = "94a413cd-1709-4373-8dc5-312c04986b0e";
    String dataId = "mapi-app-carmodel-sleuth.properties";
    String group = "DEFAULT_GROUP";

    //    String serverAddr = "http://127.0.0.1:8848";
    //    String nameSpace = "17b74835-9c78-404f-baae-3340a32e6a23";
    //    String dataId = "mapi-app-carmodel-sleuth.properties";
    //    String group = "DEFAULT_GROUP";

    //    String serverAddr = "http://127.0.0.1:8848";
    //    String nameSpace = "";
    //    String dataId = "mapi-app-carmodel-sleuth.properties";
    //    String group = "MAPI_APP_GROUP";

    Properties properties = new Properties();

    properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
    if (nameSpace != null) {
      properties.put(PropertyKeyConst.NAMESPACE, nameSpace);
      System.out.println("name space: " + nameSpace);
    }

    NacosConfigService configService =
        (NacosConfigService) NacosFactory.createConfigService(properties);

    System.out.println("server status: " + configService.getServerStatus());

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
