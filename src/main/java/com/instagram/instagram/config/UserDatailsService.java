package com.instagram.instagram.config;

import com.instagram.instagram.Repository.UserRepository;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserDatailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> opt=userRepository.findByUserName(username);
        if(opt.isPresent()){
            User user=opt.get();
            List<GrantedAuthority> authorities=new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(user.getUserName(),user.getPassword(),authorities);
        }
        throw  new BadCredentialsException("User not found with username");
    }
}
