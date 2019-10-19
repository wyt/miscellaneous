package mybatis.spring.samples.service.impl;

import mybatis.spring.samples.dao.DeptMapper;
import mybatis.spring.samples.dao.EmpMapper;
import mybatis.spring.samples.domain.Dept;
import mybatis.spring.samples.domain.Emp;
import mybatis.spring.samples.service.FooService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyongtao
 * @date 2019/10/19
 */
@Service
public class FooServiceImpl implements FooService {

  @Autowired private DeptMapper deptMapper;
  @Autowired private EmpMapper empMapper;

  @Override
  public List<Emp> doSomeBusinessStuff(int deptNo) {
    Dept dept = deptMapper.selectEmps(deptNo);
    return dept.getEmps();
  }
}
