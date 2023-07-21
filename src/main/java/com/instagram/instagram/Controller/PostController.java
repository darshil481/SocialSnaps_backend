package com.instagram.instagram.Controller;

import com.instagram.instagram.Exception.PostExeption;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.MassageResponse.MsgResponse;
import com.instagram.instagram.Repository.UserRepository;
import com.instagram.instagram.Services.PostService;
import com.instagram.instagram.Services.UserServices;
import com.instagram.instagram.entity.Post;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServices userServices;
    @PostMapping(value = "/createPost")
    public ResponseEntity<Post> createPost(@RequestBody Post post, @RequestHeader("Authorization") String token) throws PostExeption, UserException {
        User user=userServices.findUserProfile(token);
        Post p=postService.createPost(post,user.getId());
        return new ResponseEntity<>(new Post(), HttpStatus.OK);
    }
    @GetMapping("/findPostByUserId/{id}")
    public ResponseEntity<?> findPostByUserId(@PathVariable("id") Integer userId) throws PostExeption,UserException{
        List<Post> postList=postService.findPostByUserId(userId);
        return new ResponseEntity<>(postList,HttpStatus.OK);
    }
    @GetMapping("/followingUsers/{ids}")
    public ResponseEntity<?> findAllPostByUserIds(@PathVariable ("ids") List<Integer> userIds) throws  PostExeption,UserException{
        List<Post> allPostByUserIds=postService.findAllPostByUserIds(userIds);
        return new ResponseEntity<>(allPostByUserIds,HttpStatus.OK);
    }
    @GetMapping("/findPostById/{id}")
    public  ResponseEntity<?> findPostById(@PathVariable("id") Integer postId) throws PostExeption,UserException{
        Post post=postService.findPostById(postId);
        return  new ResponseEntity<>(post,HttpStatus.OK);
    }
    @PutMapping("likePost/{id}")
    public ResponseEntity<?> likePost(@PathVariable("id") Integer postId,@RequestHeader("Authorization") String token) throws  PostExeption,UserException{
        User user=userServices.findUserProfile(token);
        Post likedPost=postService.likePost(postId,user.getId());
        return new ResponseEntity<>(likedPost,HttpStatus.OK);
    }
    @PutMapping("unLikePost/{id}")
    public ResponseEntity<?> unLikePost(@PathVariable("id") Integer postId,@RequestHeader("Authorization") String token) throws  PostExeption,UserException{
        User user=userServices.findUserProfile(token);
        Post unLikedPost=postService.unLikePost(postId,user.getId());
        return new ResponseEntity<>(unLikedPost,HttpStatus.OK);
    }
    @DeleteMapping("deletePost/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Integer postId,@RequestHeader("Authorization") String token) throws  PostExeption,UserException{
        User user=userServices.findUserProfile(token);
        String msg=postService.deletePost(postId,user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("savedPost/{id}")
    public ResponseEntity<?> savedPost(@PathVariable("id") Integer postId,@RequestHeader("Authorization") String token) throws  PostExeption,UserException{
        User user=userServices.findUserProfile(token);
        String msg=postService.savedPost(postId,user.getId());
//        MsgResponse msgResponse=new MsgResponse(msg);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("unSavedPost/{id}")
    public ResponseEntity<?> unSavedPost(@PathVariable("id") Integer postId,@RequestHeader("Authorization") String token) throws  PostExeption,UserException{
        User user=userServices.findUserProfile(token);
        String msg=postService.unSavedPost(postId,user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
