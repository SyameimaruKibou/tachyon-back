package imserver.server;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO: 改用专门的Netty方式连接log服务器
 *
 * @author Kibou
 */
public class LogServerClient {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogServerClient.class);
    private EventLoopGroup group = new NioEventLoopGroup(0,new DefaultThreadFactory("cim-work"));
    private SocketChannel channel;

}
