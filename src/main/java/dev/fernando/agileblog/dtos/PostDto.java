package dev.fernando.agileblog.dtos;

import dev.fernando.agileblog.models.CategoryModel;
import lombok.Data;

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
    private CategoryModel category;
}
