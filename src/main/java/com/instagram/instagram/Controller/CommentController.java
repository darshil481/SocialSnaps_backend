package com.instagram.instagram.Controller;

import com.instagram.instagram.Exception.CommentException;
import com.instagram.instagram.Exception.PostExeption;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.Services.CommentService;
import com.instagram.instagram.Services.UserServices;
import com.instagram.instagram.entity.Comment;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserServices userServices;
    @PostMapping("/createComment/{id}")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment, @RequestHeader("Authorization") String token, @PathVariable("id") Integer postId) throws UserException, PostExeption {
        User user=userServices.findUserProfile(token);
        Comment createdComment=commentService.createComment(comment,user.getId(),postId);
        return new ResponseEntity<Comment>(createdComment, HttpStatus.OK);
    }
    @GetMapping("/findCommentById/{id}")
    public ResponseEntity<Comment> findCommentById(@PathVariable("id") Integer commentId) throws  CommentException{
        Comment comment=commentService.findCommentById(commentId);
        return new ResponseEntity<Comment>(comment,HttpStatus.OK);
    }
    @PutMapping("/likeComment/{id}")
    public ResponseEntity<Comment> likeComment(@PathVariable("id") Integer commentId,@RequestHeader("Authorization") String token) throws UserException, CommentException {
        User user=userServices.findUserProfile(token);
        Comment comment=commentService.likeComment(commentId, user.getId());
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }
    @PutMapping("/unLikeComment/{id}")
    public ResponseEntity<Comment> unLikeComment(@PathVariable("id") Integer commentId,@RequestHeader("Authorization") String token) throws UserException, CommentException {
        User user=userServices.findUserProfile(token);
        Comment comment=commentService.unLikeComment(commentId, user.getId());
        return new ResponseEntity<>(comment,HttpStatus.OK);
    }

}
