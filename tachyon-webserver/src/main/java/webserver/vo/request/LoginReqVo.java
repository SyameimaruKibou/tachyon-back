package webserver.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import request.BaseRequest;

import javax.validation.constraints.NotNull;

@Data
public class LoginReqVo extends BaseRequest {
    @NotNull(message = "username 不能为空")
    @ApiModelProperty(required = true, value = "username", example = "admin")
    private String username;
    @NotNull(message = "password 不能为空")
    @ApiModelProperty(required = true, value = "password", example = "123456")
    private String password;
}
