package samples.pool;

import org.apache.commons.pool2.ObjectPool;

import java.io.IOException;
import java.io.Reader;

public class ReaderUtil {

  /** 声明对象池，池中元素类型未StringBuffer */
  private ObjectPool<StringBuffer> pool;

  public ReaderUtil(ObjectPool<StringBuffer> pool) {
    this.pool = pool;
  }

  /** Dumps the contents of the {@link Reader} to a String, closing the {@link Reader} when done. */
  public String readToString(Reader in) throws IOException {
    StringBuffer buf = null;
    try {
      // 获取池化实例
      buf = pool.borrowObject();
      for (int c = in.read(); c != -1; c = in.read()) {
        buf.append((char) c);
      }
      return buf.toString();
    } catch (IOException e) {
      throw e;
    } catch (Exception e) {
      throw new RuntimeException("Unable to borrow buffer from pool" + e.toString());
    } finally {
      try {
        in.close();
      } catch (Exception e) {
        // ignored
      }
      try {
        if (null != buf) {
          // 返还池化对象
          pool.returnObject(buf);
        }
      } catch (Exception e) {
        // ignored
      }
    }
  }
}
