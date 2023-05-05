package dev.fernando.agileblog.models;

import lombok.Data;

import java.util.UUID;

@Data
public class AnswerSubmission {

    private UUID questionId;
    private String answer;
}
