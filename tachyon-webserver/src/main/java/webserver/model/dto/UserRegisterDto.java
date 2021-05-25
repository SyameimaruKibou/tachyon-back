package webserver.model.dto;

import lombok.Data;

@Data
public class UserRegisterDto {
    private String username;
    private String nickname;
    private String telephone;
    private String password;
    private Boolean useGroupCode;
    private String groupName;
    private String groupCode;
}
