package imserver.vo.imdata;

public class ChatObject {
    // TODO: LONG 类型的userid是否过于占用空间？
    private Long userId;
    private String msg;
    private Long to;
    private String time;

    public ChatObject() {
    }

    public ChatObject(Long userId, String msg, Long to,String time) {
        super();
        this.userId = userId;
        this.msg = msg;
        this.to = to;
        this.time = time;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
