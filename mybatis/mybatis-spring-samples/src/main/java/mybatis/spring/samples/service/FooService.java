package mybatis.spring.samples.service;

import mybatis.spring.samples.domain.Emp;

import java.util.List;

/**
 * @author wangyongtao
 * @date 2019/10/19
 */
public interface FooService {

    List<Emp> doSomeBusinessStuff(int deptNo);
}
