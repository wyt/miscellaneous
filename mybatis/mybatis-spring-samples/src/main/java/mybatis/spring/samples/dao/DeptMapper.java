package mybatis.spring.samples.dao;


import mybatis.spring.samples.domain.Dept;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
public interface DeptMapper {

  /** 查询出级联的Emp */
  Dept selectEmps(int deptNo);
}
