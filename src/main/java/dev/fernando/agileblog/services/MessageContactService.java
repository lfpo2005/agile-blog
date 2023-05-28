package dev.fernando.agileblog.services;

import dev.fernando.agileblog.models.MessageContactModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MessageContactService {
    MessageContactModel save(MessageContactModel messageContactModel);

    Optional<MessageContactModel> findById(UUID messageContactId);

    void delete(MessageContactModel messageContactModel);

    List<MessageContactModel> findAll();
}
