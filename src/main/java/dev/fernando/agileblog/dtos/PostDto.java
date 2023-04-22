package dev.fernando.agileblog.dtos;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class PostDto {

    private UUID postId;
    private String title;
    private String author;
    private String post;
    private int likes;
    private boolean favorite = false;
    private String description;
    private String imgUrl;
    private List<String> tags;
}
