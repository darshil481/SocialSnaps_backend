package com.instagram.instagram.Repository;

import com.instagram.instagram.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
@Repository
public interface PostRepository extends JpaRepository<Post,Integer> {
    @Query("SELECT p FROM Post p WHERE p.user.id = :userId")
    public List<Post> findByUserId(@Param("userId") Integer userId);
    @Query("SELECT p FROM Post p WHERE p.user.id IN :userIds ORDER BY p.createAt DESC")
    public List<Post> findPostByAllUserIds(@Param("userIds") List<Integer> userIds);

}
