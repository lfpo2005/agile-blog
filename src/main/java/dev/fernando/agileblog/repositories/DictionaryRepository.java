package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.DictionaryModel;
import dev.fernando.agileblog.models.PostModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface DictionaryRepository extends JpaRepository<DictionaryModel, UUID>, JpaSpecificationExecutor<DictionaryModel> {

}
