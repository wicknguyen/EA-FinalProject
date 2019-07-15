package com.mum.web.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class PostInfo implements Serializable {

    Long postId;
    String content;
    LocalDateTime postedDate;
    UserInfo postedBy;

    int numOfLike;

    List<UserInfo> likeUsers;


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


    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
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

    public PostInfo(Long postId, String content
            , LocalDateTime postedDate
            , UserInfo postedBy, int numOfLike

            , List<UserInfo> likeUsers
            , List<CommentInfo> commentInfos) {
        this.postId = postId;
        this.content = content;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
        this.numOfLike = numOfLike;
        this.likeUsers = likeUsers;
        this.commentInfos = commentInfos;
    }

    public List<CommentInfo> getCommentInfos() {
        return commentInfos;
    }

    public void setCommentInfos(List<CommentInfo> commentInfos) {
        this.commentInfos = commentInfos;
    }

    List<CommentInfo> commentInfos;

    @Override
    public String toString() {
        return "PostInfo{" +
                "postId='" + postId + '\'' +
                ", content='" + content + '\'' +
                ", postedDate=" + postedDate +
                ", postedBy=" + postedBy +
                ", numOfLike=" + numOfLike +
                ", likeUsers=" + likeUsers +
                ", commentInfos=" + commentInfos +
                '}';
    }

}
