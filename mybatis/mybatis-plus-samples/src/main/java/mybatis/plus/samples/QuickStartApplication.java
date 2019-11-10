package mybatis.plus.samples;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("mybatis.plus.samples.mapper")
public class QuickStartApplication {

  public static void main(String[] args) {
    SpringApplication.run(QuickStartApplication.class, args);
  }
}
