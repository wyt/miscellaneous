package samples.npool;

import java.io.IOException;
import java.io.Reader;

public class ReaderUtil {

  /**
   * Dumps the contents of the {@link Reader} to a String, closing the {@link Reader} when done.
   *
   * @param in
   * @return
   * @throws IOException
   */
  public String readToString(Reader in) throws IOException {
    StringBuffer buf = new StringBuffer();
    try {
      for (int c = in.read(); c != -1; c = in.read()) {
        buf.append((char) c);
      }
      return buf.toString();
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        in.close();
      } catch (Exception e) {
        // ignored
      }
    }
  }
}
