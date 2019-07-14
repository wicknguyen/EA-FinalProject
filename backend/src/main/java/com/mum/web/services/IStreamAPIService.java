package com.mum.web.services;

import com.mum.web.dtos.PostInfo;
import com.mum.web.dtos.RelationshipInfo;
import com.mum.web.entities.Post;
import com.mum.web.entities.Relationship;
import com.mum.web.entities.User;

import java.util.List;
import java.util.Optional;

public interface IStreamAPIService {

    //For Friendship
    //Get all followers of User
    public Optional<User> getUserById(List<User> users, String userId);

    public List<User> getFollowersList(List<User> users, User targetUser);
    public List<User> getFriendsList(User targetUser);
    public List<RelationshipInfo> getRequestedFriends(User targetUser);



    public List<User> getFollowingsList(User targetUser);

    public List<Relationship> getSuggestionsList(List<User> users, User targetUser);




    ////For Post

    public List<Post> getTimeline(List<User> users, User targetUser);
    public List<Post> getTimelineForPaging(List<User> users,User targetUser,int m,int n);

    public List<PostInfo> getTimelinePostInfo(List<User> users, User targetUser);



}
