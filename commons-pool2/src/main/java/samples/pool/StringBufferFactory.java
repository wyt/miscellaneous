package samples.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/** 实现具体的池对象工厂 */
public class StringBufferFactory extends BasePooledObjectFactory<StringBuffer> {

  /**
   * Creates an object instance, to be wrapped in a PooledObject. This method must support
   * concurrent, multi-threaded activation. 创建对象实例，将会被包装成池化对象，该方法必须支持多线程并发处理。
   */
  @Override
  public StringBuffer create() {
    return new StringBuffer();
  }

  /** Use the default PooledObject implementation. 使用默认的池化对象实现。 */
  @Override
  public PooledObject<StringBuffer> wrap(StringBuffer buffer) {
    return new DefaultPooledObject<>(buffer);
  }

  /** When an object is returned to the pool, clear the buffer. 当对象返回，清理缓冲。 */
  @Override
  public void passivateObject(PooledObject<StringBuffer> pooledObject) {
    pooledObject.getObject().setLength(0);
  }

  // for all other methods, the no-op implementation(空指令实现)
  // in BasePooledObjectFactory will suffice
}
