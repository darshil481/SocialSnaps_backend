package com.instagram.instagram.Services;

import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserServices {
    public User registerUser(User user) throws UserException;
    public User findUserById(int userId) throws  UserException;
    public User findUserProfile(String token) throws UserException;
    public User findUserByUserName(String userName) throws UserException;
    public String followUser(Integer reqUserId,Integer followUserId) throws UserException;
    public String unFollowUser(Integer reqUser,Integer unFollowUserId) throws UserException;
    public List<User> findUserByIds(List<Integer> UserIds) throws UserException;
    public List<User> serchUser(String query) throws UserException;
    public User upadteUserDatails(User updateUser,User exitingUser) throws UserException;

}
