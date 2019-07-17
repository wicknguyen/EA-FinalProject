package com.mum.web.controller;

import com.mum.web.entities.User;
import com.mum.web.functional.AuthenticationFunctionUtils;
import com.mum.web.provider.DataProvider;
import com.mum.web.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/register/**")
public class AuthenticationController {
    @Autowired
    private DataProvider dataProvider;

    @PostMapping("/")
    public String registerUser(@RequestBody User user) {
        if (AuthenticationFunctionUtils.isUserExisted.apply(user.getEmail(), dataProvider.getUsers())) {
            return "DuplicateUser";
        }

        int rand = (int) Math.floor(Math.random() * 14) + 1;
        user.setAvatar("100_" + rand + ".jpg");
        dataProvider.addUsers(user);
        return "Successfully";
    }


}
