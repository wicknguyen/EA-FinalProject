package com.mum.web.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class CommentInfo {

    Long commentId;
    LocalDateTime postedDate;
    UserInfo postedBy;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

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

    public List<UserInfo> getLikeUsers() {
        return likeUsers;
    }

    public void setLikeUsers(List<UserInfo> likeUsers) {
        this.likeUsers = likeUsers;
    }

    int numOfLike;

    List<UserInfo> likeUsers;

    public List<CommentInfo> getCommentInfos() {
        return commentInfos;
    }

    public void setCommentInfos(List<CommentInfo> commentInfos) {
        this.commentInfos = commentInfos;
    }

    List<CommentInfo> commentInfos;

    public CommentInfo() {

    }

    public CommentInfo(Long commentId, String content, LocalDateTime postedDate, UserInfo postedBy,
                       int numOfLike, List<UserInfo> likeUsers, List<CommentInfo> commentInfos) {
        this.commentId = commentId;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
        this.content = content;
        this.numOfLike = numOfLike;
        this.likeUsers = likeUsers;
        this.commentInfos = commentInfos;
    }
}
