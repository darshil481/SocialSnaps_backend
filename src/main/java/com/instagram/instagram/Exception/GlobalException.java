package com.instagram.instagram.Exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException  {
    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDetails> UserExceptionHandler(UserException userException, WebRequest request) {
        ErrorDetails errorDetails=new ErrorDetails(userException.getMessage(),request.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception exeception,WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(exeception.getMessage(),request.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> methodArgumentNotValidExceptionhandler(MethodArgumentNotValidException methodArgumentNotValidException,WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage(),"validation Error",LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(PostExeption.class)
    public ResponseEntity<ErrorDetails> postExceptionHandler(PostExeption exception,WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(exception.getMessage(),request.getDescription(false),LocalDateTime.now());
        return new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(CommentException.class)
    public ResponseEntity<ErrorDetails> commentExceptionHandler(CommentException exception,WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(exception.getMessage(),request.getDescription(false),LocalDateTime.now());
        return  new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(StoryException.class)
    public ResponseEntity<ErrorDetails> storyExceptionHandler(StoryException exception,WebRequest request){
        ErrorDetails errorDetails=new ErrorDetails(exception.getMessage(),request.getDescription(false),LocalDateTime.now());
        return  new ResponseEntity<ErrorDetails>(errorDetails,HttpStatus.BAD_REQUEST);
    }
}
