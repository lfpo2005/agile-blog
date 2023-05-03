package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<PostModel, UUID>, JpaSpecificationExecutor<PostModel> {

    @Query("SELECT p FROM PostModel p LEFT JOIN p.tags tag WHERE CONCAT(LOWER(p.title) , ' ', LOWER(tag)) LIKE %:searchTerm%\n")
    List<PostModel> findByTitleOrTagsContainingIgnoreCase(@Param("searchTerm") String searchTerm);

}
