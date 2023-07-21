package com.instagram.instagram.Services.ServiceImp;

import com.instagram.instagram.Exception.CommentException;
import com.instagram.instagram.Exception.PostExeption;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.Repository.CommentRepository;
import com.instagram.instagram.Repository.PostRepository;
import com.instagram.instagram.Services.CommentService;
import com.instagram.instagram.Services.PostService;
import com.instagram.instagram.Services.UserServices;
import com.instagram.instagram.dto.UserDto;
import com.instagram.instagram.entity.Comment;
import com.instagram.instagram.entity.Post;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserServices userServices;
    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;
    @Override
    public Comment createComment(Comment comment, Integer userId, Integer postId) throws UserException, PostExeption {
        User user=userServices.findUserById(userId);
        Post post=postService.findPostById(postId);
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setImg(user.getImg());
        comment.setUser(userDto);
        comment.setCreateAt(LocalDateTime.now());
        Comment createdComment=commentRepository.save(comment);
        post.getCommentByUser().add(createdComment);
        postRepository.save(post);
        return createdComment;
    }

    @Override
    public Comment findCommentById(Integer commentId) throws CommentException {
        Optional<Comment> comment=commentRepository.findById(commentId);
        if(comment.isPresent()){
            return comment.get();
        }
        throw new CommentException("comment not exit with id : "+commentId);
    }

    @Override
    public Comment likeComment(Integer commentId, Integer userId) throws UserException, CommentException {
        User user=userServices.findUserById(userId);
        Comment comment=commentRepository.findById(commentId).get();
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setImg(user.getImg());
        comment.getLikes().add(userDto);
        Comment likedComment=commentRepository.save(comment);
        return  likedComment;
    }

    @Override
    public Comment unLikeComment(Integer commentId, Integer userId) throws UserException, CommentException {
        User user=userServices.findUserById(userId);
        Comment comment=commentRepository.findById(commentId).get();
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setImg(user.getImg());
        comment.getLikes().remove(userDto);
        Comment likedComment=commentRepository.save(comment);
        return  likedComment;
    }
}
