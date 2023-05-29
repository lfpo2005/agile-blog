package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.configs.security.UserDetailsImpl;
import dev.fernando.agileblog.dtos.PostDto;
import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.services.PostService;
import dev.fernando.agileblog.util.ConvertImage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
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
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@CacheConfig(cacheNames = "postAllCache")
public class PostContoller {

    @Autowired
    PostService postService;
    @Autowired
    private CacheManager cacheManager;

    @CacheEvict(value = "postAllCache", allEntries = true)
    @PostMapping(value = "/posts", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> savePost(@ModelAttribute PostDto postDto, Authentication authentication) {
        try {
            log.debug("POST savePost");

            MultipartFile coverImage = postDto.getImgCover();
            String coverImageBytes = ConvertImage.convertImage(coverImage, 1200, 250);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String fullName = userDetails.getFullName();

            PostModel postModel = new PostModel();
            BeanUtils.copyProperties(postDto, postModel);
            postModel.setAuthor(fullName);
            postModel.setImgCover(coverImageBytes);
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
    @CacheEvict(value = "postAllCache", allEntries = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/posts/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updatePost(@PathVariable(value = "postId") UUID postId,
                                             @ModelAttribute @Valid PostDto postDto) {

        log.debug("PUT updateModule moduleDto received {} ", postDto.toString());
        Optional<PostModel> postModelOptional = postService.findById(postId);
        if (!postModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found for this Category.");
        }
        var postModel = postModelOptional.get();
        postModel.setTitle(postDto.getTitle());
        postModel.setPost(postDto.getPost());
        postModel.setDescription(postDto.getDescription());
        postModel.setDateUpdate(LocalDateTime.now(ZoneId.of("UTC")));
        postService.save(postModel);

        log.debug("PUT updateModule moduleId saved {} ", postModel.getPostId());
        log.info("Module updated successfully moduleId {} ", postModel.getPostId());
        return ResponseEntity.status(HttpStatus.OK).body(postModel);
    }

    @CacheEvict(value = "postAllCache", allEntries = true)
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable(value = "postId") UUID postId) {
        log.debug("DELETE deleteModule moduleId received {} ", postId);

        Optional<PostModel> postModelOptional = postService.findById(postId);
        if (!postModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found for this Category.");
        }
        postService.delete(postModelOptional.get());

        Cache postCache = cacheManager.getCache("postAllCache");
        if (postCache != null) {
            postCache.evict(postId);
        }

        log.debug("DELETE deleteModule postId deleted {} ", postId);
        log.info("Module deleted successfully postId {} ", postId);
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully.");
    }
}
