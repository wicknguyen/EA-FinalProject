package com.mum.test.controller;

import com.mum.web.controller.SocialNetworkController;
import com.mum.web.dtos.*;
import com.mum.web.entities.Post;
import com.mum.web.entities.User;
import com.mum.web.functional.AuthenticationFunctionUtils;
import com.mum.web.provider.DataProvider;
import com.mum.web.services.ISocialNetworkService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SocialNetworkControllerTest extends com.mum.test.base.Test {

    @Mock
    private DataProvider dataProvider;

    @Mock
    public ISocialNetworkService socialNetworkService;

    @InjectMocks
    private SocialNetworkController socialNetworkController;

    @org.junit.Test
    public void getTestByIdTest() {
//        List<Post> tam = socialNetworkController.getTestById("tam");
        assertNotNull(tam);
    }

    @Test
    public void getUsersTest() {

        when(dataProvider.getUsers()).thenReturn(Arrays.asList(quy));
        UserInfo user = socialNetworkController.getUsers("quy@mum.edu");
        assertNotNull(user);
        assertEquals("quy@mum.edu", user.getEmail());
    }

    @Test
    public void loadCommentForPostTest() {
        PostInfo postInfo = socialNetworkController.loadCommentForPost("bao@mum.edu");
        assertNull(postInfo);
    }

    @Test
    public void loadCommentForPostInfoTest() {
        PostInfo postInfo = createPostInfo();
        PostInfo result = socialNetworkController.loadCommentForPost(postInfo);
        assertNull(result);
    }

    @Test
    public void getPostTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        List<Post> posts = socialNetworkController.getPost("bao@mum.edu");
        assertNotNull(posts);
    }

    @Test
    public void getPostInfoTest() {
        List<PostInfo> posts = socialNetworkController.getPostInfo("bao@mum.edu");
        assertNotNull(posts);
    }

    @Test
    public void testPostInfoTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        PostInfo posts = socialNetworkController.testPostInfo("bao@mum.edu");
        assertNotNull(posts);
    }

    @Test
    public void getInfoTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        List<UserInfo> posts = socialNetworkController.getInfo("bao@mum.edu");
        assertNotNull(posts);
    }

    @Test
    public void testCommentInfoTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        CommentInfo commentInfo = socialNetworkController.testCommentInfo("bao@mum.edu");
        assertNotNull(commentInfo);
    }

    @Test
    public void testCommentInfoListTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        List<CommentInfo> userInfos = socialNetworkController.testCommentInfoList("bao@mum.edu");
        assertNotNull(userInfos);
    }

    @Test
    public void userListTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        List<User> userList = socialNetworkController.getUsers();
        assertNotNull(userList);
    }

    @Test
    public void testFriendTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        List<UserInfo> userList = socialNetworkController.testFriend("bao@mum.edu");
        assertNull(userList);
    }

    @Test
    public void likeCommentOfPostTest() {
        PostInfo postInfo = socialNetworkController.likeCommentOfPost(createCommentInfo());
        assertNull(postInfo);
    }

    @Test
    public void getTimelineInfoByUserEmailTest() {
        TimelineInfo timelineInfo = socialNetworkController.getTimelineInfoByUserEmail("bao@mum.edu");
        assertNull(timelineInfo);
    }

    @Test
    public void followUserTest() {
        RelationshipInfo relationshipInfo = socialNetworkController.followUser(createRelationshipInfo());
        assertNull(relationshipInfo);
    }

    @Test
    public void makeFriendTest() {
        when(socialNetworkService.makeFriend(any())).thenReturn(bao);
        List<UserInfo> userInfos = socialNetworkController.makeFriend(createRelationshipInfo());
        assertNotNull(userInfos);
    }


    @Test
    public void likePostTest() {
        PostInfo postInfo = socialNetworkController.likePost(createPostInfo());
        assertNull(postInfo);
    }
}
