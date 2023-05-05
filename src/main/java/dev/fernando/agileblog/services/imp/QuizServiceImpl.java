package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.models.IncorrectQuestion;
import dev.fernando.agileblog.models.QuestionModel;
import dev.fernando.agileblog.models.UserModel;
import dev.fernando.agileblog.repositories.IncorrectQuestionRepository;
import dev.fernando.agileblog.repositories.QuestionRepository;
import dev.fernando.agileblog.repositories.UserRepository;
import dev.fernando.agileblog.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    IncorrectQuestionRepository incorrectQuestionRepository;

    @Override
    public List<QuestionModel> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Override
    public List<QuestionModel> selectRandomQuestions(List<QuestionModel> allQuestions, int count) {
        Collections.shuffle(allQuestions);
        return allQuestions.stream().limit(count).collect(Collectors.toList());
    }

    @Override
    public List<QuestionModel> addIncorrectQuestionsFromPreviousAttempt(List<QuestionModel> allQuestions, List<UUID> incorrectQuestionIds, int count) {
        List<QuestionModel> incorrectQuestions = allQuestions.stream()
                .filter(q -> incorrectQuestionIds.contains(q.getQuestionId()))
                .collect(Collectors.toList());
        allQuestions.removeAll(incorrectQuestions);
        List<QuestionModel> selectedQuestions = selectRandomQuestions(allQuestions, count - incorrectQuestions.size());
        selectedQuestions.addAll(incorrectQuestions);
        Collections.shuffle(selectedQuestions);
        return selectedQuestions;
    }


    @Override
    public void saveIncorrectQuestions(UserModel user, List<QuestionModel> incorrectQuestions) {
        incorrectQuestions.forEach(question -> {
            IncorrectQuestion incorrectQuestion = new IncorrectQuestion();
            incorrectQuestion.setUser(user);
            incorrectQuestion.setQuestion(question);
            incorrectQuestionRepository.save(incorrectQuestion);
        });
    }
    @Override
    public List<QuestionModel> getIncorrectQuestions(UserModel user) {
        List<IncorrectQuestion> incorrectQuestions = incorrectQuestionRepository.findAllByUser(user);
        return incorrectQuestions.stream()
                .map(IncorrectQuestion::getQuestion)
                .collect(Collectors.toList());
    }

}
