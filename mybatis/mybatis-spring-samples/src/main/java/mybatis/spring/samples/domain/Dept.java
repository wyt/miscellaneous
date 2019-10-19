package mybatis.spring.samples.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
@Setter
@Getter
public class Dept {

  /** 部门编号 */
  private int deptNo;

  /** 部门名称 */
  private String dName;

  /** 部门位置 */
  private String loc;

  /** 一个部门下对应N个员工 */
  private List<Emp> emps;
}
