package com.mum.test.controller;

import com.mum.web.controller.AuthenticationController;
import com.mum.web.entities.User;
import com.mum.web.provider.DataProvider;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    User bao;

    @Mock
    DataProvider dataProvider;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Before
    public void setup() {
        bao = new User();
        bao.setEmail("bao@mum.edu");
    }

    @Test
    public void registerUserTest() {
        authenticationController.registerUser(bao);
    }

}
