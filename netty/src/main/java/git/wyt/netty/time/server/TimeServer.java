package git.wyt.netty.time.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TimeServer {

  private int port;

  public TimeServer(int port) {
    this.port = port;
  }

  public void run() throws Exception {
    // 创建两个线程组 boss and worker
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap(); // 创建服务端启动对象，并设置参数
      b.group(bossGroup, workerGroup)
          .channel(NioServerSocketChannel.class) // 设置服务端通道实现类型
          .option(ChannelOption.SO_BACKLOG, 128) // 初始化服务端可连接队列
          .childOption(ChannelOption.SO_KEEPALIVE, true) // 设置保持活动连接状态
          .childHandler( // 使用匿名内部类的形式初始化通道对象
              new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                  ch.pipeline().addLast(new TimeServerHandler());
                }
              });

      System.out.println("启动服务端就绪...");

      // Bind and start to accept incoming connections.
      ChannelFuture f = b.bind(port).sync();

      // Wait until the server socket is closed.
      // In this example, this does not happen, but you can do that to gracefully
      // shut down your server.
      f.channel().closeFuture().sync();
    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }

  public static void main(String[] args) throws Exception {
    int port = 8080;
    if (args.length > 0) {
      port = Integer.parseInt(args[0]);
    }

    new TimeServer(port).run();
  }
}
