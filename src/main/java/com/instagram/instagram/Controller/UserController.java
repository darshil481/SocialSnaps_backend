package com.instagram.instagram.Controller;

import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.Repository.UserRepository;
import com.instagram.instagram.Services.UserServices;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
//@RequestMapping("api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @GetMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserException {
        User ans=userServices.registerUser(user);
        return new ResponseEntity<>(ans, HttpStatus.OK);
        
    }
    @GetMapping("/findUserById")
    public ResponseEntity<User> findUserById(@RequestParam("id") int id) throws UserException{
        User user=userServices.findUserById(id);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/findUserByUserName")
    public ResponseEntity<User> findUserByUserName(@RequestParam("userName") String userName) throws UserException{
        User user=userServices.findUserByUserName(userName);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @GetMapping("/following/{id}")
    public ResponseEntity<?> following(@RequestHeader("Authorization") String token,@PathVariable("id") Integer followUserId) throws UserException {
        User user=userServices.findUserProfile(token);
        String ans= userServices.followUser(user.getId(),followUserId);
        return new ResponseEntity<>(ans,HttpStatus.OK);
    }
    @GetMapping("/unFollowing/{id}")
    public ResponseEntity<?> unFollowUser(@RequestHeader("Authorization") String token,@PathVariable("id") Integer followUserId) throws UserException {
        User user=userServices.findUserProfile(token);
        String ans= userServices.unFollowUser(user.getId(),followUserId);
        return new ResponseEntity<>(ans,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<User>> serching(@RequestParam String query) throws UserException {
        List<User> list=userServices.serchUser(query);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


    @GetMapping(value = "/signin",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<User> signinHandler(Authentication authentication) throws BadCredentialsException {
        Optional<User> opt=userRepository.findByUserName(authentication.getName());
        if(opt.isPresent()){
            return new ResponseEntity<User>(opt.get(),HttpStatus.OK);
        }else{
            throw new BadCredentialsException("Invalid User name or password !");
        }
    }
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String token,@RequestBody User user) throws UserException {
        User existingUser=userServices.findUserProfile(token);
        User upadateUser=userServices.upadteUserDatails(user,existingUser);
        return new ResponseEntity<>(upadateUser,HttpStatus.OK);
    }

}
