package com.mum.web.entities;

import org.apache.commons.codec.digest.DigestUtils;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Interaction {
    @Id
    @GeneratedValue
    protected Long interactionId;
    protected String icon;
    protected LocalDateTime actionDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "postId")
    protected Post post;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "commentId")
    protected Comment comment;
    @ManyToOne
    @JoinColumn(name = "userId")
    protected User user;
    @Enumerated(EnumType.STRING)
    protected InteractionType interactionType;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public InteractionType getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(InteractionType interactionType) {
        this.interactionType = interactionType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getInteractionId() {
        return interactionId;
    }

    public void setInteractionId(Long interactionId) {
        this.interactionId = interactionId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

//    public void generateInteractionId() {
//        this.interactionId = DigestUtils.md5Hex(user.getUserId() + actionDate + post.getPostId());
//    }
}
