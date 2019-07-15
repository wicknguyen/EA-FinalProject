package com.mum.web.services;

import com.mum.web.dtos.*;
import com.mum.web.entities.Comment;
import com.mum.web.entities.Post;
import com.mum.web.entities.Relationship;
import com.mum.web.entities.User;

import java.util.List;
import java.util.Optional;

public interface ISocialNetworkService {

    void followUser(RelationshipInfo relationshipInfo);

    User makeFriend(RelationshipInfo relationshipInfo);

    User acceptFriend(RelationshipInfo relationshipInfo);

    User rejectFriend(RelationshipInfo relationshipInfo);

    void createPost(PostInfo postInfo);

    void updateCommentOfPost(PostInfo postInfo);

    PostInfo loadPostById(Long postId);

    Comment updateLikeCommentOfPost(CommentInfo commentInfo);

    Post updateLikePost(PostInfo postInfo);

    List<PostInfo> getTimelinePostInfoByUserId(String userId);

    TimelineInfo getTimelineInfoByUserEmail(String email);


}
