package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.models.CategoryModel;
import dev.fernando.agileblog.services.CategoryService;
import dev.fernando.agileblog.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.util.List;
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
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<Page<CategoryModel>> getAllCategory(SpecificationTemplate.CategorySpec spec,
                                                              @PageableDefault(page = 0, size = 10,
                                                                      sort = "categoryId", direction = Sort.Direction.ASC) Pageable pageable) {

        Page<CategoryModel> categoryModelPage = categoryService.findAll(spec, pageable);

        if (!categoryModelPage.isEmpty()) {
            for (CategoryModel category : categoryModelPage.toList()){
                category.add(linkTo(methodOn(PublicController.class).getOneCategory(category.getCategoryId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryModelPage);
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<Object> getOneCategory(@PathVariable(value="categoryId") UUID categoryId){
        Optional<CategoryModel> categoryModelOptional = categoryService.findById(categoryId);
        if(!categoryModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryModelOptional.get());
    }
}
