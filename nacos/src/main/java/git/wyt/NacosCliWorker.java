package git.wyt;

import git.wyt.support.AgentBootPropsEnum;
import git.wyt.support.ConfigService;
import git.wyt.support.MD5Utils;

/**
 * @author wangyongtao
 * @date 2020/7/30
 */
public class NacosCliWorker {

  public static ConfigService configService = new ConfigService();

  public static void main(String[] args) {
    init();
    // configs();
    listen();
  }

  public static void configs() {
    String config = configService.getConfig();
    String md5 = MD5Utils.md5Hex(config, "UTF-8");
    // 781969d2af453a6b4d90d2be5c0d0783
    System.out.println("md5: " + md5);
    System.out.println(config);
  }

  public static void listen() {
    String result = configService.configsListener();

    System.out.println(result);
  }

  public static void init() {
    System.setProperty(
        AgentBootPropsEnum.HOLMES_NACOS_SERVER_ADDR.key(),
        AgentBootPropsEnum.HOLMES_NACOS_SERVER_ADDR.setAndGetVal("http://192.168.200.63:8848"));

    System.setProperty(
        AgentBootPropsEnum.HOLMES_NACOS_NAME_SPACE.key(),
        AgentBootPropsEnum.HOLMES_NACOS_NAME_SPACE.setAndGetVal(
            "94a413cd-1709-4373-8dc5-312c04986b0e"));

    System.setProperty(
        AgentBootPropsEnum.HOLMES_NACOS_DATA_ID.key(),
        AgentBootPropsEnum.HOLMES_NACOS_DATA_ID.setAndGetVal(
            "mapi-app-carmodel-sleuth.properties"));

    System.setProperty(
        AgentBootPropsEnum.HOLMES_NACOS_GROUP.key(),
        AgentBootPropsEnum.HOLMES_NACOS_GROUP.setAndGetVal("DEFAULT_GROUP"));
  }
}
