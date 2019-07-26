package git.wyt.mybatis.nativeapi.sample.dao;

import git.wyt.mybatis.nativeapi.sample.domain.Emp;
import git.wyt.mybatis.nativeapi.sample.util.MyBaitsUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
@DisplayName("EmpMapper测试用例")
class EmpMapperTest {

  SqlSession session = null;

  @BeforeEach
  void init() {
    session = MyBaitsUtils.openSession();
  }

  @Test
  @DisplayName("查询一个Emp")
  void selectEmp() {
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = mapper.selectEmp(7369);
    Assertions.assertEquals("SMITH", emp.getEname());
  }

  @AfterEach
  void destroy() {
    MyBaitsUtils.closeSession();
  }
}
