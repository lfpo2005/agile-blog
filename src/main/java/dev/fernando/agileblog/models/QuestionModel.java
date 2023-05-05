package dev.fernando.agileblog.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_QUESTION")
public class QuestionModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID questionId;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String question;
    @Column(nullable = false, length = 550)
    private String optionA;
    @Column(nullable = false, length = 550)
    private String optionB;
    @Column(length = 550)
    private String optionC;
    @Column(length = 550)
    private String optionD;
    @Column(nullable = false, length = 15)
    private String answer;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String explanation;
}
