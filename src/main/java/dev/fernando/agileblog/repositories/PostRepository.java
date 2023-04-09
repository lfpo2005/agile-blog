package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostModel, UUID>, JpaSpecificationExecutor<PostModel> {

    @Query(value="select * from tb_post where category_category_id = :categoryId", nativeQuery = true)
    List<PostModel> findAllPostIntoCategory(@Param("categoryId") UUID categoryId);

    @Query(value = "select * from tb_post where category_category_id = :categoryId and post_id = :postId", nativeQuery = true)
    Optional<PostModel> findPostIntoCategory(@Param("categoryId") UUID categoryId, @Param("postId") UUID postId);

}
