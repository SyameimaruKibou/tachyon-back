package imserver.config;

import com.corundumstudio.socketio.SocketConfig;
import com.corundumstudio.socketio.SocketIOServer;
import imserver.server.IMServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IMServerConfiguration{
    private final static Logger LOGGER = LoggerFactory.getLogger(IMServerConfiguration.class);

    @Value("${im.server.host}")
    private String host;

    @Value("${im.server.port}")
    private Integer port;

    @Value("${im.server.bossCount}")
    private int bossCount;

    @Value("${im.server.workCount}")
    private int workCount;

    @Value("${im.server.allowCustomRequests}")
    private boolean allowCustomRequests;

    @Value("${im.server.upgradeTimeout}")
    private int upgradeTimeout;

    @Value("${im.server.pingTimeout}")
    private int pingTimeout;

    @Value("${im.server.pingInterval}")
    private int pingInterval;

    /**
     * 以下配置在上面的application.properties中已经注明
     * @return
     */
    @Bean
    public SocketIOServer socketIOServer() {
        SocketConfig socketConfig = new SocketConfig();
        socketConfig.setTcpNoDelay(true);
        socketConfig.setSoLinger(0);
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setSocketConfig(socketConfig);
        config.setHostname(host);
        config.setPort(port);
        config.setBossThreads(bossCount);
        config.setWorkerThreads(workCount);
        config.setAllowCustomRequests(allowCustomRequests);
        config.setUpgradeTimeout(upgradeTimeout);
        config.setPingTimeout(pingTimeout);
        config.setPingInterval(pingInterval);

        LOGGER.info("netty-socketio server configured at host: " + host + " port: " + port);
        return new SocketIOServer(config);
    }
}
