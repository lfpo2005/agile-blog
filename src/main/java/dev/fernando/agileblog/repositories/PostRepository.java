package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface PostRepository extends JpaRepository<PostModel, UUID>, JpaSpecificationExecutor<PostModel> {

}
