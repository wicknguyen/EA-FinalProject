package com.mum.test.service;

import com.mum.web.entities.Gender;
import com.mum.web.entities.User;
import com.mum.web.provider.DataProvider;
import com.mum.web.services.UserAuthenticationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticationServiceTest extends com.mum.test.base.Test {

    @Mock
    private DataProvider dataProvider;

    @InjectMocks
    private UserAuthenticationService userAuthenticationService;

    @Test
    public void loadUserByUsernameTest() {

        when(dataProvider.getUsers()).thenReturn(Arrays.asList(bao));
        UserDetails userDetails = userAuthenticationService.loadUserByUsername("bao@mum.edu");
        assertNotNull(userDetails);
    }

}
