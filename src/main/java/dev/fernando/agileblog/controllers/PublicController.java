package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.models.PostModel;
import dev.fernando.agileblog.services.PostService;
import dev.fernando.agileblog.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Log4j2
@RestController
@RequestMapping("/public")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PublicController {

    @Autowired
    PostService postService;

    @GetMapping("posts")
    public ResponseEntity<Page<PostModel>> getAllPosts(SpecificationTemplate.PostSpec spec,
                                                       @PageableDefault(page = 0, size = 100,
                                                               sort = "postId", direction = Sort.Direction.ASC) Pageable pageable) {
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found for this category.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(postModelOptional.get());
    }




}
