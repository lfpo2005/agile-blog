package dev.fernando.agileblog.dtos;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class PostDto {

    private UUID postId;
    private String title;
    private String author;
    private String post;
    private LocalDateTime creationDate;
    private LocalDateTime dateUpdate;
    private int likes;
    private boolean favorite = false;
    private String description;
    private String img;
    private List<String> tags;
}
