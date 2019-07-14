package com.mum.web.entities;

import com.mum.web.functional.PostFunctionUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;


public class Comment implements Serializable {


    private String commentId;
    private String content;
    private LocalDateTime postedDate;
    private Post post;

    private User user;// user who posted this article

    private List<Interaction> interactions;

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }



    public Comment() {
        this.interactions = new ArrayList<Interaction>();
        this.comments = new ArrayList<Comment>();

    }


    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void addInteraction(Interaction interaction) {
        this.interactions.add(interaction);
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

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void generateCommentId() {
        this.commentId = DigestUtils.md5Hex(user.getUserId() + postedDate);
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
