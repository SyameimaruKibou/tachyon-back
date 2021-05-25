package imserver.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;

public class MyDataListener<ChatObject> implements DataListener<ChatObject> {


    @Override
    public void onData(SocketIOClient socketIOClient, ChatObject data, AckRequest ackRequest) throws Exception {
    }
}
