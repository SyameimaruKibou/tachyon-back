package imserver.task;

import imserver.vo.imdata.ChatObject;

public interface DataSaver {
    /**
     * 异步存储并发送信息
     */
    void save(ChatObject msg);

    /**
     * 停止写入
     */
    void stop();

    /**
     * TODO: 查询聊天记录
     * @param key
     */
}
