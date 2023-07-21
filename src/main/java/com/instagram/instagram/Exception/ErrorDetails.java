package com.instagram.instagram.Exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {
    private String msg;
    private String details;
    private LocalDateTime time;
}
