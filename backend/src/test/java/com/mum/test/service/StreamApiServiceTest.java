package com.mum.test.service;

import com.mum.web.dtos.PostInfo;
import com.mum.web.dtos.RelationshipInfo;
import com.mum.web.entities.Post;
import com.mum.web.entities.Relationship;
import com.mum.web.entities.User;
import com.mum.web.services.StreamAPIService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class StreamApiServiceTest extends com.mum.test.base.Test {

    @InjectMocks
    StreamAPIService streamAPIService;

    @Test
    public void getSuggestionsListTest() {
        List<Relationship> suggestedUsers = streamAPIService.getSuggestionsList(users, bao);
        assertNotNull(suggestedUsers);
    }

    @Test
    public void getFollowersListTest() {
        List<User> suggestedUsers = streamAPIService.getFollowersList(Arrays.asList(bao), bao);
        assertNotNull(suggestedUsers);
    }

    @Test
    public void getFriendsListTest() {
        List<User> target = streamAPIService.getFriendsList(bao);
        assertNotNull(target);
    }

    @Test
    public void getTimelineTest() {
        List<Post> target = streamAPIService.getTimeline(Arrays.asList(bao), bao);
        assertNotNull(target);
    }

    @Test
    public void getTimelineForPagingTest() {
        List<Post> posts = streamAPIService.getTimelineForPaging(Arrays.asList(bao), bao, 0, 1);
        assertNotNull(posts);
    }

    @Test
    public void getTimelinePostInfoTest() {
        List<PostInfo> posts = streamAPIService.getTimelinePostInfo(users, bao);
        assertNotNull(posts);
    }

    @Test
    public void getFollowingsListTest() {
        List<User> posts = streamAPIService.getFollowingsList(bao);
        assertNotNull(posts);
    }

    @Test
    public void getRequestedFriendsTest() {
        List<RelationshipInfo> posts = streamAPIService.getRequestedFriends(bao);
        assertNotNull(posts);
    }

    @Test
    public void getUserByIdTest() {
        Optional<User> posts = streamAPIService.getUserById(users, bao.getUserId());
        assertNotNull(posts.get());
    }
}
