package com.mum.web.entities;

import com.mum.web.functional.RelationFunctionUtils;

import com.mum.web.functional.AuthenticationFunctionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long userId;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private LocalDate dob;
    private String avatar;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @OneToMany(cascade = CascadeType.ALL)
    private List<Relation> relations;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    public List<User> getFriends() {
        return RelationFunctionUtils.getRelationUserList.apply(relations, RelationType.FRIEND, RelationStatus.ACCEPTED);
    }

    public List<User> getFollowings() {
        return RelationFunctionUtils.getRelationUserList.apply(relations, RelationType.FOLLOWING, RelationStatus.FOLLOWING);
    }

    public List<User> getWatings() {
        return RelationFunctionUtils.getRelationUserList.apply(relations, RelationType.FRIEND, RelationStatus.WAITING);
    }

    public List<User> getRequestedFriends() {
        return RelationFunctionUtils.getRelationUserList.apply(relations, RelationType.FRIEND, RelationStatus.REQUESTED);
    }

    public void acceptFriend(User user) {
        //Find requested user
        Optional<Relation> oldRelation = AuthenticationFunctionUtils.getReleationByUser.apply(this.relations, user, RelationType.FRIEND, RelationStatus.REQUESTED);
        Relation relation = new Relation();
        relation.setUser(user);
        relation.setRelationType(RelationType.FRIEND);
        relation.setRelationStatus(RelationStatus.ACCEPTED);
        //oldRelation.ifPresent(d -> relation.setRequestedDate(d.getRequestedDate()));
        if (oldRelation.isPresent()) {
            relation.setRequestedDate(oldRelation.get().getRequestedDate());
        }
        relation.setAcceptedDate(LocalDateTime.now());
        this.relations.remove(oldRelation.get());
        this.relations.add(relation);

    }

    public void rejectFriend(User user, RelationStatus relationStatus) {
        //Find requested user
        Optional<Relation> oldRelation = AuthenticationFunctionUtils.getReleationByUser.apply(this.relations, user, RelationType.FRIEND, relationStatus);
        if (oldRelation.isPresent()) {
            this.relations.remove(oldRelation.get());
        }
    }

    public void updateWatingFriend(User user) {
        //Find requested user
        Optional<Relation> oldRelation = AuthenticationFunctionUtils.getReleationByUser.apply(this.relations, user, RelationType.FRIEND, RelationStatus.WAITING);

        Relation relation = new Relation();
        relation.setUser(user);
        relation.setRelationType(RelationType.FRIEND);
        relation.setRelationStatus(RelationStatus.ACCEPTED);
        //oldRelation.ifPresent(d -> relation.setRequestedDate(d.getRequestedDate()));
        if (oldRelation.isPresent()) {
            relation.setRequestedDate(oldRelation.get().getRequestedDate());
        }
        relation.setAcceptedDate(LocalDateTime.now());
        this.relations.remove(oldRelation);
        this.relations.add(relation);

    }

    public void addFriend(User user) {
        Relation relation = new Relation();
        relation.setUser(user);
        relation.setRelationType(RelationType.FRIEND);
        relation.setRelationStatus(RelationStatus.ACCEPTED);
        relation.setAcceptedDate(LocalDateTime.now());
        this.relations.add(relation);
    }

    public void addRequestedFriend(User user) {
        Relation relation = new Relation();
        relation.setUser(user);
        relation.setRelationType(RelationType.FRIEND);
        relation.setRelationStatus(RelationStatus.REQUESTED);
        relation.setRequestedDate(LocalDateTime.now());
        this.relations.add(relation);
    }

    public void addWaitingFriend(User user) {
        Relation relation = new Relation();
        relation.setUser(user);
        relation.setRelationType(RelationType.FRIEND);
        relation.setRelationStatus(RelationStatus.WAITING);
        relation.setRequestedDate(LocalDateTime.now());
        this.relations.add(relation);
    }

    public void addFollowing(User user) {
        Relation relation = new Relation();
        relation.setUser(user);
        relation.setRelationType(RelationType.FOLLOWING);
        relation.setRelationStatus(RelationStatus.FOLLOWING);
        relation.setAcceptedDate(LocalDateTime.now());
        this.relations.add(relation);
    }

    public User() {
        this.relations = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
