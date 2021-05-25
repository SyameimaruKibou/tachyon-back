package imserver.server;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import imserver.data.OnlineUsersInfo;
import imserver.vo.imdata.SimpleMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import imserver.task.DataSaver;
import imserver.vo.imdata.ChatObject;
import imserver.vo.imdata.RegisterObject;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;

@Component
public class IMServerService {
    Timer timer;

    @Autowired
    private SocketIOServer server;

    @Autowired
    OnlineUsersInfo onlineUsersInfo;

    @Autowired
    DataSaver dataSaver;

    private final static Logger LOGGER = LoggerFactory.getLogger(IMServerService.class);

    @PostConstruct
    private void autoStartup() throws Exception {
        start();
    }
    @PreDestroy
    private void autoStop() throws Exception  {
        destory();
    }

    public void start() {
        LOGGER.info("netty-socketio server started");

        server.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient socketIOClient) {
                LOGGER.info("[client session: " + socketIOClient.getSessionId() + " joined]");
            }
        });

        // 注册用户注册监听器（用于让用户加入自己所在的组/房间）
        // 前提是 webserver 为用户提供了正确的 id-group 的对应关系
        server.addEventListener("register", RegisterObject.class, new DataListener<RegisterObject>() {
            @Override
            public void onData(SocketIOClient socketIOClient, RegisterObject registerObject, AckRequest ackRequest) throws Exception {
                LOGGER.info("[client session: " + socketIOClient.getSessionId() + " joined as user:"
                        + registerObject.getUserId() + "]");
                onlineUsersInfo.putSession(registerObject.getUserId(), socketIOClient.getSessionId());
                // 保存 user-group 和 user-session 的映射关系
                LOGGER.info("[user: " + registerObject.getUserId()
                        + " logined in group: " + registerObject.getGroupId() + "]");
                onlineUsersInfo.putGroup(registerObject.getUserId(), registerObject.getGroupId());

                // 为发布注册信息的这个session加入组
                socketIOClient.joinRoom(registerObject.getGroupId().toString());

            }
        });

        // 注册群聊事件监听器
        server.addEventListener("groupchat", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient socketIOClient, ChatObject data, AckRequest ackRequest) throws Exception {
                // 转发消息
                server.getRoomOperations(onlineUsersInfo.getGroup(data.getUserId()).toString()).sendEvent("groupchat",data);
                // 持久化消息
                // dataSaver.save(data);
            }
        });

        // 注册单聊事件监听器
        server.addEventListener("p2pchat", ChatObject.class, new DataListener<ChatObject>() {
            @Override
            public void onData(SocketIOClient socketIOClient, ChatObject data, AckRequest ackRequest) throws Exception {
                // 从 chatObject 中获得接受方信息并发送

                server.getClient(onlineUsersInfo.getSessionInfo(data.getTo())).sendEvent("p2pchat", data);
                dataSaver.save(data);
            }
        });


        // 注册用户退出监听器
        server.addDisconnectListener(socketIOClient -> {
            LOGGER.info("[client session: " + socketIOClient.getSessionId() + " left]");
            // 清除client信息
            onlineUsersInfo.userLeave(socketIOClient.getSessionId());
            // 退出所在房间
            for (String room : socketIOClient.getAllRooms()) {
                LOGGER.info("[session: " + socketIOClient.getSessionId() + " left]");
                socketIOClient.leaveRoom(room);
            }

        });

        // 获取群组内在线情况信息
        server.addEventListener("checkOnlineUsers", SimpleMsg.class, new DataListener<SimpleMsg>() {
            @Override
            public void onData(SocketIOClient socketIOClient, SimpleMsg simpleMsg, AckRequest ackRequest) throws Exception {
                Set<Long> userList = onlineUsersInfo.getOnlineUsersByGroupId(simpleMsg.getId());
                if (userList == null)
                    socketIOClient.sendEvent("users",new ArrayList<Long>());
                else
                    socketIOClient.sendEvent("users",userList);
            }
        });

        timer = new Timer();
        timer.schedule(new TimeTask(), 10*1000,10*1000);

        // 正式启动
        server.start();
    }

    public void broadcastOnlineUser() {
        for(SocketIOClient socketIOClient : server.getAllClients()) {
            Set<Long> userList = onlineUsersInfo.getOnLineUsersBySessionId(socketIOClient.getSessionId());
            if (userList == null)
                socketIOClient.sendEvent("users",new ArrayList<Long>());
            else
                socketIOClient.sendEvent("users",userList);
        }
    }

    public void destory() {
        server.stop();
        LOGGER.info("netty-socketio server exited");
    }

    // 定时任务
    public class TimeTask extends TimerTask {
        public void run() {
            //LOGGER.info("timed Task running");
            broadcastOnlineUser();
        }
    }
}
