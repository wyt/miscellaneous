package git.wyt.nacos;

import git.wyt.nacos.common.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Http tool.
 *
 * @author Nacos
 */
public class HttpSimpleClient {

  /**
   * Get method.
   *
   * @param url url
   * @param headers headers
   * @param paramValues paramValues
   * @param encoding encoding
   * @param readTimeoutMs readTimeoutMs
   * @param isSsl isSsl
   * @return result
   * @throws IOException io exception
   */
  public static HttpResult httpGet(
      String url,
      List<String> headers,
      List<String> paramValues,
      String encoding,
      long readTimeoutMs,
      boolean isSsl)
      throws IOException {
    String encodedContent = encodingParams(paramValues, encoding);
    url += (null == encodedContent) ? "" : ("?" + encodedContent);

    HttpURLConnection conn = null;

    try {
      conn = (HttpURLConnection) new URL(url).openConnection();
      conn.setRequestMethod("GET");
      conn.setConnectTimeout(100);
      conn.setReadTimeout((int) readTimeoutMs);
      List<String> newHeaders = getHeaders(url, headers, paramValues);
      setHeaders(conn, newHeaders, encoding);

      conn.connect();

      int respCode = conn.getResponseCode();
      String resp = null;

      if (HttpURLConnection.HTTP_OK == respCode) {
        resp = IoUtils.toString(conn.getInputStream(), encoding);
      } else {
        resp = IoUtils.toString(conn.getErrorStream(), encoding);
      }
      return new HttpResult(respCode, conn.getHeaderFields(), resp);
    } finally {
      IoUtils.closeQuietly(conn);
    }
  }

  /** 发送GET请求. */
  public static HttpResult httpGet(
      String url,
      List<String> headers,
      List<String> paramValues,
      String encoding,
      long readTimeoutMs)
      throws IOException {
    return httpGet(url, headers, paramValues, encoding, readTimeoutMs, false);
  }

  /**
   * 发送POST请求.
   *
   * @param url url
   * @param headers 请求Header，可以为null
   * @param paramValues 参数，可以为null
   * @param encoding URL编码使用的字符集
   * @param readTimeoutMs 响应超时
   * @param isSsl 是否https
   * @return result
   * @throws IOException io exception
   */
  public static HttpResult httpPost(
      String url,
      List<String> headers,
      List<String> paramValues,
      String encoding,
      long readTimeoutMs,
      boolean isSsl)
      throws IOException {
    String encodedContent = encodingParams(paramValues, encoding);
    encodedContent = (null == encodedContent) ? "" : encodedContent;
    HttpURLConnection conn = null;
    try {
      conn = (HttpURLConnection) new URL(url).openConnection();
      conn.setRequestMethod("POST");
      conn.setConnectTimeout(3000);
      conn.setReadTimeout((int) readTimeoutMs);
      conn.setDoOutput(true);
      conn.setDoInput(true);
      List<String> newHeaders = getHeaders(url, headers, paramValues);
      setHeaders(conn, newHeaders, encoding);

      conn.getOutputStream().write(encodedContent.getBytes(encoding));

      int respCode = conn.getResponseCode();
      String resp = null;

      if (HttpURLConnection.HTTP_OK == respCode) {
        resp = IoUtils.toString(conn.getInputStream(), encoding);
      } else {
        resp = IoUtils.toString(conn.getErrorStream(), encoding);
      }
      return new HttpResult(respCode, conn.getHeaderFields(), resp);
    } finally {
      IoUtils.closeQuietly(conn);
    }
  }

  /**
   * 发送POST请求.
   *
   * @param url url
   * @param headers 请求Header，可以为null
   * @param paramValues 参数，可以为null
   * @param encoding URL编码使用的字符集
   * @param readTimeoutMs 响应超时
   * @return result
   * @throws IOException io exception
   */
  public static HttpResult httpPost(
      String url,
      List<String> headers,
      List<String> paramValues,
      String encoding,
      long readTimeoutMs)
      throws IOException {
    return httpPost(url, headers, paramValues, encoding, readTimeoutMs, false);
  }

  private static void setHeaders(HttpURLConnection conn, List<String> headers, String encoding) {
    if (null != headers) {
      for (Iterator<String> iter = headers.iterator(); iter.hasNext(); ) {
        conn.addRequestProperty(iter.next(), iter.next());
      }
    }
    conn.addRequestProperty(HttpHeaderConsts.CLIENT_VERSION_HEADER, VersionUtils.version);
    conn.addRequestProperty(
        "Content-Type", "application/x-www-form-urlencoded;charset=" + encoding);

    String ts = String.valueOf(System.currentTimeMillis());
    String token =
        MD5Utils.md5Hex(ts + System.getProperty("nacos.client.appKey", ""), Constants.ENCODE);

    conn.addRequestProperty(Constants.CLIENT_APPNAME_HEADER, "unknown");
    conn.addRequestProperty(Constants.CLIENT_REQUEST_TS_HEADER, ts);
    conn.addRequestProperty(Constants.CLIENT_REQUEST_TOKEN_HEADER, token);
  }

  private static List<String> getHeaders(String url, List<String> headers, List<String> paramValues)
      throws IOException {
    List<String> newHeaders = new ArrayList<String>();
    newHeaders.add("exConfigInfo");
    newHeaders.add("true");
    newHeaders.add("RequestId");
    newHeaders.add(UuidUtils.generateUuid());
    if (headers != null) {
      newHeaders.addAll(headers);
    }
    return newHeaders;
  }

  private static String encodingParams(List<String> paramValues, String encoding)
      throws UnsupportedEncodingException {
    StringBuilder sb = new StringBuilder();
    if (null == paramValues) {
      return null;
    }

    for (Iterator<String> iter = paramValues.iterator(); iter.hasNext(); ) {
      sb.append(iter.next()).append("=");
      sb.append(URLEncoder.encode(iter.next(), encoding));
      if (iter.hasNext()) {
        sb.append("&");
      }
    }
    return sb.toString();
  }

  public static class HttpResult {

    public final int code;

    public final Map<String, List<String>> headers;

    public final String content;

    public HttpResult(int code, String content) {
      this.code = code;
      this.headers = null;
      this.content = content;
    }

    public HttpResult(int code, Map<String, List<String>> headers, String content) {
      this.code = code;
      this.headers = headers;
      this.content = content;
    }

    @Override
    public String toString() {
      return "HttpResult{"
          + "code="
          + code
          + ", headers="
          + headers
          + ", content='"
          + content
          + '\''
          + '}';
    }
  }
}
