package com.mum.web.functional;

import com.mum.web.dtos.RelationshipInfo;
import com.mum.web.entities.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RelationFunctionUtils {

    @FunctionalInterface
    public interface TriFunction<S, T, U, R> {
        R apply(S s, T t, U u);
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


    public static BiFunction<User, User, Relationship> relationshipBiFunction =
            (user1, user2) ->
                    new Relationship(user1, user2,
                            user1.getFriends().stream()
                                    .filter(user -> user2.getFriends().contains(user))
                                    .collect(Collectors.toList()));

    //Return list of users, who
    //not belong to list of friends of the targetUser
    //is friend of friends of the targetUser
    //A has a list of friends B, C
    //B has a list of friends C, D, E
    //C has a list of friends D, F, G
    //Result: D, E, F, G
    public static BiFunction<List<User>, User, List<Relationship>> getSuggestionsList =
            (users, targetUser) ->
                    users.stream()
                            .filter(user -> targetUser.getFriends().contains(user))//return B, C
                            .flatMap(user -> user.getFriends().stream())//return C, D, E, D, F, G
                            .filter(user -> !targetUser.getFriends().contains(user))//return D, E, F, G
                            .filter(user -> user != targetUser)
                            .distinct()
                            .map(user -> relationshipBiFunction.apply(user, targetUser))
                            .sorted(Comparator.comparing(Relationship::getSize, Comparator.reverseOrder()).thenComparing(Relationship::getLastName))
                            .collect(Collectors.toList());

    public static BiFunction<List<User>, User, List<RelationshipInfo>> getAllSuggestedUserList =
            (users, targetUser) ->
                    getSuggestionsList.apply(users, targetUser)
                            .stream().map(r -> r.convertToInfo())
                            .distinct()
                            .collect(Collectors.toList());

    public static BiFunction<List<User>, User, List<RelationshipInfo>> getSuggestedUserList =
            (users, targetUser) ->
                    Stream.concat(
                            users.stream()
                                    .filter(user ->
                                            RelationFunctionUtils.getFriendsList.apply(targetUser).stream().filter(user1 -> user1.equals(user)).count() <= 0
                                            /*
                                            Stream.concat(
                                                    //RelationFunctionUtils.getFriendsList.apply(targetUser).stream(),
                                                    RelationFunctionUtils.getRequestedFriends.apply(targetUser).stream(),
                                                    RelationFunctionUtils.getFriendsList.apply(targetUser).stream()).filter(user1 -> user1.equals(user)).count() <= 0
                                                    //RelationFunctionUtils.getFollowingsList.apply(targetUser).stream()).filter(user1 -> user1.equals(user)).count() <= 0

                                            */
                                                    && !user.equals(targetUser)).map(user -> relationshipBiFunction.apply(user, targetUser))
                                    .distinct(),
                            RelationFunctionUtils.getSuggestionsList.apply(users, targetUser).stream().distinct()
                    )
                            .filter(e -> RelationFunctionUtils.getRequestedFriends.apply(targetUser).stream()
                                    .filter(e2 -> e.equals(e2)).count() <= 0)
                            .filter(distinctByKey(r -> r.getA().getEmail()))
                            .distinct()
                            .sorted(Comparator.comparing(Relationship::getSize, Comparator.reverseOrder()).thenComparing(Relationship::getLastName))
                            .map(r -> r.convertToInfo())
                            .distinct()
                            .collect(Collectors.toList());

    //A has a followings list contains the targetUser
    //B has a followings list contains the targetUser
    //return A, B
    public static BiFunction<List<User>, User, List<User>> getFollowersList =
            (users, targetUser) -> users.stream()
                    .filter(u -> u.getFollowings().contains(targetUser))
                    .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                    .collect(Collectors.toList());


    public static Function<User, List<User>> getFriendsList = (targetUser)
            -> targetUser.getFriends()
            .stream()
            .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
            .collect(Collectors.toList());

    public static Function<User, List<RelationshipInfo>> getRequestedFriends = (targetUser)
            -> targetUser.getRequestedFriends()
            .stream().map(user -> RelationFunctionUtils.relationshipBiFunction.apply(user, targetUser))
            .sorted(Comparator.comparing(Relationship::getSize, Comparator.reverseOrder()).thenComparing(Relationship::getLastName))
            .map(r -> r.convertToInfo())
            .collect(Collectors.toList());


    public static Function<User, List<User>> getFollowingsList = (targetUser)
            -> targetUser.getFollowings()
            .stream()
            .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
            .collect(Collectors.toList());

    public static Function<User, List<User>> getWaitngsList = (targetUser)
            -> targetUser.getWatings()
            .stream()
            .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
            .collect(Collectors.toList());


    public static TriFunction<List<Relation>, RelationType, RelationStatus, List<User>> getRelationUserList =
            (relations, relationType, relationStatus) ->
                    relations.stream()
                            .filter(relation -> relation.getRelationType() == relationType
                                    && relation.getRelationStatus() == relationStatus)
                            .map(relation -> relation.getUser())
                            .sorted(Comparator.comparing(User::getLastName).thenComparing(User::getFirstName))
                            .collect(Collectors.toList());


}
