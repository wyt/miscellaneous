package git.wyt.mybatis.nativeapi.sample.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author wangyongtao
 * @date 2019-7-26
 */
@Slf4j
public class MyBaitsUtils {

  static final String MYBATIS_CONFIG = "mybatis-config.xml";

  static SqlSessionFactory sqlSessionFactory = null;

  static ThreadLocal<SqlSession> threadLocal = new ThreadLocal<SqlSession>();

  static {
    InputStream inputStream = null;
    try {
      inputStream = Resources.getResourceAsStream(MYBATIS_CONFIG);
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }
  }

  public static SqlSession openSession() {
    SqlSession session = threadLocal.get();
    if (session == null) {
      System.out.println("sqlSession为空，新创建");
      session = sqlSessionFactory.openSession(false);
      threadLocal.set(session);
    } else {
      System.out.println("sqlSession存在，不创建");
    }
    return session;
  }

  public static void closeSession() {
    SqlSession session = threadLocal.get();
    if (session != null) {
      session.commit();
      session.close();
      System.out.println("关闭sqlSession");
    }
    threadLocal.remove();
  }
}
