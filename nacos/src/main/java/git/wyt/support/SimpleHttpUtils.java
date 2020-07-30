package git.wyt.support;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author wangyongtao
 * @date 2020-7-30
 */
public class SimpleHttpUtils {

  private static final String REQUEST_METHOD_POST = "POST";
  private static final String REQUEST_METHOD_GET = "GET";

  /** 连接超时 */
  private static int CONNECT_TIME_OUT = 5000;

  /** 读取数据超时 */
  private static int READ_TIME_OUT = 50000;

  /** 请求编码 */
  public static String REQUEST_ENCODING = "UTF-8";

  /** 接收编码 */
  public static String RESPONSE_ENCODING = "UTF-8";

  public static final short OK = 200;

  public static final short Bad_Request = 400;

  public static final short Internal_Server_Error = 500;

  public static final short PARAM_ERROR_NO_ANALYSESOR = 1000;

  /**
   * 发送带参数的GET的HTTP请求
   *
   * @param reqUrl HTTP请求URL
   * @param paramMap 参数映射表
   * @return HTTP响应的字符串
   */
  public static String doGet(String reqUrl, Map<String, String> paramMap, String recvEncoding) {
    return doRequest(reqUrl, paramMap, REQUEST_METHOD_GET, recvEncoding);
  }

  /**
   * 发送带参数的POST的HTTP请求
   *
   * @param reqUrl HTTP请求URL
   * @param paramMap 参数映射表
   * @return HTTP响应的字符串
   */
  public static String doPost(String reqUrl, Map<String, String> paramMap, String recvEncoding) {
    return doRequest(reqUrl, paramMap, REQUEST_METHOD_POST, recvEncoding);
  }

  private static String doRequest(
      String reqUrl, Map<String, String> paramMap, String reqMethod, String recvEncoding) {

    return doExecute(reqUrl, paramMap, reqMethod, recvEncoding);
  }

  private static String doExecute(
      String reqUrl, Map<String, String> paramMap, String reqMethod, String recvEncoding) {

    HttpURLConnection urlCon = null;
    String responseContent = null;

    try {
      StringBuilder params = new StringBuilder();
      if (paramMap != null) {
        for (Map.Entry<String, String> element : paramMap.entrySet()) {
          params.append(element.getKey());
          params.append("=");
          if (REQUEST_METHOD_GET.equals(reqMethod)) {
            params.append(URLEncoder.encode(element.getValue(), REQUEST_ENCODING));
          } else {
            params.append(element.getValue());
          }
          params.append("&");
        }

        if (params.length() > 0) {
          params = params.deleteCharAt(params.length() - 1);
        }

        if (params.length() > 0 && (REQUEST_METHOD_GET.equals(reqMethod))) {
          reqUrl = reqUrl + "?" + params.toString();
        }
      }
      URL url = new URL(reqUrl);
      urlCon = (HttpURLConnection) url.openConnection();
      urlCon.setRequestMethod(reqMethod);
      urlCon.setConnectTimeout(CONNECT_TIME_OUT);
      urlCon.setReadTimeout(READ_TIME_OUT);
      urlCon.setDoOutput(true);
      if (REQUEST_METHOD_POST.equals(reqMethod)) {
        byte[] b = params.toString().getBytes();
        urlCon.setRequestProperty(
            "Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        urlCon.setRequestProperty("Content-Length", String.valueOf(b.length));
        urlCon.setRequestProperty("Long-Pulling-Timeout", String.valueOf(30000));
        urlCon.getOutputStream().write(b, 0, b.length);
        urlCon.getOutputStream().flush();
        urlCon.getOutputStream().close();
      }
      responseContent = stream2Str(urlCon.getInputStream(), recvEncoding);
      urlCon.getResponseMessage();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      IoUtils.closeQuietly(urlCon);
    }
    return responseContent;
  }

  /**
   * 流转字符串
   *
   * @param in
   * @param recvEncoding
   * @return
   */
  private static String stream2Str(InputStream in, String recvEncoding) {
    String msg = null;
    try {
      byte[] total = new byte[0];
      byte[] buffer = new byte[1024];
      while (in.read(buffer) > 0) {
        total = addAll(total, buffer);
        buffer = new byte[1024];
      }
      msg = new String(total, Charset.forName(recvEncoding));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        in.close();
      } catch (IOException ignored) {
      }
    }
    return msg;
  }

  /**
   * Adds all the elements of the given arrays into a new array.
   *
   * <p>The new array contains all of the element of {@code array1} followed by all of the elements
   * {@code array2}. When an array is returned, it is always a new array.
   *
   * <pre>
   * ArrayUtils.addAll(array1, null)   = cloned copy of array1
   * ArrayUtils.addAll(null, array2)   = cloned copy of array2
   * ArrayUtils.addAll([], [])         = []
   * </pre>
   *
   * @param array1 the first array whose elements are added to the new array.
   * @param array2 the second array whose elements are added to the new array.
   * @return The new byte[] array.
   * @since 2.1
   */
  public static byte[] addAll(final byte[] array1, final byte... array2) {
    if (array1 == null) {
      return clone(array2);
    } else if (array2 == null) {
      return clone(array1);
    }
    final byte[] joinedArray = new byte[array1.length + array2.length];
    System.arraycopy(array1, 0, joinedArray, 0, array1.length);
    System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
    return joinedArray;
  }

  /**
   * Clones an array returning a typecast result and handling {@code null}.
   *
   * <p>This method returns {@code null} for a {@code null} input array.
   *
   * @param array the array to clone, may be {@code null}
   * @return the cloned array, {@code null} if {@code null} input
   */
  public static byte[] clone(final byte[] array) {
    if (array == null) {
      return null;
    }
    return array.clone();
  }
}
