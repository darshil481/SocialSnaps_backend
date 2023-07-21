package com.instagram.instagram.Services.ServiceImp;

import com.instagram.instagram.Exception.StoryException;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.Repository.StoryRepository;
import com.instagram.instagram.Repository.UserRepository;
import com.instagram.instagram.Services.StoryService;
import com.instagram.instagram.Services.UserServices;
import com.instagram.instagram.dto.UserDto;
import com.instagram.instagram.entity.Story;
import com.instagram.instagram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class StoryServiceImp implements StoryService {
    @Autowired
    private StoryRepository storyRepository;
    @Autowired
    private UserServices userServices;
    @Override
    public Story createStory(Story story, Integer userId) throws UserException {
        User user=userServices.findUserById(userId);
        UserDto userDto=new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setImg(user.getImg());
        story.setUser(userDto);
        story.setCreateAt(LocalDateTime.now());
        user.getStories().add(story);
        Story createdStory=storyRepository.save(story);
        return createdStory;
    }

    @Override
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException {
        User user=userServices.findUserById(userId);
        List<Story> storyList=user.getStories();
         if(storyList.size() ==0){
             throw new StoryException("this user doesn't have any story !");
         }
         return storyList;
    }
}
