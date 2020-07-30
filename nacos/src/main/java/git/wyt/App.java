package git.wyt;

import okhttp3.*;

import java.io.IOException;

/** Hello world! */
public class App {

  public static void main(String[] args) {
    System.out.println("Hello World!");
  }

  public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

  OkHttpClient client = new OkHttpClient();

  String post(String url, String json) throws IOException {
    RequestBody body = RequestBody.create(json, JSON);
    Request request = new Request.Builder().url(url).post(body).build();
    try (Response response = client.newCall(request).execute()) {
      return response.body().string();
    }
  }
}
