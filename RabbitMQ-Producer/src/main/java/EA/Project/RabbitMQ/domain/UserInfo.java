package EA.Project.RabbitMQ.domain;

import java.io.Serializable;
import java.time.LocalDate;

public class UserInfo implements Serializable {

    private int id;
    private String userId;
    private String fullName;
    private String avatar;
    private String email;
    private LocalDate dob;

    public UserInfo(){}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }


    public UserInfo(String userId, String fullName, String avatar, String email, LocalDate dob) {
        this.userId = userId;
        this.fullName = fullName;
        this.avatar = avatar;
        this.email = email;
        this.dob = dob;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", email='" + email + '\'' +
                ", dob=" + dob +
                '}';
    }
}
