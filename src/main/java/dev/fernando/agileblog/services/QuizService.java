package dev.fernando.agileblog.services;

import dev.fernando.agileblog.models.QuestionModel;
import dev.fernando.agileblog.models.UserModel;

import java.util.List;
import java.util.UUID;

public interface QuizService {
    List<QuestionModel> addIncorrectQuestionsFromPreviousAttempt(List<QuestionModel> allQuestions, List<UUID> incorrectQuestionIds, int i);

    List<QuestionModel> selectRandomQuestions(List<QuestionModel> allQuestions, int i);

    List<QuestionModel> getAllQuestions();

    void saveIncorrectQuestions(UserModel user, List<QuestionModel> incorrectQuestions);

    List<QuestionModel> getIncorrectQuestions(UserModel user);
}
