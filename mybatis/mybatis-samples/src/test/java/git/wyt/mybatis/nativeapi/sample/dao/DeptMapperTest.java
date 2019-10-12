package git.wyt.mybatis.nativeapi.sample.dao;

import git.wyt.mybatis.nativeapi.sample.domain.Dept;
import git.wyt.mybatis.nativeapi.sample.domain.Emp;
import git.wyt.mybatis.nativeapi.sample.util.MyBaitsUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;

import java.util.List;

/**
 * @author wangyongtao
 * @date 2019-8-6
 */
@DisplayName("DeptMapper测试用例")
class DeptMapperTest {

  SqlSession session = null;

  @BeforeEach
  void init() {
    session = MyBaitsUtils.openSession();
  }

  @DisplayName("查询部门管理的员工")
  @Test
  void selectEmps() {
    DeptMapper deptMapper = session.getMapper(DeptMapper.class);
    Dept dept = deptMapper.selectEmps(10);

    List<Emp> emps = dept.getEmps();

    for (Emp emp : emps) {
      System.out.println(emp.getEmpNo() + "," + emp.getEName());
    }

    Assertions.assertEquals(3, emps.size());
  }

  @AfterEach
  void destroy() {
    MyBaitsUtils.closeSession();
  }
}
