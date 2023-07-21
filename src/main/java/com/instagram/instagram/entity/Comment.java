package com.instagram.instagram.entity;

import com.instagram.instagram.dto.UserDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name="comment")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name = "user_id")),
            @AttributeOverride(name="email", column = @Column(name = "user_email"))
    })
    private UserDto user;
    private  String content;
    @Embedded
    @ElementCollection
    private Set<UserDto> likes=new HashSet<>();
    private LocalDateTime createAt;

}
