package dev.fernando.agileblog.models;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "TB_INCORRECT_QUESTION")
public class IncorrectQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID incorrectQuestionId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionModel question;
}
