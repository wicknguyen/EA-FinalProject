package com.mum.web.services;

import com.mum.web.dtos.*;
import com.mum.web.entities.*;
import com.mum.web.functional.AuthenticationFunctionUtils;
import com.mum.web.functional.PostFunctionUtils;
import com.mum.web.functional.RelationFunctionUtils;
import com.mum.web.provider.DataProvider;
import com.mum.web.repositories.PostRepository;
import com.mum.web.repositories.UserRepository;
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
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    @Override
    public void createPost(PostInfo postInfo) {
        List<User> users = userRepository.findAll();
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
            currentUser.addPost(post);
        }
        userRepository.saveAll(users);
    }

    public User acceptFriend(RelationshipInfo relationshipInfo) {
        List<User> users = userRepository.findAll();
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
            userRepository.saveAll(users);
        }
        return currentUser;
    }

    public User rejectFriend(RelationshipInfo relationshipInfo) {
        List<User> users = userRepository.findAll();

        User currentUser = AuthenticationFunctionUtils.getUserByMail.apply(relationshipInfo.getB().getEmail(), users).get();
        User followUser = AuthenticationFunctionUtils.getUserByMail.apply(relationshipInfo.getA().getEmail(), users).get();

        currentUser.rejectFriend(followUser, RelationStatus.REQUESTED);
        followUser.rejectFriend(currentUser, RelationStatus.WAITING);

        userRepository.saveAll(users);

        return currentUser;

    }

    public User makeFriend(RelationshipInfo relationshipInfo) {
        List<User> users = userRepository.findAll();

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
            userRepository.saveAll(users);
        }

        return currentUser;

    }

    public void followUser(RelationshipInfo relationshipInfo) {
        List<User> users = userRepository.findAll();

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
            userRepository.saveAll(users);
        }
    }

    @Override
    public void updateCommentOfPost(PostInfo postInfo) {
        if (postInfo.getCommentInfos().size() > 0) {
            CommentInfo commentInfo = postInfo.getCommentInfos().iterator().next();
            Comment newComment = new Comment();
            newComment.setContent(commentInfo.getContent());
            newComment.setPostedDate(LocalDateTime.now());
            List<User> allUsers = userRepository.findAll();
            newComment.setPost(PostFunctionUtils.getCommentByPostId.apply(postInfo.getPostId(), allUsers).get());
            String userLoginEmail = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            newComment.setUser(AuthenticationFunctionUtils.getUserByMail.apply(userLoginEmail, allUsers).get());

            Optional<Post> optionalPost = PostFunctionUtils.getCommentByPostId.apply(postInfo.getPostId(), allUsers);
            optionalPost.get().getComments().add(newComment);

            userRepository.saveAll(allUsers);
        }
    }

    @Override
    public Comment updateLikeCommentOfPost(CommentInfo commentInfo) {
        List<User> allUsers = userRepository.findAll();
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
            userRepository.saveAll(allUsers);
            return comment;
        }
        userRepository.saveAll(allUsers);
        return null;
    }

    private Interaction createInteraction(List<User> allUsers, String email, Post post) {
        Interaction interaction = new Interaction();
        interaction.setUser(AuthenticationFunctionUtils.getUserByMail.apply(email, allUsers).get());
        interaction.setInteractionType(InteractionType.LIKE);
        interaction.setActionDate(LocalDateTime.now());
        interaction.setPost(post);

        userRepository.saveAll(allUsers);
        return interaction;
    }

    @Override
    public Post updateLikePost(PostInfo postInfo) {
        List<User> allUsers = userRepository.findAll();
        Optional<Post> optionalPost = postRepository.findById(postInfo.getPostId());
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
            userRepository.saveAll(allUsers);
            return post;
        }
        userRepository.saveAll(allUsers);
        return null;
    }

    @Override
    public PostInfo loadPostById(Long postId) {
        Optional<Post> optionalPost = PostFunctionUtils.getCommentByPostId.apply(postId, userRepository.findAll());
        PostInfo postInfo = null;
        if (optionalPost.isPresent()) {
            postInfo = optionalPost.get().getPostInfo();
        }
        return postInfo;
    }

    @Override
    public List<PostInfo> getTimelinePostInfoByUserId(String userId) {
        Optional<User> user = AuthenticationFunctionUtils.getUserById.apply(userRepository.findAll(), userId);
        if (!user.isPresent()) return null;
        List<Post> posts = PostFunctionUtils.getTimeline.apply(userRepository.findAll(), user.get());
        return PostFunctionUtils.convertToListPostInfo.apply(posts);

        //return PostFunctionUtils.convertToListPostInfo.apply(PostFunctionUtils.getTimeline.apply(users, targetUser));
    }

    @Override
    public TimelineInfo getTimelineInfoByUserEmail(String email) {
        TimelineInfo timelineInfo = new TimelineInfo();
        Optional<User> user = AuthenticationFunctionUtils.getUserByMail.apply(email, userRepository.findAll());
        if (!user.isPresent()) return null;
        UserInfo myProfile = AuthenticationFunctionUtils.convertToUserInfo.apply(user.get());
        List<Post> posts = PostFunctionUtils.getTimeline.apply(userRepository.findAll(), user.get());
        List<PostInfo> postInfos = PostFunctionUtils.convertToListPostInfo.apply(posts);
        List<UserInfo> friends = AuthenticationFunctionUtils.converToListUserInfo.apply(RelationFunctionUtils.getFriendsList.apply(user.get()));
        List<UserInfo> followings = AuthenticationFunctionUtils.converToListUserInfo.apply(RelationFunctionUtils.getFollowingsList.apply(user.get()));
        List<RelationshipInfo> requestedFriends = RelationFunctionUtils.getRequestedFriends.apply(user.get());
        List<RelationshipInfo> suggestedFriends = RelationFunctionUtils.getSuggestedUserList.apply(userRepository.findAll(), user.get());
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
