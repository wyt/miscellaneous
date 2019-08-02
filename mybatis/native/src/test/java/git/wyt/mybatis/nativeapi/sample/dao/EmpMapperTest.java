package git.wyt.mybatis.nativeapi.sample.dao;

import git.wyt.mybatis.nativeapi.sample.domain.Emp;
import git.wyt.mybatis.nativeapi.sample.util.MyBaitsUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
@DisplayName("EmpMapper测试用例")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmpMapperTest {

  SqlSession session = null;

  @BeforeEach
  void init() {
    System.out.println("init >--------- invoked");
    session = MyBaitsUtils.openSession();
  }

  @Test
  @Order(1)
  @DisplayName("查询一个Emp")
  void selectEmp() {
    System.out.println("selectEmp --------- invoked");

    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = mapper.selectEmp(7369);
    Assertions.assertEquals("SMITH", emp.getEname());
  }

  @Test
  @Order(2)
  @DisplayName("插入一个Emp")
  void insertEmp() {
    System.out.println("insertEmp --------- invoked");
    try {
      EmpMapper mapper = session.getMapper(EmpMapper.class);

      Emp emp = new Emp();
      emp.setEname("wangyongtao");
      emp.setJob("CEO");
      emp.setHiredate(new Date(System.currentTimeMillis()));
      emp.setSal(new BigDecimal(42000.23));
      emp.setDeptno(10);

      mapper.insertEmp(emp);
      System.out.print("empno: " + emp.getEmpno());
    } catch (Exception e) {
      session.rollback();
    }
  }

  @AfterEach
  void destroy() {
    System.out.println("destroy ---------< invoked");
    MyBaitsUtils.closeSession();
  }
}
