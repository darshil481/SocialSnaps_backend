package com.instagram.instagram.Services;

import com.instagram.instagram.Exception.CommentException;
import com.instagram.instagram.Exception.PostExeption;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.entity.Comment;
import org.springframework.stereotype.Service;

@Service
public interface CommentService {
    public Comment createComment(Comment comment,Integer userId,Integer postId) throws UserException, PostExeption;
    public Comment findCommentById(Integer commentId) throws CommentException;
    public Comment likeComment(Integer commentId,Integer userId) throws  UserException,CommentException;
    public Comment unLikeComment(Integer commentId,Integer userId) throws  UserException,CommentException;

}
