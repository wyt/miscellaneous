package git.wyt.support;

/**
 * @author wangyongtao
 * @date 2020/7/23
 */
@SuppressWarnings("all")
public enum AgentBootPropsEnum {

  /** 日志上报地址 */
  HOLMES_SLEUTH_LOGSTASH_URL("holmes.sleuth.logstash.url", true),

  /** Nacos服务地址 */
  HOLMES_NACOS_SERVER_ADDR("holmes.nacos.server_addr", true),

  /** Nacos命名空间 */
  HOLMES_NACOS_NAME_SPACE("holmes.nacos.name_space", true),

  /** Nacos DataID */
  HOLMES_NACOS_DATA_ID("holmes.nacos.data_id", true),

  /** Nacos Group */
  HOLMES_NACOS_GROUP("holmes.nacos.group", true);

  private String key;

  /** 是否允许键值为空 */
  private boolean isAllowBlank;

  private String val;

  AgentBootPropsEnum(String key, boolean isAllowBlank) {
    this.key = key;
    this.isAllowBlank = isAllowBlank;
    this.val = System.getProperty(key);
  }

  public String key() {
    return key;
  }

  public String val() {
    return val;
  }

  public String setAndGetVal(String val) {
    this.val = val;
    return this.val;
  }

  public boolean isAllowBlank() {
    return isAllowBlank;
  }
}
