package com.instagram.instagram.Repository;

import com.instagram.instagram.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByUserName(String userName);
    public Optional<User> findByEmail(String email);
//    List<User> findAllUserByIds(List<Integer> userIds);
    @Query("SELECT u FROM User u WHERE u.id IN :userIds")
    public List<User> findAllUserByIds(@Param("userIds") List<Integer> userIds);

    @Query("SELECT DISTINCT u FROM User u WHERE u.userName LIKE %:query% OR u.email LIKE %:query%")
    public List<User> findByQuery(@Param("query") String query);

}
