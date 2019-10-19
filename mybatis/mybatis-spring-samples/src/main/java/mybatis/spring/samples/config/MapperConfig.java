package mybatis.spring.samples.config;

import mybatis.spring.samples.dao.DeptMapper;
import mybatis.spring.samples.dao.EmpMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mappers JavaConfig.
 *
 * @author wangyongtao
 * @date 2019/10/19
 */
@Configuration
public class MapperConfig {

  @Bean
  public DeptMapper deptMapper(SqlSessionFactory sqlSessionFactory) {
    SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
    DeptMapper deptMapper = sqlSessionTemplate.getMapper(DeptMapper.class);
    return deptMapper;
  }

  @Bean
  public EmpMapper empMapper(SqlSessionFactory sqlSessionFactory) {
    SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
    EmpMapper empMapper = sqlSessionTemplate.getMapper(EmpMapper.class);
    return empMapper;
  }
}
