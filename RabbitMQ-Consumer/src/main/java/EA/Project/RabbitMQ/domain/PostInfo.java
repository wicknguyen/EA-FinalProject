package EA.Project.RabbitMQ.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class PostInfo implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    String postId;
    String content;
    LocalDateTime postedDate;
    @ManyToOne(cascade = CascadeType.ALL)
    UserInfo postedBy;

    int numOfLike;
    int numOfLove;

    @OneToMany
    List<UserInfo> likeUsers;
    @OneToMany
    List<UserInfo> loveUsers;
    @OneToMany
    List<CommentInfo> commentInfos;


    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }

    public UserInfo getPostedBy() {
        return postedBy;
    }

    public void setPostedBy(UserInfo postedBy) {
        this.postedBy = postedBy;
    }

    public int getNumOfLike() {
        return numOfLike;
    }

    public void setNumOfLike(int numOfLike) {
        this.numOfLike = numOfLike;
    }

    public int getNumOfLove() {
        return numOfLove;
    }

    public void setNumOfLove(int numOfLove) {
        this.numOfLove = numOfLove;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public List<UserInfo> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(List<UserInfo> likeUsers) {
        this.likeUsers = likeUsers;
    }

    public List<UserInfo> getLoveUsers() {
        return loveUsers;
    }

    public void setLoveUsers(List<UserInfo> loveUsers) {
        this.loveUsers = loveUsers;
    }


    public PostInfo()
    {

    }

    public PostInfo(String postId, String content
            , LocalDateTime postedDate
            , UserInfo postedBy, int numOfLike, int numOfLove

            , List<UserInfo> likeUsers, List<UserInfo> loveUsers
            , List<CommentInfo> commentInfos) {
        this.postId = postId;
        this.content = content;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
        this.numOfLike = numOfLike;
        this.numOfLove = numOfLove;
        this.likeUsers = likeUsers;
        this.loveUsers = loveUsers;
        this.commentInfos = commentInfos;
    }

    public List<CommentInfo> getCommentInfos() {
        return commentInfos;
    }

    public void setCommentInfos(List<CommentInfo> commentInfos) {
        this.commentInfos = commentInfos;
    }

}
