package com.mum.web.services;

import com.mum.web.dtos.PostInfo;
import com.mum.web.dtos.RelationshipInfo;
import com.mum.web.entities.Post;
import com.mum.web.entities.Relationship;
import com.mum.web.entities.User;
import com.mum.web.functional.AuthenticationFunctionUtils;
import com.mum.web.functional.PostFunctionUtils;
import com.mum.web.functional.RelationFunctionUtils;

import java.util.List;
import java.util.Optional;

public class StreamAPIService implements IStreamAPIService {

    @Override
    public List<Relationship> getSuggestionsList(List<User> users, User targetUser) {
        //Return list of users, who
        //not belong to list of friends of the targetUser
        //is friend of friends of the targetUser
        //A has a list of friends B, C
        //B has a list of friends C, D, E
        //C has a list of friends D, F, G
        //Result: D, E, F, G
        return RelationFunctionUtils.getSuggestionsList.apply(users, targetUser);
    }


    @Override
    public Optional<User> getUserById(List<User> users, String userId) {

        return AuthenticationFunctionUtils.getUserById.apply(users, userId);

    }

    @Override
    //Get all followers of User
    public List<User> getFollowersList(List<User> users, User targetUser) {
        //A has a followings list contains the targetUser
        //B has a followings list contains the targetUser
        //return A, B
        return RelationFunctionUtils.getFollowersList.apply(users, targetUser);
    }

    @Override
    public List<User> getFriendsList(User targetUser) {
        return RelationFunctionUtils.getFriendsList.apply(targetUser);
    }

    @Override
    public List<RelationshipInfo> getRequestedFriends(User targetUser) {
//        return RelationFunctionUtils.getRequestedFriends.apply(targetUser);
        return null;
    }

    @Override
    public List<User> getFollowingsList(User targetUser) {
        return RelationFunctionUtils.getFollowingsList.apply(targetUser);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////For Post


    @Override
    public List<Post> getTimeline(List<User> users, User targetUser) {
        //return all posts of all followings of the targetUser
        //and all posts of the targetUser
        //and all posts of all friends of the targetUser
        //and all posts of all users who the targetUser is their friends
        //A following B, C, D
        //A has list of friends D, E, F
        //Result: list of posts of A, [B, C, D], [D,E, F]
        return
                PostFunctionUtils.getTimeline.apply(users, targetUser);
    }

    @Override
    public List<PostInfo> getTimelinePostInfo(List<User> users, User targetUser)
    {
//        return PostFunctionUtils.convertToListPostInfo.apply(PostFunctionUtils.getTimeline.apply(users, targetUser));
        return null;
    }


    @Override
    public List<Post> getTimelineForPaging(List<User> users, User targetUser, int m, int n) {

        return PostFunctionUtils.getTimelineForPaging.apply(users, targetUser, m, n);
    }
}
