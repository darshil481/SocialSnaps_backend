package com.instagram.instagram.entity;

import com.instagram.instagram.dto.UserDto;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="stories")
public class Story {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="id", column = @Column(name = "user_id")),
            @AttributeOverride(name="email", column = @Column(name = "user_email"))
    })
    private UserDto user;
    @NotNull
    private String story_img;
    private String caption;
    private LocalDateTime createAt;



}
