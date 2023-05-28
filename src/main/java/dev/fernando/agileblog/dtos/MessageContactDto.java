package dev.fernando.agileblog.dtos;

import dev.fernando.agileblog.enums.AnsweredType;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class MessageContactDto {
    private UUID messageContactId;
    private String message;
    private String email;
    private AnsweredType answered;
    private boolean visualized;
    private LocalDateTime creationDate;
}
