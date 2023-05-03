package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.configs.security.UserDetailsImpl;
import dev.fernando.agileblog.dtos.PostDto;
import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.services.ImageService;
import dev.fernando.agileblog.services.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
//@RequestMapping("api/v1")
public class PostContoller {

    @Autowired
    PostService postService;

    @Autowired
    ImageService imageService;


    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/posts")
    public ResponseEntity<Object> savePost(@RequestBody @Valid PostDto postDto,
                                           Authentication authentication) {

        log.debug("POST savePost postDto received: ------> {}", postDto.toString());


        var postModel = new PostModel();
        BeanUtils.copyProperties(postDto, postModel);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String fullName = userDetails.getFullName();
        postModel.setAuthor(fullName);
        postModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        postModel.setDateUpdate(LocalDateTime.now(ZoneId.of("UTC")));
        postService.save(postModel);

        log.debug("POST savePost postId saved: ------> {}", postModel.getPostId());
        log.info("Post saved successfully postId: ------> {}", postModel.getPostId());

        return ResponseEntity.status(HttpStatus.CREATED).body(postModel);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<Object> deletePost(@PathVariable(value="postId") UUID postId){
        log.debug("DELETE deleteModule moduleId received {} ", postId);

        Optional<PostModel> postModelOptional = postService.findById(postId);
        if(!postModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found for this Category.");
        }
        postService.delete(postModelOptional.get());
        log.debug("DELETE deleteModule postId deleted {} ", postId);
        log.info("Module deleted successfully postId {} ", postId);
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully.");
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/posts/{postId}")
    public ResponseEntity<Object> updatePost(@PathVariable(value="postId") UUID postId,
                                               @RequestBody @Valid PostDto postDto){
        
        log.debug("PUT updateModule moduleDto received {} ", postDto.toString());
        Optional<PostModel> postModelOptional = postService.findById(postId);
        if(!postModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found for this Category.");
        }
        var postModel = postModelOptional.get();
        postModel.setTitle(postModel.getTitle());
        postModel.setPost(postModel.getPost());
        postModel.setDescription(postModel.getDescription());
/*
        postModel.setImgUrl(postDto.getImgUrl());
*/
        postService.save(postModel);

        log.debug("PUT updateModule moduleId saved {} ", postModel.getPostId());
        log.info("Module updated successfully moduleId {} ", postModel.getPostId());
        return ResponseEntity.status(HttpStatus.OK).body(postModel);
    }
/*    @GetMapping("/search")
//    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> searchPosts(@RequestParam("searchTerm") String searchTerm, Model model) {
        List<PostModel> postDtoList = postService.searchPosts(searchTerm);
        List<PostModel> postModelList = new ArrayList<>();
        for (PostModel postDto : postDtoList) {
            PostModel postModel = new PostModel();
            BeanUtils.copyProperties(postDto, postModel);
            postModelList.add(postModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postModelList);
    }*/
}
