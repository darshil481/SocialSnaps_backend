package com.instagram.instagram.Services;

import com.instagram.instagram.Exception.PostExeption;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.entity.Post;
import org.apache.catalina.LifecycleState;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PostService {
    public Post createPost(Post post,Integer userId) throws UserException, PostExeption;
    public String deletePost(Integer postId,Integer userId) throws  UserException,PostExeption;
    public List<Post> findPostByUserId(Integer userId) throws  UserException;
    public Post findPostById(Integer postId) throws  PostExeption;
    public List<Post> findAllPostByUserIds(List<Integer> userIds) throws PostExeption,UserException;
    public String savedPost(Integer postId,Integer userId) throws  PostExeption,UserException;
    public String unSavedPost(Integer postId,Integer userId) throws  PostExeption,UserException;
    public Post likePost(Integer postId,Integer userId) throws PostExeption,UserException;
    public Post unLikePost(Integer postId,Integer userId) throws PostExeption,UserException;


}
