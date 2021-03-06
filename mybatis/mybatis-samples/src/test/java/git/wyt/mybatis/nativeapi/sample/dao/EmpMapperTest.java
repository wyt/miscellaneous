package git.wyt.mybatis.nativeapi.sample.dao;

import git.wyt.mybatis.nativeapi.sample.domain.Dept;
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
      emp.setEName("Angus");
      emp.setJob("CEO");
      emp.setMgr(7739);
      emp.setHireDate(new Date(System.currentTimeMillis()));
      emp.setSal(new BigDecimal(42000.23));
      emp.setComm(new BigDecimal(100));

      Dept dept = new Dept();
      dept.setDeptNo(10);
      emp.setDept(dept);

      mapper.insertEmp(emp);
      insterEmpNo = emp.getEmpNo();
      System.out.println("empNo: " + insterEmpNo);
    } catch (Exception e) {
      session.rollback();
      // 抛出，否则发生异常时，junit测试可能通过
      throw e;
    }
  }

  @Test
  @Order(2)
  @DisplayName("更新一个Emp")
  void updateEmp() {
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = mapper.selectEmp(insterEmpNo);

    Dept dept = new Dept();
    dept.setDeptNo(40);
    emp.setDept(dept);
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
    Assertions.assertEquals("Angus", emp.getEName());
    Assertions.assertEquals("CTO", emp.getJob());

    Assertions.assertNull(emp.getDept().getDName());
    Assertions.assertNull(emp.getDept().getLoc());
    Assertions.assertEquals(40, emp.getDept().getDeptNo());
  }

  @Test
  @Order(4)
  @DisplayName("查询一个Emp,关联部门信息")
  void selectEmpCascade() {
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = mapper.selectEmpCascade(insterEmpNo);

    Assertions.assertEquals("Angus", emp.getEName());
    Assertions.assertEquals("CTO", emp.getJob());

    Assertions.assertEquals(40, emp.getDept().getDeptNo());
    Assertions.assertEquals("OPERATIONS", emp.getDept().getDName());
    Assertions.assertEquals("BOSTON", emp.getDept().getLoc());
  }

  @Test
  @Order(5)
  @DisplayName("删除一个Emp")
  void delete() {
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    mapper.deleteEmp(insterEmpNo);
  }

  @Test
  @Order(6)
  @DisplayName("按指定列查询Emp")
  void findByColumn() {
    EmpMapper mapper = session.getMapper(EmpMapper.class);
    Emp emp = mapper.findByColumn("e_name", "SCOTT");
    Assertions.assertEquals(7788, emp.getEmpNo());
  }

  @AfterEach
  void destroy() {
    System.out.println("destroy ---------< invoked");
    MyBaitsUtils.closeSession();
  }
}
