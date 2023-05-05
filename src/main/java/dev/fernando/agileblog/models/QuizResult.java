package dev.fernando.agileblog.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_QUIZ_RESULT")
public class QuizResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID quizId;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private int correctAnswers;

    @Column(nullable = false)
    private int totalQuestions;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

    @Column(nullable = false)
    private int quizNumber;
}
