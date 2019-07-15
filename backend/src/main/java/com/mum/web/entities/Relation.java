package com.mum.web.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Relation {
    @Id
    @GeneratedValue
    private int relationId;
    @ManyToOne
    private User user;
    @Enumerated(EnumType.STRING)
    private RelationType relationType;
    @Enumerated(EnumType.STRING)
    private RelationStatus relationStatus;
    private LocalDateTime requestedDate;
    private LocalDateTime acceptedDate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RelationType getRelationType() {
        return relationType;
    }

    public void setRelationType(RelationType relationType) {
        this.relationType = relationType;
    }

    public RelationStatus getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(RelationStatus relationStatus) {
        this.relationStatus = relationStatus;
    }

    public LocalDateTime getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(LocalDateTime requestedDate) {
        this.requestedDate = requestedDate;
    }

    public LocalDateTime getAcceptedDate() {
        return acceptedDate;
    }

    public void setAcceptedDate(LocalDateTime acceptedDate) {
        this.acceptedDate = acceptedDate;
    }
}
