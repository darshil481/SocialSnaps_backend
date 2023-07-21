package com.instagram.instagram.entity;

import com.instagram.instagram.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String caption;
    private String post_img;
    private String location;
    private LocalDateTime createAt;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name = "user_id")),
            @AttributeOverride(name="email", column = @Column(name = "user_email"))
    })
    private UserDto user;
    @OneToMany
    private List<Comment> commentByUser=new ArrayList<>();
    @Embedded
    @ElementCollection
    @JoinTable(name = "likesByUsers",joinColumns = @JoinColumn(name="user_id"))
    private Set<UserDto> LikeByUser=new HashSet<>();

}
