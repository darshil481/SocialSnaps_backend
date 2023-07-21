package com.instagram.instagram.Services.ServiceImp;

import com.instagram.instagram.Exception.PostExeption;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.Repository.PostRepository;
import com.instagram.instagram.Repository.UserRepository;
import com.instagram.instagram.Services.PostService;
import com.instagram.instagram.Services.UserServices;
import com.instagram.instagram.Token.TokenProvider;
import com.instagram.instagram.dto.UserDto;
import com.instagram.instagram.entity.Post;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImp implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Post createPost(Post post,Integer userId) throws UserException, PostExeption {

        User user=userServices.findUserById(userId);
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setName(user.getName());
        userDto.setImg(user.getImg());
        post.setUser(userDto);
        postRepository.save(post);
        return post;

    }

    @Override
    public String deletePost(Integer postId, Integer userId) throws UserException, PostExeption {

        Optional<Post> post=postRepository.findById(postId);
        if(post.get().getUser().getId()== userId){
            postRepository.deleteById(post.get().getId());
            return "post deleted !";
        }
        throw  new PostExeption("you cant delete other user post");

    }

    @Override
    public List<Post> findPostByUserId(Integer userId) throws UserException {
        List<Post> postList=postRepository.findByUserId(userId);
        if(postList.size() == 0){
            throw  new UserException("This user does not have any posts !");
        }
        return postList;
    }

    @Override
    public Post findPostById(Integer postId) throws PostExeption {
        Optional<Post> opt=postRepository.findById(postId);
        if(opt.isPresent()){
            return opt.get();
        }
        throw  new PostExeption("Post Not found with id "+ postId);
    }

    @Override
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostExeption, UserException {
        List<Post> postList=postRepository.findPostByAllUserIds(userIds);
        if(postList.size() == 0){
            throw new PostExeption("Post Not Exist !");
        }
        return postList;
    }

    @Override
    public String savedPost(Integer postId, Integer userId) throws PostExeption, UserException {
        Optional<Post> post=postRepository.findById(postId);
        User user=userServices.findUserById(userId);
        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().add(post.get());
            userRepository.save(user);
        }
        return "post are Saved !";
    }

    @Override
    public String unSavedPost(Integer postId, Integer userId) throws PostExeption, UserException {
        Optional<Post> post=postRepository.findById(postId);
        User user=userServices.findUserById(userId);
        if(!user.getSavedPost().contains(post)){
            user.getSavedPost().remove(post.get());
            userRepository.save(user);
        }
        return "post are removed !";
    }

    @Override
    public Post likePost(Integer postId, Integer userId) throws PostExeption, UserException {
        Optional<Post> post=postRepository.findById(postId);
        Optional<User> user=userRepository.findById(userId);
        UserDto userDto=new UserDto();
        userDto.setId(user.get().getId());
        userDto.setUserName(user.get().getUserName());
        userDto.setEmail(user.get().getEmail());
        userDto.setImg(user.get().getImg());
        post.get().getLikeByUser().add(userDto);
        Post p=postRepository.save(post.get());
        return p;
    }

    @Override
    public Post unLikePost(Integer postId, Integer userId) throws PostExeption, UserException {
        Optional<Post> post=postRepository.findById(postId);
        Optional<User> user=userRepository.findById(userId);
        UserDto userDto=new UserDto();
        userDto.setId(user.get().getId());
        userDto.setUserName(user.get().getUserName());
        userDto.setEmail(user.get().getEmail());
        userDto.setImg(user.get().getImg());
        post.get().getLikeByUser().remove(userDto);
        Post p=postRepository.save(post.get());
        return p;
    }
}
