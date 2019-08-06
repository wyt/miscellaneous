package git.wyt.mybatis.nativeapi.sample.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
@Setter
@Getter
public class Emp {

  /** 员工编号 */
  private int empNo;

  /** 员工姓名 */
  private String eName;

  /** 员工岗位 */
  private String job;

  /** 员工上级 */
  private int mgr;

  /** 员工入职日期 */
  private Date hireDate;

  /** 员工工资 */
  private BigDecimal sal;

  /** 员工提成 */
  private BigDecimal comm;

  /** 员工部门 */
  private int deptNo;

  /** Emp与Dept一对一 */
  private Dept dept;
}
