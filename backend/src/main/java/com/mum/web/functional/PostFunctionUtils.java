package com.mum.web.functional;

import com.mum.web.entities.*;
import com.mum.web.dtos.CommentInfo;
import com.mum.web.dtos.PostInfo;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PostFunctionUtils {

    @FunctionalInterface
    public interface PentaFunction<S,T,U,V,R> {
        R apply(S s, T t, U u,V v);
    }


    public static BiFunction<List<Interaction>, InteractionType, List<User>> getInteractionUserList
            = (interactions, interactionType) ->
            interactions.stream()
                    .filter(interaction -> interaction.getInteractionType() == interactionType)
                    .map(interaction -> interaction.getUser())
                    .collect(Collectors.toList());

    //return all posts of all followings of the targetUser
    //and all posts of the targetUser
    //and all posts of all friends of the targetUser
    //and all posts of all users who the targetUser is their friends
    //A following B, C, D
    //A has list of friends D, E, F
    //Result: list of posts of A, [B, C, D], [D,E, F]
    public static BiFunction<List<User>, User, List<Post>> getTimeline =
            (users, targetUser) -> //B, C, D; and D, E, F; and A
                    users.stream()
                            .filter(u -> targetUser.getFollowings().contains(u)//return all followings of the targetUser
                                    || targetUser.getFriends().contains(u)//return all friends of the targetUser
                                    || u.equals(targetUser))
                            .distinct()//remove duplicate user D
                            //Posts of B, C, D, E, F and A
                            .flatMap(user -> user.getPosts().stream())
                            .sorted(Comparator.comparing(Post::getPostedDate, Comparator.reverseOrder()))
                            .collect(Collectors.toList());




    public static PentaFunction<List<User>, User,Integer,Integer,List<Post>> getTimelineForPaging =
            (users, targetUser,m,n) ->getTimeline.apply(users,targetUser).stream().skip(m).limit(n - m + 1).collect(Collectors.toList());

// TODO [QUY]
//    public static Function<Comment, CommentInfo> convertToCommentInfo =
//            (comment -> new CommentInfo(comment.getCommentId(),comment.getContent(),
//                    comment.getPostedDate(),AuthenticationFunctionUtils.convertToUserInfo.apply(comment.getUser())
//                    ,comment.getLikeCount(),comment.getLoveCount()
//                    ,AuthenticationFunctionUtils.converToListUserInfo.apply(comment.getLikeUserList())
//                    ,AuthenticationFunctionUtils.converToListUserInfo.apply(comment.getLoveUserList())
//            //));
//                    ,PostFunctionUtils.convertToListCommentInfo.apply(comment.getComments())));
//    public static Function<List<Comment>,List<CommentInfo>> convertToListCommentInfo=
//            (comments -> comments.stream()
//                    .map(comment -> convertToCommentInfo.apply(comment)).collect(Collectors.toList()));
//    public static Function<Post, PostInfo> convertToPostInfo =
//            (post -> post.getPostInfo());
//    public static Function<List<Post>,List<PostInfo>> convertToListPostInfo=
//            (posts -> posts.stream()
//            .map(post -> convertToPostInfo.apply(post)).collect(Collectors.toList()));

    public static BiFunction<Long, List<User>, Optional<Post>> getCommentByPostId = (postId, users) -> users.stream()
            .flatMap(u -> u.getPosts().stream())
            .filter(p -> p.getPostId().equals(postId)).findFirst();

}
