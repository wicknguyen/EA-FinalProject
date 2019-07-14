package com.mum.web.services;

import com.mum.web.dtos.*;
import com.mum.web.entities.Comment;
import com.mum.web.entities.Post;
import com.mum.web.entities.Relationship;
import com.mum.web.entities.User;

import java.util.List;
import java.util.Optional;

public interface ISocialNetworkService {


    // TODO[NQG] consider remove these.
    public void addFriend(User targetUser, User friend);
    public void addFollowing(User targetUser, User following);

    public void followUser(RelationshipInfo relationshipInfo);
    public User makeFriend(RelationshipInfo relationshipInfo);
    public User acceptFriend(RelationshipInfo relationshipInfo);
    public User rejectFriend(RelationshipInfo relationshipInfo);






    //For Post
    public List<Post> getMyPost(User targetUser);




    ///////////////////////////////////////////////////////////////
    //Update Data
    public List<Post> addPost(User targetUser, Post post);

    void createPost(PostInfo postInfo);


    void updateCommentOfPost(PostInfo postInfo);

    PostInfo loadPostById(String postId);

    Comment updateLikeCommentOfPost(CommentInfo commentInfo);

    Post updateLikePost(PostInfo postInfo);


    ////////////////////
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

    public List<PostInfo> getTimelinePostInfoByUserId(String userId);

    public List<UserInfo> getFriendListByUserEmail(String email);
    public List<UserInfo> getFollowingListByUserEmail(String email);
    public List<RelationshipInfo> getRequestedFriendListByUserEmail(String email);
    public List<RelationshipInfo> getSuggestedFriendListByUserEmail(String email);



    public TimelineInfo getTimelineInfoByUserEmail(String email);


}
