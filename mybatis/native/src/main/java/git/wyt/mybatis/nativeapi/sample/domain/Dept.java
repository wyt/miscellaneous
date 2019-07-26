package git.wyt.mybatis.nativeapi.sample.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
@Setter
@Getter
public class Dept {

  /** 部门编号 */
  private int deptno;

  /** 部门名称 */
  private String dname;

  /** 部门位置 */
  private String loc;
}
