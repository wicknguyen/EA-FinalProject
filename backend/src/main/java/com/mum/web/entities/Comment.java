package com.mum.web.entities;

import com.mum.web.functional.PostFunctionUtils;
import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue
    private Long commentId;
    private String content;
    private LocalDateTime postedDate;
    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;// user who posted this article
    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Interaction> interactions;
    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "parentCommentId")
    private Comment parentComment;

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

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

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
