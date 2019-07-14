package com.mum.test.service;

import com.mum.web.dtos.*;
import com.mum.web.entities.Post;
import com.mum.web.entities.Relationship;
import com.mum.web.entities.User;
import com.mum.web.provider.DataProvider;
import com.mum.web.services.SocialNetworkService;
import com.mum.web.services.StreamAPIService;
import jdk.nashorn.internal.runtime.options.Option;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SocialNetworkServiceTest extends com.mum.test.base.Test {

    @Mock
    private DataProvider dataProvider;

    @InjectMocks
    SocialNetworkService socialNetworkService;

    @Test
    public void addFriendTest() {
        socialNetworkService.addFriend(bao, quy);
        List<User> friends = socialNetworkService.getFriendsList(bao);

        assertNotNull(friends);
    }

    @Test
    public void addFollowingTest() {
        socialNetworkService.addFollowing(bao, tam);
        List<User> friends = socialNetworkService.getFollowingsList(bao);

        assertNotNull(friends);
    }


    @Test
    public void updateCommentOfPostTest() {
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(dataProvider.getUsers()).thenReturn(users);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("bao@mum.edu");
        PostInfo postInfo = createPostInfo();
        CommentInfo commentInfo = new CommentInfo();
        commentInfo.setContent("hic chan vl");
        postInfo.setCommentInfos(Arrays.asList(commentInfo));
        socialNetworkService.updateCommentOfPost(postInfo);
    }

    @Test
    public void loadPostByUserEmailTest() {
        // TODO[TAM] rewrite it
        when(dataProvider.getUsers()).thenReturn(users);
        PostInfo postInfo = socialNetworkService.loadPostById("bao@mum.edu");
        assertNotNull(postInfo);

    }

    @Test
    public void addPostTest() {
        List<Post> posts = socialNetworkService.addPost(bao, new Post());
        assertNotNull(posts);

    }

    @Test
    public void getFriendListByUserEmailTest() {
        when(dataProvider.getUsers()).thenReturn(users);
        List<UserInfo> userInfos = socialNetworkService.getFriendListByUserEmail("bao@mum.edu");
        assertNotNull(userInfos);

    }

    @Test
    public void getFollowingListByUserEmailTest() {
        when(dataProvider.getUsers()).thenReturn(users);
        List<UserInfo> userInfos = socialNetworkService.getFollowingListByUserEmail("bao@mum.edu");
        assertNotNull(userInfos);

    }

    @Test
    public void getRequestedFriendListByUserEmailTest() {
        when(dataProvider.getUsers()).thenReturn(users);
        List<RelationshipInfo> userInfos = socialNetworkService.getRequestedFriendListByUserEmail("bao@mum.edu");
        assertNotNull(userInfos);

    }

    @Test
    public void getSuggestedFriendListByUserEmailTest() {
        when(dataProvider.getUsers()).thenReturn(users);
        List<RelationshipInfo> userInfos = socialNetworkService.getSuggestedFriendListByUserEmail("bao@mum.edu");
        assertNotNull(userInfos);

    }


    @Test
    public void getSuggestionsListTest() {
        List<Relationship> userInfos = socialNetworkService.getSuggestionsList(users, bao);
        assertNotNull(userInfos);

    }

    @Test
    public void getTimelinePostInfoByUserIdTest() {
        when(dataProvider.getUsers()).thenReturn(users);
        List<PostInfo> userInfos = socialNetworkService.getTimelinePostInfoByUserId("bao@mum.edu");
        assertNotNull(userInfos);

    }

    @Test
    public void getTimelineInfoByUserEmailTest() {
        when(dataProvider.getUsers()).thenReturn(users);
        TimelineInfo timelineInfo = socialNetworkService.getTimelineInfoByUserEmail("bao@mum.edu");
        assertNotNull(timelineInfo);

    }

    @Test
    public void getUserByIdTest() {
        Optional<User> userInfos = socialNetworkService.getUserById(users, "bao@mum.edu");
        assertNotNull(userInfos.get());

    }

    @Test
    public void getTimelineForPagingTest() {
        List<Post> posts = socialNetworkService.getTimelineForPaging(users, bao, 0, 10);
        assertNotNull(posts);

    }

    @Test
    public void getTimelineTest() {
        List<Post> posts = socialNetworkService.getTimeline(users, bao);
        assertNotNull(posts);

    }

    @Test
    public void getRequestedFriendsTest() {
        List<RelationshipInfo> users = socialNetworkService.getRequestedFriends(bao);
        assertNotNull(users);

    }

    @Test
    public void getFollowersListTest() {
        List<User> friendUsers = socialNetworkService.getFollowersList(users, bao);
        assertNotNull(friendUsers);

    }

    @Test
    public void getMyPostTest() {
        List<Post> posts = socialNetworkService.getMyPost(bao);
        assertNotNull(posts);

    }

    @Test
    public void makeFriendTest() {
        when(dataProvider.getUsers()).thenReturn(users);
        UserInfo a = new UserInfo(bao.getUserId(), bao.getFullName(), bao.getAvatar(), bao.getEmail(), bao.getDob());
        UserInfo b = new UserInfo(quy.getUserId(), quy.getFullName(), quy.getAvatar(), quy.getEmail(), quy.getDob());
        List<UserInfo> users = new ArrayList<>();
        users.add(a);
        users.add(b);
        User user = socialNetworkService.makeFriend(new RelationshipInfo(a, b, users, 2, null));
        assertNotNull(user);

    }

    @Test
    public void followUserTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        socialNetworkService.followUser(createRelationshipInfo());
    }

    @Test
    public void createPostTest() {
        when(dataProvider.getUsers()).thenReturn(users);

        socialNetworkService.createPost(createPostInfo());
//        PostInfo postInfo = socialNetworkService.loadPostById("1011992");
//        assertNotNull(postInfo);
    }

    @Test
    public void updateLikeCommentOfPostTest() {
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("bao@mum.edu");
        when(dataProvider.getUsers()).thenReturn(users);
        CommentInfo commentInfo = createCommentInfo();
        socialNetworkService.updateLikeCommentOfPost(commentInfo);
    }

    @Test
    public void updateLikePostTest() {
        Authentication authentication = Mockito.mock(Authentication.class);
        // Mockito.whens() for your authorization object
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn("bao@mum.edu");
        when(dataProvider.getUsers()).thenReturn(users);
        Post post = socialNetworkService.updateLikePost(createPostInfo());
        assertNotNull(post);
    }
}
