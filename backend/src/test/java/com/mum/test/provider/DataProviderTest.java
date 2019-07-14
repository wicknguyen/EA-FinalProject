package com.mum.test.provider;

import com.mum.web.entities.User;
import com.mum.web.provider.DataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class DataProviderTest {

    @InjectMocks
    public DataProvider dataProvider;

    @Test
    public void initDataTest() {
        dataProvider.initData();
        List<User> users = dataProvider.getUsers();
        assertNotNull(users);
    }
}
