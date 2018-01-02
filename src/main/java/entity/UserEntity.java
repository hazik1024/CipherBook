package entity;

import enums.UserStatus;
import enums.convert.UserStatusConvert;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户表
 */
@Entity
@Table(name = "t_user")
public class UserEntity implements BaseEntity {
    @Id
    @Column(name = "uid")
    private int uid;
    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "truename")
    private String truename;
    @Column(name = "idcard")
    private String idcard;
    @Column(name = "registertime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date registertime;
    @Column(name = "lastlogintime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastlogintime;
    @Column(name = "status")
    @Convert(converter = UserStatusConvert.class)
    private UserStatus status;

    private String token;


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public Date getLastlogintime() {
        return lastlogintime;
    }

    public void setLastlogintime(Date lastlogintime) {
        this.lastlogintime = lastlogintime;
    }

    public Date getRegistertime() {
        return registertime;
    }

    public void setRegistertime(Date registertime) {
        this.registertime = registertime;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}
