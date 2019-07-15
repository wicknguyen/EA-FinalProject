package com.mum.web.functional;

import com.mum.web.entities.Relation;
import com.mum.web.entities.RelationStatus;
import com.mum.web.entities.RelationType;
import com.mum.web.entities.User;
import com.mum.web.dtos.UserInfo;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AuthenticationFunctionUtils {

    @FunctionalInterface
    public interface PentaFunction<S, T, U, V, R> {
        R apply(S s, T t, U u, V v);
    }

    public static BiFunction<String, List<User>, Optional<User>> getUserByMail = (userName, users) ->
            users.stream().filter(user -> user.getEmail().equals(userName)).findFirst();

    public static BiFunction<String, List<User>, Boolean> isUserExisted = (email, users) ->
            users.stream().filter(u -> u.getEmail().equals(email)).findFirst().isPresent();


    public static BiFunction<List<User>, String, Optional<User>> getUserById = (users, userId) ->
            users.stream()
                    .filter(user -> user.getUserId().equals(userId))
                    .findFirst();

    public static BiFunction<List<User>, String, Optional<User>> getUserByEmail = (users, userEmail) ->
            users.stream()
                    .filter(user -> user.getEmail().equals(userEmail))
                    .findFirst();

    public static BiFunction<List<User>, Long, Optional<User>> getUserByUserId = (users, userId) ->
            users.stream()
                    .filter(user -> user.getUserId().equals(userId))
                    .findFirst();

    public static PentaFunction<List<Relation>, User, RelationType, RelationStatus, Optional<Relation>> getReleationByUser =
            (relations, user, relationType, relationStatus) ->
                    relations.stream()
                            .filter(relation -> relation.getUser().equals(user)
                                    && relation.getRelationType() == relationType
                                    && relation.getRelationStatus() == relationStatus)
                            .findFirst();

    public static Function<User, UserInfo> convertToUserInfo =
            (user) -> new UserInfo(user.getUserId(), user.getFullName(), user.getAvatar(), user.getEmail(), user.getDob());

    public static Function<List<User>, List<UserInfo>> converToListUserInfo =
            (users -> users.stream().map(convertToUserInfo).collect(Collectors.toList()));

}
