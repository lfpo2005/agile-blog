package dev.fernando.agileblog.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.fernando.agileblog.enums.AnsweredType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_MENSAGE_CONTACT")
public class MessageContactModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID messageContactId;

    @Column(nullable = false, length = 1000)
    private String message;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String userAnswer;
    @Column(nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private AnsweredType answered;
    private boolean visualized = false;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime creationDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime dateAnswer;
}
