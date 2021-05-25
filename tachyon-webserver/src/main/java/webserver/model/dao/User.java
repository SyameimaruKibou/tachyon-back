package webserver.model.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String nickname;
    private String telephone;
    private String password;
    private String avatar;
    private int role;
    private Long groupId;
    // 程序自行维护依赖关系
    @Transient
    private Organization group;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;

    public User() {

    }

    public User(String username, String nickname, String telephone, String password) {
        this.username = username;
        this.nickname = nickname;
        this.telephone = telephone;
        this.password = password;
    }

    public User(String username, String nickname, String telephone, String password, int role, Long groupId) {
        this.username = username;
        this.nickname = nickname;
        this.telephone = telephone;
        this.password = password;
        this.role = role;
        this.groupId = groupId;
    }
}
