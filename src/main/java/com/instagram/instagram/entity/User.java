package com.instagram.instagram.entity;

import com.instagram.instagram.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String userName;
    private String email;
    private String mobile;
    private String website;
    private String bio;
    private String gender;
    private String img;
    private String password;
    @Embedded
    @ElementCollection
    private Set<UserDto> follower=new HashSet<UserDto>();
    @Embedded
    @ElementCollection
    private Set<UserDto> following=new HashSet<UserDto>();
    @OneToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private List<Story> stories=new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Post> savedPost=new ArrayList<>();

}
