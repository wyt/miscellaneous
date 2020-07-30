package git.wyt.nacos.common;

import java.util.UUID;

/**
 * UUID utils.
 *
 * @author nkorange
 */
public class UuidUtils {

  public static String generateUuid() {
    return UUID.randomUUID().toString();
  }
}
