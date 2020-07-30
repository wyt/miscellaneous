package git.wyt.support;

/**
 * Listener for watch config.
 *
 * @author Nacos
 */
public interface Listener {

  /**
   * Receive config info.
   *
   * @param configInfo config info
   */
  void receiveConfigInfo(final String configInfo);
}
