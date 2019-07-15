package com.mum.web.dtos;

import java.util.List;

public class TimelineInfo {

    UserInfo myProfile;
    List<PostInfo> posts;
    List<UserInfo> friends;


    List<RelationshipInfo> requestedFriends;

    List<UserInfo> followings;

    List<RelationshipInfo> suggestedFriends;

    List<UserInfo> waitingriends;


    public List<UserInfo> getWaitingriends() {
        return waitingriends;
    }

    public void setWaitingriends(List<UserInfo> waitingriends) {
        this.waitingriends = waitingriends;
    }


    public List<RelationshipInfo> getRequestedFriends() {
        return requestedFriends;
    }

    public void setRequestedFriends(List<RelationshipInfo> requestedFriends) {
        this.requestedFriends = requestedFriends;
    }


    public UserInfo getMyProfile() {
        return myProfile;
    }

    public void setMyProfile(UserInfo myProfile) {
        this.myProfile = myProfile;
    }

    public List<PostInfo> getPosts() {
        return posts;
    }

    public void setPosts(List<PostInfo> posts) {
        this.posts = posts;
    }

    public List<UserInfo> getFriends() {
        return friends;
    }

    public void setFriends(List<UserInfo> friends) {
        this.friends = friends;
    }

    public List<UserInfo> getFollowings() {
        return followings;
    }

    public void setFollowings(List<UserInfo> followings) {
        this.followings = followings;
    }

    public List<RelationshipInfo> getSuggestedFriends() {
        return suggestedFriends;
    }

    public void setSuggestedFriends(List<RelationshipInfo> suggestedFriends) {
        this.suggestedFriends = suggestedFriends;
    }
}
