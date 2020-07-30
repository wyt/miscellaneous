package git.wyt.support;

import java.util.HashMap;
import java.util.Map;

import static git.wyt.support.SimpleHttpUtils.REQUEST_ENCODING;

/**
 * @author wangyongtao
 * @date 2020/7/29
 */
@SuppressWarnings("all")
public class ConfigService {

  public static final String CONFIGS_API = "/nacos/v1/cs/configs";

  public static final String CONFIGS_LISTENER_API = "/nacos/v1/cs/configs/listener";

  public String getConfig() {

    String serverAddr = AgentBootPropsEnum.HOLMES_NACOS_SERVER_ADDR.val();
    String nameSpace = AgentBootPropsEnum.HOLMES_NACOS_NAME_SPACE.val();
    String dataId = AgentBootPropsEnum.HOLMES_NACOS_DATA_ID.val();
    String group = AgentBootPropsEnum.HOLMES_NACOS_GROUP.val();

    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("tenant", nameSpace);
    paramMap.put("dataId", dataId);
    paramMap.put("group", group);

    String config = SimpleHttpUtils.doGet(serverAddr + CONFIGS_API, paramMap, REQUEST_ENCODING);

    return config;
  }

  public String configsListener() {

    String serverAddr = AgentBootPropsEnum.HOLMES_NACOS_SERVER_ADDR.val();
    String nameSpace = AgentBootPropsEnum.HOLMES_NACOS_NAME_SPACE.val();
    String dataId = AgentBootPropsEnum.HOLMES_NACOS_DATA_ID.val();
    String group = AgentBootPropsEnum.HOLMES_NACOS_GROUP.val();
    String contentMD5 = "781969d2af453a6b4d90d2be5c0d0783";

    // Listening-Configs=dataId^2Group^2contentMD5^2tenant^1
    StringBuilder builder = new StringBuilder();
    builder.append(dataId).append("^2");
    builder.append(group).append("^2");
    if (StringUtils.isNotBlank(contentMD5)) {
      builder.append(contentMD5).append("^2");
    }
    if (StringUtils.isNotBlank(nameSpace)) {
      builder.append(nameSpace).append("^2");
    }

    if (builder.length() > 0) {
      builder = builder.delete(builder.length() - 2, builder.length() - 1);
    }
    builder.append("^1");

    Map<String, String> paramMap = new HashMap<>();
    paramMap.put("Listening-Configs", builder.toString());

    String result =
        SimpleHttpUtils.doPost(serverAddr + CONFIGS_LISTENER_API, paramMap, REQUEST_ENCODING);

    return result;
  }

  public void addListener(String dataId, String group, Listener listener) {
    // TODO
  }
}
