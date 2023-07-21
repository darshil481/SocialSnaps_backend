package com.instagram.instagram.Controller;

import com.instagram.instagram.Exception.StoryException;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.Services.StoryService;
import com.instagram.instagram.Services.UserServices;
import com.instagram.instagram.entity.Story;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StoryController {
    @Autowired
    private StoryService storyService;
    @Autowired
    private UserServices userServices;
    @PostMapping("createStory")
    public ResponseEntity<Story> createStory(@RequestBody Story story, @RequestHeader("Authorization") String token) throws UserException {
        User user=userServices.findUserProfile(token);
        Story createStory=storyService.createStory(story,user.getId());
        return new ResponseEntity<Story>(createStory, HttpStatus.OK);
    }
    @GetMapping("findStoryByUserId/{id}")
    public ResponseEntity<List<Story>> findStoryByUserId(@PathVariable("id") Integer userId) throws UserException, StoryException {
        User user=userServices.findUserById(userId);
        List<Story> storyList=storyService.findStoryByUserId(user.getId());
        return new ResponseEntity<List<Story>>(storyList,HttpStatus.OK);
    }

}
