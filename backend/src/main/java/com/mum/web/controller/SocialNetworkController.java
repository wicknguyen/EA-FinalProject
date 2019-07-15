package com.mum.web.controller;

import com.mum.web.dtos.*;
import com.mum.web.entities.*;
import com.mum.web.functional.AuthenticationFunctionUtils;
import com.mum.web.functional.PostFunctionUtils;
import com.mum.web.functional.RelationFunctionUtils;
import com.mum.web.provider.DataProvider;
import com.mum.web.services.ISocialNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/**")
public class SocialNetworkController {
    @Autowired
    private DataProvider dataProvider;
    @Autowired
    private ISocialNetworkService socialNetworkService;

    @GetMapping("/testGetUsers")
    public List<User> getUsers() {
        return dataProvider.getUsers();
    }

    @GetMapping("/user/{email}")
    public UserInfo getUsers(@PathVariable String email) {
        Optional<User> user = AuthenticationFunctionUtils.getUserByMail.apply(email, dataProvider.getUsers());
        return user.isPresent() ? AuthenticationFunctionUtils.convertToUserInfo.apply(user.get()) : null;
    }

    @GetMapping("comment/{postId}")
    public PostInfo loadCommentForPost(@PathVariable Long postId) {
        return socialNetworkService.loadPostById(postId);
    }

    @PostMapping("comment")
    public PostInfo loadCommentForPost(@RequestBody PostInfo postInfo) {
        socialNetworkService.updateCommentOfPost(postInfo);
        return socialNetworkService.loadPostById(postInfo.getPostId());
    }

    @PostMapping("like-comment")
    public PostInfo likeCommentOfPost(@RequestBody CommentInfo commentInfo) {
        Comment comment = socialNetworkService.updateLikeCommentOfPost(commentInfo);
        if (comment == null) {
            return null;
        }
        return socialNetworkService.loadPostById(comment.getPost().getPostId());
    }

    @PostMapping("like-post")
    public PostInfo likePost(@RequestBody PostInfo postInfo) {
        Post post = socialNetworkService.updateLikePost(postInfo);
        if (post == null) {
            return null;
        }
        return socialNetworkService.loadPostById(post.getPostId());
    }

    @GetMapping("timeline/{email}")
    public TimelineInfo getTimelineInfoByUserEmail(@PathVariable String email) {
        return socialNetworkService.getTimelineInfoByUserEmail(email);
    }

    @PostMapping("/followUser")
    public RelationshipInfo followUser(@RequestBody RelationshipInfo relationshipInfo) {
        System.out.println(relationshipInfo);
        socialNetworkService.followUser(relationshipInfo);
        return null;
        //socialNetworkService.updateLikeCommentOfPost(RelationshipInfo);
        //return socialNetworkService.loadPostByUserEmail(postInfo.getPostedBy().getEmail());
    }

    @PostMapping("/makeFriend")
    public List<UserInfo> makeFriend(@RequestBody RelationshipInfo relationshipInfo) {
        System.out.println(relationshipInfo);
        User user = socialNetworkService.makeFriend(relationshipInfo);
        List<UserInfo> userInfos = AuthenticationFunctionUtils.converToListUserInfo.apply(RelationFunctionUtils.getFriendsList.apply(user));
        return userInfos;
        //return null;
        //socialNetworkService.updateLikeCommentOfPost(RelationshipInfo);
        //return socialNetworkService.loadPostByUserEmail(postInfo.getPostedBy().getEmail());
    }

    @PostMapping("/acceptFriend")
    public List<UserInfo> acceptFriend(@RequestBody RelationshipInfo relationshipInfo) {
        System.out.println(relationshipInfo);
        User user = socialNetworkService.acceptFriend(relationshipInfo);
        List<UserInfo> userInfos = AuthenticationFunctionUtils.converToListUserInfo.apply(RelationFunctionUtils.getFriendsList.apply(user));
        return userInfos;
        //socialNetworkService.updateLikeCommentOfPost(RelationshipInfo);
        //return socialNetworkService.loadPostByUserEmail(postInfo.getPostedBy().getEmail());
    }

    @PostMapping("/rejectFriend")
    public List<UserInfo> rejectFriend(@RequestBody RelationshipInfo relationshipInfo) {
        System.out.println(relationshipInfo);
        User user = socialNetworkService.rejectFriend(relationshipInfo);
        List<UserInfo> userInfos = AuthenticationFunctionUtils.converToListUserInfo.apply(RelationFunctionUtils.getFriendsList.apply(user));
        return userInfos;
        //socialNetworkService.updateLikeCommentOfPost(RelationshipInfo);
        //return socialNetworkService.loadPostByUserEmail(postInfo.getPostedBy().getEmail());
    }

    @PostMapping("/post")
    public void createPost(@RequestBody PostInfo postInfo) {
        socialNetworkService.createPost(postInfo);
        System.out.println(postInfo);
    }

    @GetMapping("/testGetPost/{userId}")
    public List<Post> getPost(@PathVariable String userId) {
        Optional<User> user = AuthenticationFunctionUtils.getUserById.apply(dataProvider.getUsers(), userId);
        if (!user.isPresent()) return null;
        List<Post> posts = PostFunctionUtils.getTimeline.apply(dataProvider.getUsers(), user.get());
        return posts;

        //return PostFunctionUtils.convertToListPostInfo.apply( posts);
    }

    @GetMapping("/testGetPostInfo/{userId}")
    public List<PostInfo> getPostInfo(@PathVariable String userId) {
        return socialNetworkService.getTimelinePostInfoByUserId(userId);
    }

    @GetMapping("/testPostInfo/{userId}")
    public PostInfo testPostInfo(@PathVariable String userId) {
        Optional<User> user = AuthenticationFunctionUtils.getUserById.apply(dataProvider.getUsers(), userId);
        if (!user.isPresent()) return null;
        List<Post> posts = PostFunctionUtils.getTimeline.apply(dataProvider.getUsers(), user.get());
        PostInfo postInfo = new PostInfo(123L, "456", LocalDateTime.now()
                , AuthenticationFunctionUtils.convertToUserInfo.apply(user.get())
                , 1, 1, null, null, null);
        return postInfo;
    }

    @GetMapping("/testGetUserInfo/{userId}")
    public List<UserInfo> getInfo(@PathVariable String userId) {
        Optional<User> user = AuthenticationFunctionUtils.getUserById.apply(dataProvider.getUsers(), userId);
        if (!user.isPresent()) return null;
        UserInfo userInfo = AuthenticationFunctionUtils.convertToUserInfo.apply(user.get());
        return AuthenticationFunctionUtils.converToListUserInfo.apply(dataProvider.getUsers());
    }

    @GetMapping("/testCommentInfo/{userId}")
    public CommentInfo testCommentInfo(@PathVariable String userId) {
        Optional<User> user = AuthenticationFunctionUtils.getUserById.apply(dataProvider.getUsers(), userId);
        if (!user.isPresent()) return null;
        List<Post> posts = PostFunctionUtils.getTimeline.apply(dataProvider.getUsers(), user.get());

        CommentInfo postInfo = new CommentInfo(123L, "456", LocalDateTime.now()
                , AuthenticationFunctionUtils.convertToUserInfo.apply(user.get())
                , 1, 1, null, null, null);
        return postInfo;
    }

    @GetMapping("/testCommentInfoList/{userId}")
    public List<CommentInfo> testCommentInfoList(@PathVariable String userId) {
        Optional<User> user = AuthenticationFunctionUtils.getUserById.apply(dataProvider.getUsers(), userId);
        if (!user.isPresent()) return null;
        List<Post> posts = PostFunctionUtils.getTimeline.apply(dataProvider.getUsers(), user.get());
        Post post = posts.get(0);
        List<CommentInfo> commentInfos = PostFunctionUtils.convertToListCommentInfo.apply(post.getComments());
        return commentInfos;
    }


    @GetMapping("/testFriend/{userId}")
    public List<UserInfo> testFriend(@PathVariable String userId) {
        Optional<User> user = AuthenticationFunctionUtils.getUserById.apply(dataProvider.getUsers(), userId);
        if (!user.isPresent()) return null;
        List<User> users = user.get().getFriends();
        List<UserInfo> userInfos = null;
        return userInfos;
    }

}
