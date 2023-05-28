package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.models.MessageContactModel;
import dev.fernando.agileblog.repositories.MessageContactRepositry;
import dev.fernando.agileblog.services.MessageContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageContactServiceImpl implements MessageContactService {

    @Autowired
    MessageContactRepositry messageContactRepositry;

    @Override
    public MessageContactModel save(MessageContactModel messageContactModel) {
        return messageContactRepositry.save(messageContactModel);
    }

    @Override
    public Optional<MessageContactModel> findById(UUID messageContactId) {
        return messageContactRepositry.findById(messageContactId);
    }

    @Override
    public void delete(MessageContactModel messageContactModel) {
        messageContactRepositry.delete(messageContactModel);
    }

    @Override
    public List<MessageContactModel> findAll() {
        return messageContactRepositry.findAll();
    }
}
