package request;

import io.swagger.annotations.ApiModelProperty;

/**
 * Function:
 * @author Kibou
 * Date: 2021/4/7 14:28
 * @since JDK 11
 */

public class BaseRequest {

    /*
    @ApiModelProperty(required = false, value="唯一请求号", example = "123456789")
    private String reqNo;
    */
    @ApiModelProperty(required = false, value="当前请求时间戳", example = "0")
    private int timeStamp;

    public BaseRequest() {
        this.setTimeStamp((int)(System.currentTimeMillis() / 1000));
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "BaseRequest{" +
                "timeStamp=" + timeStamp +
                '}';
    }
}
