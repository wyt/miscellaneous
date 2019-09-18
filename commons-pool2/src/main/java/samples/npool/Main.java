package samples.npool;

import samples.FilePathUtils;

import java.io.FileReader;
import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {

    FileReader reader = new FileReader(FilePathUtils.getResourcePath("苔.txt"));

    String result = new ReaderUtil().readToString(reader);

    System.out.println(result);
  }
}
