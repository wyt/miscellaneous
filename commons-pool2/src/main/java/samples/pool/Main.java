package samples.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import samples.FilePathUtils;

import java.io.FileReader;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    //
    ReaderUtil readerUtil = new ReaderUtil(new GenericObjectPool<>(new StringBufferFactory()));

    FileReader reader = new FileReader(FilePathUtils.getResourcePath("苔.txt"));

    String result = readerUtil.readToString(reader);

    System.out.println(result);
  }
}
