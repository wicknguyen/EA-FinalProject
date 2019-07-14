package com.mum.web.services;

import com.mum.web.dtos.*;
import com.mum.web.entities.*;
import com.mum.web.functional.AuthenticationFunctionUtils;
import com.mum.web.functional.PostFunctionUtils;
import com.mum.web.functional.RelationFunctionUtils;
import com.mum.web.provider.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SocialNetworkService implements ISocialNetworkService {


    @Autowired
    private DataProvider dataProvider;

    @Override
    public void createPost(PostInfo postInfo) {

        List<User> users = dataProvider.getUsers();

        User currentUser = new User();
        boolean bfind = false;
        for (User user : users) {
            if (user.getEmail().equals(postInfo.getPostedBy().getEmail())) {
                currentUser = user;
                bfind = true;
                break;
            }

        }
        if (bfind) {
            Post post = new Post();
            post.setContent(postInfo.getContent());
            post.setPostedDate(LocalDateTime.now());
            //post.setPostedDate(postInfo.getPostedDate());

            post.setUser(currentUser);
            post.generatePostId();
            currentUser.addPost(post);

        }
    }

    @Override
    public void addFriend(User targetUser, User friend) {
        targetUser.addFriend(friend);
        friend.addFriend(targetUser); //?

        //TODO[quy] add to data provider
    }

    public User acceptFriend(RelationshipInfo relationshipInfo) {
        List<User> users = dataProvider.getUsers();

        User currentUser = new User(), followUser = new User();


        int count = 0;

        for (User user : users) {
            if (user.getEmail().equals(relationshipInfo.getB().getEmail())) {
                currentUser = user;
                count++;

            }
            if (user.getEmail().equals(relationshipInfo.getA().getEmail())) {
                followUser = user;
                count++;

            }
            if (count == 2) break;


        }
        if (count == 2) {

            currentUser.acceptFriend(followUser);
            followUser.updateWatingFriend(currentUser);

            //currentUser.addFriend(followUser);
            //followUser.addFriend(currentUser);
            //currentUser.addWaitingFriend(followUser);
            //followUser.addRequestedFriend(currentUser);


            dataProvider.setUsers(users);
        }

        return currentUser;


    }

    public User rejectFriend(RelationshipInfo relationshipInfo) {
        List<User> users = dataProvider.getUsers();

        //User currentUser = new User(), followUser = new User();
/*

        int count = 0;

        for (User user : users) {
            if (user.getEmail().equals(relationshipInfo.getB().getEmail())) {
                currentUser = user;
                count++;

            }
            if (user.getEmail().equals(relationshipInfo.getA().getEmail())) {
                followUser = user;
                count++;

            }
            if (count == 2) break;


        }
        */

        User currentUser = AuthenticationFunctionUtils.getUserByMail.apply(relationshipInfo.getB().getEmail(),users).get();
        User followUser = AuthenticationFunctionUtils.getUserByMail.apply(relationshipInfo.getA().getEmail(),users).get();


//        if (count == 2) {


            currentUser.rejectFriend(followUser,RelationStatus.REQUESTED);
            followUser.rejectFriend(currentUser,RelationStatus.WAITING);

            //currentUser.acceptFriend(followUser);
            //followUser.updateWatingFriend(currentUser);

            //currentUser.addFriend(followUser);
            //followUser.addFriend(currentUser);
            //currentUser.addWaitingFriend(followUser);
            //followUser.addRequestedFriend(currentUser);


            //dataProvider.setUsers(users);
  //      }

        return currentUser;


    }

    public User makeFriend(RelationshipInfo relationshipInfo) {
        List<User> users = dataProvider.getUsers();

        User currentUser = new User(), followUser = new User();


        int count = 0;

        for (User user : users) {
            if (user.getEmail().equals(relationshipInfo.getB().getEmail())) {
                currentUser = user;
                count++;

            }
            if (user.getEmail().equals(relationshipInfo.getA().getEmail())) {
                followUser = user;
                count++;

            }
            if (count == 2) break;


        }
        if (count == 2) {
            //currentUser.addFriend(followUser);
            //followUser.addFriend(currentUser);
            currentUser.addWaitingFriend(followUser);
            followUser.addRequestedFriend(currentUser);
            dataProvider.setUsers(users);
        }

        return currentUser;


    }

    public void followUser(RelationshipInfo relationshipInfo) {
        List<User> users = dataProvider.getUsers();

        User currentUser = new User(), followUser = new User();


        int count = 0;

        for (User user : users) {
            if (user.getEmail().equals(relationshipInfo.getB().getEmail())) {
                currentUser = user;
                count++;

            }
            if (user.getEmail().equals(relationshipInfo.getA().getEmail())) {
                followUser = user;
                count++;

            }
            if (count == 2) break;


        }
        if (count == 2) {
            currentUser.addFollowing(followUser);
            dataProvider.setUsers(users);
        }


    }

    @Override
    public void addFollowing(User targetUser, User following) {
        targetUser.addFollowing(following);
        //TODO[quy] add to data provider

    }

    @Override
    public void updateCommentOfPost(PostInfo postInfo) {
        CommentInfo commentInfo = postInfo.getCommentInfos().iterator().next();
        Comment newComment = new Comment();
        newComment.setContent(commentInfo.getContent());
        newComment.setPostedDate(LocalDateTime.now());
        List<User> allUsers = dataProvider.getUsers();
        newComment.setPost(PostFunctionUtils.getCommentByPostId.apply(postInfo.getPostId(), allUsers).get());
        String userLoginEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        newComment.setUser(AuthenticationFunctionUtils.getUserByMail.apply(userLoginEmail, allUsers).get());
        newComment.generateCommentId();

        Optional<Post> optionalPost = PostFunctionUtils.getCommentByPostId.apply(postInfo.getPostId(), allUsers);
        optionalPost.get().getComments().add(newComment);
    }

    @Override
    public Comment updateLikeCommentOfPost(CommentInfo commentInfo) {
        List<User> allUsers = dataProvider.getUsers();
        Optional<Comment> optionalComment = allUsers.stream()
                .flatMap(u -> u.getPosts().stream())
                .flatMap(p -> p.getComments().stream())
                .filter(p -> p.getCommentId().equals(commentInfo.getCommentId())).findFirst();
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<UserInfo> userInfoList = commentInfo.getLikeUsers().stream()
                    .filter(u -> email.equals(u.getEmail())).collect(Collectors.toList());
            List<Interaction> interactionList = comment.getInteractions().stream()
                    .filter(u -> email.equals(u.getUser().getEmail())
                            && u.getInteractionType() == InteractionType.LIKE).collect(Collectors.toList());

            if (userInfoList.isEmpty() && !interactionList.isEmpty()) {
                comment.getInteractions().removeAll(interactionList);
            } else if (!userInfoList.isEmpty() && interactionList.isEmpty()) {
                Interaction interaction = createInteraction(allUsers, email, comment.getPost());
                interaction.setComment(comment);
                comment.getInteractions().add(interaction);
            }
            return comment;
        }
        return null;
    }

    private Interaction createInteraction(List<User> allUsers, String email, Post post) {
        Interaction interaction = new Interaction();
        interaction.setUser(AuthenticationFunctionUtils.getUserByMail.apply(email, allUsers).get());
        interaction.setInteractionType(InteractionType.LIKE);
        interaction.setActionDate(LocalDateTime.now());
        interaction.setPost(post);
        interaction.generateInteractionId();
        return interaction;
    }

    @Override
    public Post updateLikePost(PostInfo postInfo) {
        List<User> allUsers = dataProvider.getUsers();
        Optional<Post> optionalPost = allUsers.stream()
                .flatMap(u -> u.getPosts().stream())
                .filter(p -> p.getPostId().equals(postInfo.getPostId())).findFirst();
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<UserInfo> userInfoList = postInfo.getLikeUsers().stream()
                    .filter(u -> email.equals(u.getEmail())).collect(Collectors.toList());
            List<Interaction> interactionList = post.getInteractions().stream()
                    .filter(u -> email.equals(u.getUser().getEmail())
                            && u.getInteractionType() == InteractionType.LIKE).collect(Collectors.toList());

            if (userInfoList.isEmpty() && !interactionList.isEmpty()) {
                post.getInteractions().removeAll(interactionList);
            } else if (!userInfoList.isEmpty() && interactionList.isEmpty()) {
                Interaction interaction = createInteraction(allUsers, email, post);
                post.getInteractions().add(interaction);
            }
            return post;
        }
        return null;
    }

    @Override
    public PostInfo loadPostById(String postId) {
        Optional<Post> optionalPost = PostFunctionUtils.getCommentByPostId.apply(postId, dataProvider.getUsers());
        Post post = optionalPost.get();
        return post.getPostInfo();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    public List<Post> getMyPost(User targetUser) {
        return targetUser.getPosts();
    }


    ///////////////////////////////////////////////////////////////
    //Update Data


    @Override
    public List<Post> addPost(User targetUser, Post post) {
        targetUser.addPost(post);

        //TODO[quy] add a post to current user to data provider

        return targetUser.getPosts();
    }


    ///////////

    @Override
    public List<UserInfo> getFriendListByUserEmail(String email) {

        Optional<User> user = AuthenticationFunctionUtils.getUserByMail.apply(email, dataProvider.getUsers());

        if (!user.isPresent()) return null;

        List<User> users = RelationFunctionUtils.getFriendsList.apply(user.get());

        return AuthenticationFunctionUtils.converToListUserInfo.apply(users);


    }

    @Override

    public List<UserInfo> getFollowingListByUserEmail(String email) {

        Optional<User> user = AuthenticationFunctionUtils.getUserByMail.apply(email, dataProvider.getUsers());

        if (!user.isPresent()) return null;

        List<User> users = RelationFunctionUtils.getFollowingsList.apply(user.get());

        return AuthenticationFunctionUtils.converToListUserInfo.apply(users);
    }

    @Override

    public List<RelationshipInfo> getRequestedFriendListByUserEmail(String email) {

        Optional<User> user = AuthenticationFunctionUtils.getUserByMail.apply(email, dataProvider.getUsers());

        if (!user.isPresent()) return null;

        List<RelationshipInfo> users = RelationFunctionUtils.getRequestedFriends.apply(user.get());

        return users;//AuthenticationFunctionUtils.converToListUserInfo.apply(users);
    }

    public List<RelationshipInfo> getSuggestedFriendListByUserEmail(String email) {
        Optional<User> user = AuthenticationFunctionUtils.getUserByMail.apply(email, dataProvider.getUsers());

        if (!user.isPresent()) return null;

        List<RelationshipInfo> users = RelationFunctionUtils.getSuggestedUserList.apply(dataProvider.getUsers(), user.get());

        return users;

    }


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
        return RelationFunctionUtils.getRequestedFriends.apply(targetUser);
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
    public List<PostInfo> getTimelinePostInfoByUserId(String userId) {
        Optional<User> user = AuthenticationFunctionUtils.getUserById.apply(dataProvider.getUsers(), userId);

        if (!user.isPresent()) return null;

        List<Post> posts = PostFunctionUtils.getTimeline.apply(dataProvider.getUsers(), user.get());

        return PostFunctionUtils.convertToListPostInfo.apply(posts);

        //return PostFunctionUtils.convertToListPostInfo.apply(PostFunctionUtils.getTimeline.apply(users, targetUser));
    }


    @Override
    public List<Post> getTimelineForPaging(List<User> users, User targetUser, int m, int n) {

        return PostFunctionUtils.getTimelineForPaging.apply(users, targetUser, m, n);
    }


    @Override
    public TimelineInfo getTimelineInfoByUserEmail(String email) {
        TimelineInfo timelineInfo = new TimelineInfo();
        Optional<User> user = AuthenticationFunctionUtils.getUserByMail.apply(email, dataProvider.getUsers());

        if (!user.isPresent()) return null;


        UserInfo myProfile = AuthenticationFunctionUtils.convertToUserInfo.apply(user.get());


        List<Post> posts = PostFunctionUtils.getTimeline.apply(dataProvider.getUsers(), user.get());

        List<PostInfo> postInfos = PostFunctionUtils.convertToListPostInfo.apply(posts);

        List<UserInfo> friends = AuthenticationFunctionUtils.converToListUserInfo.apply(RelationFunctionUtils.getFriendsList.apply(user.get()));

        List<UserInfo> followings = AuthenticationFunctionUtils.converToListUserInfo.apply(RelationFunctionUtils.getFollowingsList.apply(user.get()));

        List<RelationshipInfo> requestedFriends =RelationFunctionUtils.getRequestedFriends.apply(user.get());

        List<RelationshipInfo> suggestedFriends = RelationFunctionUtils.getSuggestedUserList.apply(dataProvider.getUsers(), user.get());

        List<UserInfo> waitingriends = AuthenticationFunctionUtils.converToListUserInfo.apply(RelationFunctionUtils.getWaitngsList.apply(user.get()));


        timelineInfo.setMyProfile(myProfile);
        timelineInfo.setFriends(friends);
        timelineInfo.setFollowings(followings);
        timelineInfo.setRequestedFriends(requestedFriends);
        timelineInfo.setWaitingriends(waitingriends);
        timelineInfo.setSuggestedFriends(suggestedFriends);
        timelineInfo.setPosts(postInfos);


        return timelineInfo;

    }
}
