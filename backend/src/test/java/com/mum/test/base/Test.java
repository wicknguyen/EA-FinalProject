package com.mum.test.base;

import com.mum.web.dtos.CommentInfo;
import com.mum.web.dtos.PostInfo;
import com.mum.web.dtos.RelationshipInfo;
import com.mum.web.dtos.UserInfo;
import com.mum.web.entities.*;
import com.mum.web.functional.AuthenticationFunctionUtils;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class Test {

    public List<String> datas;
    public List<User> users;

    public PostInfo postInfo;

    public User bao;
    public User tam;
    public User quy;
    public User huy;
    public User bang;
    public User sang;
    public User tung;
    public User viet;

    @Before
    public void setup() {

        datas = new ArrayList<>();
        users = new ArrayList<>();

        initTestData();
        initFriend();

    }

    @org.junit.Test
    public void test() {

    }

    void initFriend() {
        bao = users.get(0);
        tam = users.get(1);
        quy = users.get(2);
        huy = users.get(3);
        bang = users.get(4);
        sang = users.get(5);
        tung = users.get(6);
        viet = users.get(7);
        //bao friend quy & bang, viet
        bao.addFriend(quy);

        bao.addFriend(bang);
        bao.addFriend(viet);

        //bao follow tam & huy
        bao.addFollowing(tam);
        bao.addFollowing(huy);

        //quy friend bao, sang
        quy.addFriend(bao);
        quy.addFriend(sang);
        //bang friend bao, sang, tung
        bang.addFriend(bao);
        bang.addFriend(sang);
        bang.addFriend(tung);


        sang.addFriend(bang);
        sang.addFriend(quy);

        tung.addFriend(bang);


    }

    public void initTestData() {
        String[] email = {"bao@mum.edu", "tam@mum.edu", "quy@mum.edu", "huy@mum.edu", "bang@mum.edu", "sang@mum.edu", "tung@mum.edu", "viet@mum.edu"};
        String[] firstName = {"Bao", "Tam", "Quy", "Huy", "Bang", "Sang", "Tung", "Viet"};
        String[] lastName = {"Tran", "Tan", "Nguyen", "Luong", "Le", "Truong", "Nguyen", "Phan"};
        String[] postContent = {"Who are we? We come from many places, cultures, and backgrounds, but share a strong common commitment to personal inner growth, wellness, sustainability, and positive values."
                , "MUM, a unique university in the Midwest for the unique students of the world."};

        String[] commentContent = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Ut sem.",
                "this is a comment 2"};

        String[] replyContent = {"this is reply 1",
                "This is reply 2"};


        DateTimeFormatter formatter
                = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.now();// LocalDateTime.parse("2018-12-30T19:34:50.63",formatter);

        for (int i = 0; i < email.length; i++) {

            User user = new User();
            //
            user.setUserId(email[i]);

            user.setEmail(email[i]);
            user.setFirstName(firstName[i]);
            user.setLastName(lastName[i]);
            user.setPassword("123456");
            user.setAvatar("100_2.jpg");
            users.add(user);

        }

        for (int i = 0; i < email.length; i++) {

            Post post = new Post();
            post.setPostId(email[i]);

            post.setContent(users.get(i).getFirstName() + " posted: " + postContent[i % 2]);
            post.setUser(users.get(i));
            post.setPostedDate(dateTime);

            Interaction interaction = new Interaction();
            interaction.setPost(post);
            interaction.setUser(users.get((i + 1) % email.length));
            interaction.setInteractionType(InteractionType.LIKE);
            dateTime = LocalDateTime.now();
            interaction.setActionDate(dateTime);
            post.addInteraction(interaction);
            interaction.setUser(users.get((i + 2) % email.length));
            post.addInteraction(interaction);
            interaction.setInteractionType(InteractionType.LOVE);
            interaction.setUser(users.get((i + 3) % email.length));
            post.addInteraction(interaction);
            interaction.setUser(users.get((i + 4) % email.length));
            post.addInteraction(interaction);


            Comment comment = new Comment();
            comment.setUser(users.get((i + 1) % email.length));
            comment.setContent(users.get((i + 1) % email.length) + " commented: " + commentContent[i % 2]);
            comment.setCommentId("123");
            dateTime = LocalDateTime.now();
            comment.setPostedDate(dateTime);

            interaction.setInteractionType(InteractionType.LIKE);
            interaction.setUser(users.get((i + 5) % email.length));
            comment.addInteraction(interaction);
            comment.setPost(post);


            Comment reply = new Comment();
            reply.setCommentId("1234");
            reply.setContent(users.get((i + 2) % email.length) + " replied: " + replyContent[(i + 1) % 2]);
            interaction.setInteractionType(InteractionType.LOVE);
            interaction.setUser(users.get((i + 6) % email.length));
            reply.addInteraction(interaction);
            reply.setUser(users.get((i + 2) % email.length));

            dateTime = LocalDateTime.now();
            reply.setPostedDate(dateTime);


            if (i % 2 == 0) {
                reply.setContent(users.get((i + 3) % email.length) + " replied: " + replyContent[(i + 1) % 2]);
                comment.addComment(reply);
            }

            if (i % 2 == 0) {
                reply.setContent(users.get((i + 3) % email.length) + " replied: " + commentContent[(i + 1) % 2]);
                comment.addComment(reply);
            }

            post.addComment(comment);

            if (i % 2 == 0) {

                comment.setContent(users.get(i + 1).getFirstName() + " commented : " + commentContent[i % 2]);
                comment.setUser(users.get(i + 1));

                dateTime = LocalDateTime.now();
                comment.setPostedDate(dateTime);
                post.addComment(comment);
            }
            users.get(i).addPost(post);

        }
    }

    public PostInfo createPostInfo() {
        UserInfo a = new UserInfo(bao.getUserId(), bao.getFullName(), bao.getAvatar(), bao.getEmail(), bao.getDob());
        UserInfo b = new UserInfo(quy.getUserId(), quy.getFullName(), quy.getAvatar(), quy.getEmail(), quy.getDob());

        return new PostInfo("bao@mum.edu", "456", LocalDateTime.now()
                , AuthenticationFunctionUtils.convertToUserInfo.apply(bao)
                , 1, 1, Arrays.asList(a, b), Arrays.asList(a, b), null);
    }

    public RelationshipInfo createRelationshipInfo() {
        UserInfo a = new UserInfo(bao.getUserId(), bao.getFullName(), bao.getAvatar(), bao.getEmail(), bao.getDob());
        UserInfo b = new UserInfo(quy.getUserId(), quy.getFullName(), quy.getAvatar(), quy.getEmail(), quy.getDob());
        List<UserInfo> users = new ArrayList<>();
        users.add(a);
        users.add(b);
        return new RelationshipInfo(a, b, users, 2, null);
    }

    public CommentInfo createCommentInfo() {
        UserInfo a = new UserInfo(bao.getUserId(), bao.getFullName(), bao.getAvatar(), bao.getEmail(), bao.getDob());
        UserInfo b = new UserInfo(quy.getUserId(), quy.getFullName(), quy.getAvatar(), quy.getEmail(), quy.getDob());

        CommentInfo commentInfo = new CommentInfo("123", "456", LocalDateTime.now(), new UserInfo("bao@mum.edu", "Bao Tran"
                , "1001.jpg", "bao@mum.edu", null), 2, 2, Arrays.asList(a, b), Arrays.asList(a, b), null);
        return commentInfo;
    }
}
