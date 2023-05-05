package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.IncorrectQuestion;
import dev.fernando.agileblog.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IncorrectQuestionRepository extends JpaRepository<IncorrectQuestion, UUID> {
    List<IncorrectQuestion> findAllByUser(UserModel user);
}
