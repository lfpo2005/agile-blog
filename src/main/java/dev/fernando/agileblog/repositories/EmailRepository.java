package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
    boolean existsByEmailTo(String emailTo);

    @Query("SELECT e FROM EmailModel e WHERE e.activeNewsletter = true")
    List<EmailModel> findUsersWithActiveNewsletter();


}
