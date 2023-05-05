package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.QuestionModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<QuestionModel, UUID> {
}
