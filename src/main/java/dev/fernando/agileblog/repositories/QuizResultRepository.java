package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizResultRepository extends JpaRepository<QuizResult, UUID> {

    Optional<QuizResult> findTopByUserIdOrderByQuizNumberDesc(UUID userId);
    List<QuizResult> findByUserId(UUID userId);

}
