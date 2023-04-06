package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.dtos.CategoryDto;
import dev.fernando.agileblog.models.CategoryModel;
import dev.fernando.agileblog.services.CategoryService;
import dev.fernando.agileblog.specifications.SpecificationTemplate;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Object> saveCategory(@RequestBody @Valid CategoryDto categoryDto){
        log.debug("POST saveCategory CategoriaDto received: ------> {}", categoryDto.toString());

        var categoryModel = new CategoryModel();

        BeanUtils.copyProperties(categoryDto, categoryModel);
        categoryService.save(categoryModel);

        log.debug("POST saveCategory categoryId saved: ------> {}", categoryModel.getCategoryId());
        log.info("Category saved successfully categoryId: ------> {}", categoryModel.getCategoryId());

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryModel);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Object> deleteCategory(@PathVariable(value="categoryId") UUID categoryId){
        log.debug("DELETE deleteCategory categoryId received {} ", categoryId);
        Optional<CategoryModel> categoriaModelOptional = categoryService.findById(categoryId);
        if(!categoriaModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria não existe.");
        }
        categoryService.delete(categoriaModelOptional.get());
        log.debug("DELETE Category categoryId deleted {} ", categoryId);
        log.info("Category deleted successfully categoryId {} ", categoryId);
        return ResponseEntity.status(HttpStatus.OK).body("Category deleted successfully!");
    }

   // @PermitAll
    @GetMapping
    public ResponseEntity<List<CategoryModel>> getAllCategory(SpecificationTemplate.CategorySpec spec,
                                                              /*@PageableDefault(page = 0, size = 10,
                                                                   sort = "categoryId", direction = Sort.Direction.ASC) Pageable pageable,*/
                                                              @RequestParam(required = false) UUID categoryId) {

        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll(spec));
    }
    @PermitAll
    @GetMapping("/{categoryId}")
    public ResponseEntity<Object> getOneCategory(@PathVariable(value="categoryId") UUID categoryId){
        Optional<CategoryModel> categoryModelOptional = categoryService.findById(categoryId);
        if(!categoryModelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category Not Found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(categoryModelOptional.get());
    }
}
/*@And({
           @Spec(path = "name", spec = Like.class)
    })
    public interface CategorySpec extends Specification<CategoryModel> {}*/