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

  static int insterEmpNo;

  @BeforeEach
  void init() {
    System.out.println("init >--------- invoked");
    session = MyBaitsUtils.openSession();
  }

  @Test
  @Order(1)
  @DisplayName("插入一个Emp")
  void insertEmp() {
    System.out.println("insertEmp --------- invoked");
    try {
      EmpMapper mapper = session.getMapper(EmpMapper.class);

      Emp emp = new Emp();
      emp.setEname("Angus");
      emp.setJob("CEO");
      emp.setHiredate(new Date(System.currentTimeMillis()));
      emp.setSal(new BigDecimal(42000.23));
      emp.setDeptno(10);

      mapper.insertEmp(emp);
      insterEmpNo = emp.getEmpno();
      System.out.println("empno: " + insterEmpNo);
    } catch (Exception e) {
      session.rollback();
    }
  }

  @Test
  @Order(2)
  @DisplayName("更新一个Emp")
  void updateEmp() {
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = mapper.selectEmp(insterEmpNo);
    emp.setJob("CTO");
    mapper.updateEmp(emp);
  }

  @Test
  @Order(3)
  @DisplayName("查询一个Emp")
  void selectEmp() {
    System.out.println("selectEmp --------- invoked");

    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = mapper.selectEmp(insterEmpNo);
    Assertions.assertEquals("Angus", emp.getEname());
    Assertions.assertEquals("CTO", emp.getJob());
  }

  @Test
  @Order(4)
  @DisplayName("删除一个Emp")
  void delete() {
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    mapper.deleteEmp(insterEmpNo);
  }

  @AfterEach
  void destroy() {
    System.out.println("destroy ---------< invoked");
    MyBaitsUtils.closeSession();
  }
}
