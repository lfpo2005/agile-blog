package dev.fernando.agileblog.repositories;

import dev.fernando.agileblog.models.MessageContactModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageContactRepositry extends JpaRepository<MessageContactModel, UUID> {
}
