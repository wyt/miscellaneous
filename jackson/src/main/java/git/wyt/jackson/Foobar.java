package git.wyt.jackson;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;

/**
 * @author wangyongtao
 * @date 2020/8/7
 */
public class Foobar {

  static ObjectMapper mapper = new ObjectMapper();

  static {
    // SerializationFeature for changing how JSON is written

    // to enable standard indentation ("pretty-printing"):
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    // to allow serialization of "empty" POJOs (no properties to serialize)
    // (without this setting, an exception is thrown in those cases)
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    // to write java.util.Date, Calendar as number (timestamp):
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    // DeserializationFeature for changing how JSON is read as POJOs:

    // to prevent exception when encountering unknown property:
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    // to allow coercion of JSON empty String ("") to null Object value:
    mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

    // JsonParser.Feature for configuring parsing settings:

    // to allow C/C++ style comments in JSON (non-standard, disabled by default)
    // (note: with Jackson 2.5, there is also `mapper.enable(feature)` / `mapper.disable(feature)`)
    mapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
    // to allow (non-standard) unquoted field names in JSON:
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    // to allow use of apostrophes (single quotes), non standard
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    // JsonGenerator.Feature for configuring low-level JSON generation:

    // to force escaping of non-ASCII characters:
    mapper.configure(JsonGenerator.Feature.ESCAPE_NON_ASCII, true);
  }

  static final String TEST_CASE_STR01 = "{\"name\":\"Bob\",\"age\":13}";

  public static void main(String[] args) throws IOException {
    case03();
  }

  public void case01() throws JsonProcessingException {
    MyValue value = mapper.readValue(TEST_CASE_STR01, MyValue.class);

    System.out.println(value.name + "=" + value.age);

    String jsonString = mapper.writeValueAsString(value);

    System.out.println(jsonString);
  }

  public static void case03() throws IOException {

    JsonFactory f = mapper.getFactory(); // may alternatively construct directly too

    // First: write simple JSON output
    //    String path = this.getClass().getResource("/pic/body.png").getpath();

    File jsonFile = new File(Foobar.class.getResource("/").getFile(), "test_case_demo.json");
    JsonGenerator g = f.createGenerator(jsonFile, JsonEncoding.UTF8);
    // write JSON: { "message" : "Hello world!" }
    g.writeStartObject();
    g.writeStringField("message", "Hello world!");
    g.writeEndObject();
    g.close();

    // Second: read file back
    JsonParser p = f.createParser(jsonFile);

    JsonToken t = p.nextToken(); // Should be JsonToken.START_OBJECT
    t = p.nextToken(); // JsonToken.FIELD_NAME
    if ((t != JsonToken.FIELD_NAME) || !"message".equals(p.getCurrentName())) {
      // handle error
    }
    t = p.nextToken();
    if (t != JsonToken.VALUE_STRING) {
      // similarly
    }
    String msg = p.getText();
    System.out.printf("My message to you is: %s!\n", msg);
    p.close();
  }
}
