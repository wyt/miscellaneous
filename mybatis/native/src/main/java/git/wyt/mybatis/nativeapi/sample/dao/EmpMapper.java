package git.wyt.mybatis.nativeapi.sample.dao;

import git.wyt.mybatis.nativeapi.sample.domain.Emp;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
public interface EmpMapper {

  Emp selectEmp(int empno);
}
