package EA.Project.RabbitMQ.dto;

import EA.Project.RabbitMQ.domain.PostInfo;

import javax.persistence.*;

@Entity
public class PostMessageInfo {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    PostInfo postInfo;
    String userId;
    int status = 1;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PostInfo getPostInfo() {
        return postInfo;
    }

    public void setPostInfo(PostInfo postInfo) {
        this.postInfo = postInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public PostMessageInfo(){

    }

    public PostMessageInfo(PostInfo postInfo, String userId, int status) {
        this.postInfo = postInfo;
        this.userId = userId;
        this.status = status;
    }
}
