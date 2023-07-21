package com.instagram.instagram.Services.ServiceImp;

import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.Repository.UserRepository;
import com.instagram.instagram.Token.TokenClaims;
import com.instagram.instagram.Token.TokenProvider;
import com.instagram.instagram.Services.UserServices;
import com.instagram.instagram.dto.UserDto;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImp implements UserServices {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;
    @Override
    public User registerUser(User user) throws UserException {
        System.out.println(user.getEmail());
        System.out.println(user.getName());
        System.out.println(user.getUserName());

        Optional<User> isExitEmail=userRepository.findByEmail(user.getEmail());
        Optional<User> isExitUserName=userRepository.findByUserName(user.getUserName());

        if(isExitEmail.isPresent()){
            throw new UserException("Email is Already exist !!");
        }
        if(isExitUserName.isPresent()){
            throw  new UserException("UserName is Already exist !!");
        }

        if(user.getUserName() == null || user.getEmail() == null || user.getName() == null ){
            throw  new UserException("All field are required !!");
        }

        User newUser=new User();
        newUser.setEmail(user.getEmail());
        newUser.setName(user.getName());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setUserName(user.getUserName());

        return userRepository.save(newUser);
    }

    @Override
    public User findUserById(int userId) throws UserException {
        Optional<User> opt = userRepository.findById(userId);

        if(opt.isPresent()){
            return opt.get();
        }else{
            throw new UserException("User not exist !!");
        }
    }
    @Override
    public User findUserProfile(String token) throws UserException {
        token=token.substring(7);
        TokenClaims tokenClaims= tokenProvider.getClaimsFromToken(token);
        String email=tokenClaims.getUserName();
        Optional<User> opt=userRepository.findByUserName(email);
        if(opt.isPresent()){
            return opt.get();
        }
        throw new UserException("Invalid Token");

    }

    @Override
    public User findUserByUserName(String userName) throws UserException {
        Optional<User> user=userRepository.findByUserName(userName);
        return user.get();
    }

    @Override
    public String followUser(Integer reqUserId, Integer followUserId) throws UserException {
        User reqUser=userRepository.findById(reqUserId).get();
        //jis user ko follow karna he
        User followUser=userRepository.findById(followUserId).get();

        UserDto follower=new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setUserName(reqUser.getUserName());
        follower.setImg(reqUser.getImg());
        follower.setId(reqUserId);
        follower.setName(reqUser.getName());

        UserDto following =new UserDto();
        following.setId(followUser.getId());
        following.setUserName(followUser.getUserName());
        following.setEmail(followUser.getEmail());
        following.setImg(followUser.getImg());
        following.setName(followUser.getName());
        reqUser.getFollowing().add(following);
        followUser.getFollower().add(follower);

        userRepository.save(reqUser);
        userRepository.save(followUser);
        return "you are follow "+followUser.getUserName();
    }

    @Override
    public String unFollowUser(Integer reqUserId, Integer unFollowUserId) throws UserException {
        User reqUser=userRepository.findById(reqUserId).get();
        //jis user ko follow karna he
        User followUser=userRepository.findById(unFollowUserId).get();

        UserDto follower=new UserDto();
        follower.setEmail(reqUser.getEmail());
        follower.setUserName(reqUser.getUserName());
        follower.setImg(reqUser.getImg());
        follower.setId(reqUserId);
        follower.setName(reqUser.getName());

        UserDto following =new UserDto();
        following.setId(followUser.getId());
        following.setUserName(followUser.getUserName());
        following.setEmail(followUser.getEmail());
        following.setImg(followUser.getImg());
        following.setName(followUser.getName());


        reqUser.getFollowing().remove(following);
        followUser.getFollower().remove(follower);
        
        userRepository.save(reqUser);
        userRepository.save(followUser);

        return "you have unfollowed " + followUser.getUserName();
    }

    @Override
    public List<User> findUserByIds(List<Integer> UserIds) throws UserException {
        List<User> userList=userRepository.findAllUserByIds(UserIds);
        return userList;
    }

    @Override
    public List<User> serchUser(String query) throws UserException {
        List<User> userList=userRepository.findByQuery(query);
        if(userList.size() == 0){
            throw new UserException("User not found !!");
        }
        return userList;
    }

    @Override
    public User upadteUserDatails(User updateUser, User exitingUser) throws UserException {
        if(updateUser.getEmail() != null) exitingUser.setEmail(updateUser.getEmail());
        if(updateUser.getName() != null) exitingUser.setName(updateUser.getName());
        if(updateUser.getUserName() != null) exitingUser.setUserName(updateUser.getUserName());
        if(updateUser.getImg() != null) exitingUser.setImg(updateUser.getImg());
        if(updateUser.getBio() != null) exitingUser.setBio(updateUser.getBio());
        if(updateUser.getGender() != null)exitingUser.setGender(updateUser.getGender());
        if(updateUser.getMobile() != null) exitingUser.setMobile(updateUser.getMobile());
        userRepository.save(exitingUser);
        return exitingUser;
    }
}
