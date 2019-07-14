package com.mum.web.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class CommentInfo {

    String commentId;
    LocalDateTime postedDate;
    UserInfo postedBy;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    String content;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
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

    public int getNumOfLove() {
        return numOfLove;
    }

    public void setNumOfLove(int numOfLove) {
        this.numOfLove = numOfLove;
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



    int numOfLike;
    int numOfLove;

    List<UserInfo> likeUsers;
    List<UserInfo> loveUsers;

    public List<CommentInfo> getCommentInfos() {
        return commentInfos;
    }

    public void setCommentInfos(List<CommentInfo> commentInfos) {
        this.commentInfos = commentInfos;
    }

    List<CommentInfo> commentInfos;

    public CommentInfo()
    {

    }

    public CommentInfo(String commentId,String content, LocalDateTime postedDate, UserInfo postedBy,
                       int numOfLike, int numOfLove, List<UserInfo> likeUsers, List<UserInfo> loveUsers
                       , List<CommentInfo> commentInfos) {
        //)
        // {
        this.commentId = commentId;
        this.postedDate = postedDate;
        this.postedBy = postedBy;
        this.content = content;
        this.numOfLike = numOfLike;
        this.numOfLove = numOfLove;
        this.likeUsers = likeUsers;
        this.loveUsers = loveUsers;
        this.commentInfos = commentInfos;
    }
}
