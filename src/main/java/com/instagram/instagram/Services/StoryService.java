package com.instagram.instagram.Services;

import com.instagram.instagram.Exception.StoryException;
import com.instagram.instagram.Exception.UserException;
import com.instagram.instagram.entity.Story;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface StoryService {
    public Story createStory(Story story,Integer userId) throws UserException;
    public List<Story> findStoryByUserId(Integer userId) throws UserException, StoryException;

}
