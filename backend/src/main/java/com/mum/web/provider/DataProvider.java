package com.mum.web.provider;

import com.mum.web.entities.*;
import com.mum.web.repositories.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataProvider {
    @Autowired
    private UserRepository userRepository;

    private List<String> datas = new ArrayList<>();

    private List<User> users = new ArrayList<>();

    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }

    public void addDatas(String data) {
        this.datas.add(data);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUsers(User user) {
        userRepository.save(user);
    }

    @PostConstruct
    public void initData() {
        ////This is for test only
        initTestData();
        initFriend();

    }

    void initFriend() {
        User bao = users.get(0);
        User tam = users.get(1);
        User quy = users.get(2);
        User huy = users.get(3);
        User bang = users.get(4);
        User sang = users.get(5);
        User tung = users.get(6);
        User viet = users.get(7);
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

        viet.addFriend(bao);


        userRepository.saveAll(users);
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

        LocalDateTime dateTime = LocalDateTime.now();// LocalDateTime.parse("2018-12-30T19:34:50.63",formatter);

        for (int i = 0; i < email.length; i++) {

            User user = new User();
            user.setEmail(email[i]);
            user.setFirstName(firstName[i]);
            user.setLastName(lastName[i]);
            user.setPassword("123456");
            user.setAvatar("100_" + (i + 1) + ".jpg");
            user.setDob(LocalDate.of(1977, 7,7));
            users.add(user);

        }

        List<Post> posts = new ArrayList<>();
        for (int i = 0; i < email.length; i++) {

            Post post = new Post();

            post.setContent(users.get(i).getFirstName() + " posted: " + postContent[i % 2]);
            post.setUser(users.get(i));
            post.setPostedDate(dateTime);

            Interaction like = new Interaction();

            like.setPost(post);
            User userInteraction = users.get((i + 1) % email.length);
            like.setUser(userInteraction);
            like.setInteractionType(InteractionType.LIKE);
            dateTime = LocalDateTime.now();
            like.setActionDate(dateTime);

            post.addInteraction(like);
            like.setUser(users.get((i + 2) % email.length));
            if (i % 2 == 0)
                post.addInteraction(like);

            Comment comment = new Comment();
            comment.setUser(userInteraction);
            comment.setContent(userInteraction + " commented: " + commentContent[i % 2]);

            dateTime = LocalDateTime.now();
            comment.setPostedDate(dateTime);

            Interaction likeComment = new Interaction();

            likeComment.setInteractionType(InteractionType.LIKE);
            likeComment.setUser(users.get((i + 5) % email.length));
            comment.addInteraction(like);

            comment.setPost(post);

            Comment reply = new Comment();
            reply.setContent(users.get((i + 2) % email.length) + " replied: " + replyContent[(i + 1) % 2]);
            reply.setUser(users.get((i + 2) % email.length));

            dateTime = LocalDateTime.now();
            reply.setPostedDate(dateTime);

            if (i % 2 == 0) {
                reply.setContent(users.get((i + 3) % email.length) + " replied: " + replyContent[(i + 1) % 2]);
                comment.addComment(reply);
                reply.setParentComment(comment);
            }

            if (i % 2 == 0) {
                reply.setContent(users.get((i + 3) % email.length) + " replied: " + commentContent[(i + 1) % 2]);
                comment.addComment(reply);
                reply.setParentComment(comment);
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
            posts.add(post);

        }
        userRepository.saveAll(users);
    }
}
