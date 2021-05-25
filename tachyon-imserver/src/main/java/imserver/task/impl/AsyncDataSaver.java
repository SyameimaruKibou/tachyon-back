package imserver.task.impl;

import com.alibaba.fastjson.JSONObject;
import imserver.data.OnlineUsersInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import imserver.task.DataSaver;
import imserver.vo.imdata.ChatObject;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 用于异步存储聊天记录
 * 暂时通过开辟子线程发送HTTP请求的方式进行连接
 *
 */

@Service
public class AsyncDataSaver implements DataSaver {
    private final static Logger LOGGER = LoggerFactory.getLogger(AsyncDataSaver.class);

    /**
     * The default buffer size.
     */
    private static final int DEFAULT_QUEUE_SIZE = 64;
    // blockingQueue 作为消息异步存储的缓存
    private BlockingQueue<ChatObject> blockingQueue = new ArrayBlockingQueue<>(DEFAULT_QUEUE_SIZE);

    @Autowired
    private StringRedisTemplate redis;

    @Autowired
    private OnlineUsersInfo onlineUsersInfo;


    private volatile boolean started = false;
    private Worker worker = new Worker();

    /**
     * 消息异步存储方法
     */
    @Override
    public void save(ChatObject msg) {
        // 如果异步存储进程未启动，那么启动该进程
        startDataSaver();
        try {
            blockingQueue.put(msg);
        } catch (InterruptedException e) {
            LOGGER.error("InterruptedException", e);
        }

    }

    /**
     * 消息异步发送进程
     */
    private class Worker extends Thread {
        @Override
        public void run() {
            while (started) {
                try {
                    ChatObject msg = blockingQueue.take();
                    sendLog(msg);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }

    }

    /**
     * TODO: 其他持久化方式
     * 当前的持久化措施暂时选择使用和imserver同位置的 redis 直接存储。后续需要进行修改
     *
     */
    private void sendLog(ChatObject msg) {
        Long groupId = onlineUsersInfo.getGroup(msg.getUserId());
        // 传输使用Redis客户端，发送JSON数组
        if (msg.getTo() == 0L ) redis.opsForList().leftPush("group:" + groupId.toString(),JSONObject.toJSONString(msg));
        redis.opsForList().leftPush("rec:" + msg.getTo() + "-" + "from:" + msg.getUserId(),JSONObject.toJSONString(msg.getMsg()));
    }

    /**
     * 开始工作
     */
    private void startDataSaver() {
        if (started) {
            return;
        }

        // 启动异步发送进程
        worker.setDaemon(true);
        worker.setName("AsyncDataSaver-Worker");
        started = true;
        worker.start();
    }

    @Override
    public void stop() {
        started = false;
        worker.interrupt();
    }
}
