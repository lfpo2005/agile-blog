package dev.fernando.agileblog.dtos;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

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
    private MultipartFile imgCover;
    private String alt;
    private List<String> tags;
}
