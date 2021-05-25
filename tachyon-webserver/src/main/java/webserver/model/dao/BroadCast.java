package webserver.model.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class BroadCast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String content;
    private String title;

    private Long groupId;
    private Long userId;
    private String userName;
    private Long projectId;

    @Transient
    private Organization group;
    @Transient
    private User user;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date updateTime;

    private boolean isCommon;
    private boolean isDeleted;

    public BroadCast(String name, String content, String title, Long userId, boolean isCommon, boolean isDeleted) {
        this.name = name;
        this.content = content;
        this.title = title;
        this.userId = userId;
        this.isCommon = isCommon;
        this.isDeleted = isDeleted;
    }
}
