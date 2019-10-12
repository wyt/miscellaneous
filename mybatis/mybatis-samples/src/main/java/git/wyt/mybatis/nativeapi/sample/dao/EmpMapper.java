package git.wyt.mybatis.nativeapi.sample.dao;

import git.wyt.mybatis.nativeapi.sample.domain.Emp;
import org.apache.ibatis.annotations.Param;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
public interface EmpMapper {

  Emp selectEmp(int empNo);

  /** 查询出级联的Dept */
  Emp selectEmpCascade(int empNo);

  Emp findByColumn(@Param("column") String column, @Param("value") String value);

  void insertEmp(Emp emp);

  void deleteEmp(int empNo);

  void updateEmp(Emp emp);
}
