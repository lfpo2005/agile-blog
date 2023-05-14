package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.configs.security.UserDetailsImpl;
import dev.fernando.agileblog.dtos.PostDto;
import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.services.PostService;
import dev.fernando.agileblog.util.ConvertImage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
//@RequestMapping("api/v1")
public class PostContoller {

    @Autowired
    PostService postService;

    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> savePost(@RequestParam("title") String title,
                                           @RequestParam("post") String postText,
                                           @RequestParam("description") String description,
                                           @RequestParam("tags") List<String> tags,
                                           @RequestParam("img") MultipartFile file,
                                           Authentication authentication) {
        try {
            log.debug("POST savePost");

            String imgBase64 = ConvertImage.convertImage(file, 1300, 300);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String fullName = userDetails.getFullName();

            PostModel postModel = new PostModel();
            postModel.setTitle(title);
            postModel.setPost(postText);
            postModel.setDescription(description);

            List<String> upperCaseTags = tags.stream().map(String::toUpperCase).collect(Collectors.toList());

            postModel.setTags(upperCaseTags);
            postModel.setAuthor(fullName);
            postModel.setImg(imgBase64);
            postModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
            postModel.setDateUpdate(LocalDateTime.now(ZoneId.of("UTC")));
            postService.save(postModel);

            if (HttpStatus.OK.value() == HttpServletResponse.SC_OK) {
                postService.sendNewPostNotification(postModel);
            }

            log.debug("POST savePost postId saved: ------> {}", postModel.getPostId());
            log.info("Post saved successfully postId: ------> {}", postModel.getPostId());

            return ResponseEntity.status(HttpStatus.CREATED).body(postModel);
        } catch (IOException e) {
            log.error("Error while processing image: ", e);
            return ResponseEntity.badRequest().build();
        }
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "postId") UUID postId) {
        log.debug("DELETE deleteModule moduleId received {} ", postId);

        Optional<PostModel> postModelOptional = postService.findById(postId);
        if (!postModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found for this Category.");
        }
        postService.delete(postModelOptional.get());
        log.debug("DELETE deleteModule postId deleted {} ", postId);
        log.info("Module deleted successfully postId {} ", postId);
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully.");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable(value = "postId") UUID postId,
                                             @RequestBody @Valid PostDto postDto) {

        log.debug("PUT updateModule moduleDto received {} ", postDto.toString());
        Optional<PostModel> postModelOptional = postService.findById(postId);
        if (!postModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found for this Category.");
        }
        var postModel = postModelOptional.get();
        postModel.setTitle(postModel.getTitle());
        postModel.setPost(postModel.getPost());
        postModel.setDescription(postModel.getDescription());
        postService.save(postModel);

        log.debug("PUT updateModule moduleId saved {} ", postModel.getPostId());
        log.info("Module updated successfully moduleId {} ", postModel.getPostId());
        return ResponseEntity.status(HttpStatus.OK).body(postModel);
    }

}
