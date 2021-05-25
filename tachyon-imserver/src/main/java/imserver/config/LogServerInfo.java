package imserver.config;

/**
 * TODO: 用于存储 LogServer 的相关信息，在后续数据持久化服务器实装之后使用
 *
  */

public class LogServerInfo {
    private String serverIp;
    private Integer serverPort;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }
}
