package webserver.model.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String invitationCode;
    private String avatar;
    private Long managerId;
    private boolean isDeleted;
    private boolean isFixed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date createTime;
    public Organization() {

    }

    public Organization(String name, String invitationCode, Long managerId) {
        this.name = name;
        this.invitationCode = invitationCode;
        this.managerId = managerId;
        isDeleted = false;
        isFixed = false;
    }
}
