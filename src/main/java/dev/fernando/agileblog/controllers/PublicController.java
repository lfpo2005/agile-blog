package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.services.PostService;
import dev.fernando.agileblog.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*", maxAge = 3600)
@CacheConfig(cacheNames = "postAllCache")
public class PublicController {

    @Autowired
    PostService postService;

    @Cacheable(value = "postAllCache", key = "#pageable.pageNumber")
    @GetMapping("posts/all")
    public ResponseEntity<Page<PostModel>> getAllPosts(SpecificationTemplate.PostSpec spec,
                                                       @PageableDefault(page = 0, size = 100,
                                                               sort = "postId", direction = Sort.Direction.ASC) Pageable pageable) {
        return getPageResponseEntity(spec, pageable);
    }
    @GetMapping("posts")
    public ResponseEntity<Page<PostModel>> getPostsByTitle(SpecificationTemplate.PostSpec spec,
                                                           @PageableDefault(page = 0, size = 100, sort = "postId", direction = Sort.Direction.ASC) Pageable pageable) {
        return getPageResponseEntity(spec, pageable);
    }

    @NotNull
    private ResponseEntity<Page<PostModel>> getPageResponseEntity(SpecificationTemplate.PostSpec spec, @PageableDefault(page = 0, size = 100, sort = "postId", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<PostModel> postModelPage = postService.findAll(spec, pageable);
        if (!postModelPage.isEmpty()) {
            for (PostModel post : postModelPage.toList()){
                post.add(linkTo(methodOn(PublicController.class).getOnePost(post.getPostId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(postModelPage);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Object> getOnePost(@PathVariable(value="postId") UUID postId){

        Optional<PostModel> postModelOptional = postService.findById(postId);
        if(!postModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found.");
        }
        postService.incrementViews(postId);
        return ResponseEntity.status(HttpStatus.OK).body(postModelOptional.get());
    }

    @GetMapping("/search")
//    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> searchPosts(@RequestParam("searchTerm") String searchTerm) {
        List<PostModel> postDtoList = postService.searchPosts(searchTerm);
        List<PostModel> postModelList = new ArrayList<>();
        for (PostModel postDto : postDtoList) {
            PostModel postModel = new PostModel();
            BeanUtils.copyProperties(postDto, postModel);
            postModelList.add(postModel);
        }
        return ResponseEntity.status(HttpStatus.OK).body(postModelList);
    }
}
