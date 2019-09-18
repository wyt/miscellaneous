package samples;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

/**
 * 获取classpath下的资源全路径.
 *
 * @author wangyongtao
 * @date 2019/09/17
 */
public class FilePathUtils {

  public static final String CLASS_PATH =
      FilePathUtils.class.getClassLoader().getResource(".").getPath();

  public static String getResourcePath(String resource) {
    String resPath = "";
    try {
      resPath =
          URLDecoder.decode(
              CLASS_PATH + File.separator + resource, Charset.defaultCharset().displayName());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return resPath;
  }
}
