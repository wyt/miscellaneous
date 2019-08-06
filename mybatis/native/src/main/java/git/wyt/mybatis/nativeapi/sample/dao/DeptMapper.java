package git.wyt.mybatis.nativeapi.sample.dao;

import git.wyt.mybatis.nativeapi.sample.domain.Dept;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
public interface DeptMapper {

  /** 查询出级联的Emp */
  Dept selectEmps(int deptNo);
}
