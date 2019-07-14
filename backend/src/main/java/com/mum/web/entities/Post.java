package com.mum.web.entities;

import com.mum.web.functional.AuthenticationFunctionUtils;
import com.mum.web.functional.PostFunctionUtils;
import com.mum.web.dtos.PostInfo;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Post  {

    private String postId;
    private String content;
    private LocalDateTime postedDate;

    private User user;// user who posted this article

    private List<Interaction> interactions;

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }



    public Post() {
        this.interactions = new ArrayList<Interaction>();
        this.comments = new ArrayList<Comment>();

    }


    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addInteraction(Interaction interaction) {
        this.interactions.add(interaction);
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

    public LocalDateTime getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(LocalDateTime postedDate) {
        this.postedDate = postedDate;
    }



    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

    public void setInteractions(List<Interaction> interactions) {
        this.interactions = interactions;
    }


    public List<User> getInteractionUserList(InteractionType interactionType) {
        return PostFunctionUtils.getInteractionUserList.apply(this.interactions, interactionType);

    }


    public List<User> getLikeUserList() {

        return PostFunctionUtils.getInteractionUserList.apply(this.interactions, InteractionType.LIKE);

    }

    public int getLikeCount() {
        return this.getLikeUserList().size();
    }


    public List<User> getLoveUserList() {
        return PostFunctionUtils.getInteractionUserList.apply(this.interactions, InteractionType.LOVE);
    }


    public int getLoveCount() {
        return this.getLoveUserList().size();
    }

    public void generatePostId() {
        this.postId = DigestUtils.md5Hex(user.getUserId() + postedDate);
    }

    public PostInfo getPostInfo()
    {
        return new PostInfo(this.getPostId(),this.getContent(),
                this.getPostedDate(), AuthenticationFunctionUtils.convertToUserInfo.apply(this.user)
                ,this.getLikeCount(),this.getLoveCount(),
                AuthenticationFunctionUtils.converToListUserInfo.apply(this.getLikeUserList())
                ,AuthenticationFunctionUtils.converToListUserInfo.apply(this.getLoveUserList()),
               //null
                PostFunctionUtils.convertToListCommentInfo.apply(this.getComments())
        );
    }

}
