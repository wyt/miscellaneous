package config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

@Configuration
public class MybatisConfig {

  String[] mapperLocations;

  private static final ResourcePatternResolver resourceResolver =
      new PathMatchingResourcePatternResolver();

  @Bean
  public SqlSessionFactory sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
    sqlSessionFactoryBean.setDataSource(dataSource());

    sqlSessionFactoryBean.setMapperLocations(resolveMapperLocations());
    return sqlSessionFactoryBean.getObject();
  }

  @Bean
  public DataSource dataSource() {
    return null;
  }

  public Resource[] resolveMapperLocations() {
    return Stream.of(Optional.ofNullable(mapperLocations).orElse(new String[0]))
        .flatMap(location -> Stream.of(getResources(location)))
        .toArray(Resource[]::new);
  }

  private Resource[] getResources(String location) {
    try {
      return resourceResolver.getResources(location);
    } catch (IOException e) {
      return new Resource[0];
    }
  }
}
