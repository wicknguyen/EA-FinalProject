package com.mum.web.services;

import com.mum.web.entities.User;
import com.mum.web.functional.AuthenticationFunctionUtils;
import com.mum.web.provider.DataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserAuthenticationService implements UserDetailsService {

    @Autowired
    private DataProvider dataProvider;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = dataProvider.getUsers();
        Optional<User> foundUser = AuthenticationFunctionUtils.getUserByMail.apply(username, users);
        if (!foundUser.isPresent()) {
            throw new UsernameNotFoundException("Can not find user with username = " + username);
        }
        User user = foundUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("USER"));
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(), authorities);
        return userDetails;
    }
}
