package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.models.AnswerSubmission;
import dev.fernando.agileblog.models.QuestionModel;
import dev.fernando.agileblog.models.QuizResult;
import dev.fernando.agileblog.models.UserModel;
import dev.fernando.agileblog.repositories.QuizResultRepository;
import dev.fernando.agileblog.repositories.UserRepository;
import dev.fernando.agileblog.services.QuizService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RestController
@RequestMapping("/quiz")
@CrossOrigin(origins = "*", maxAge = 3600)
public class QuizController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    QuizService quizService;

    @GetMapping("/start")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<QuestionModel>> startQuiz(@RequestParam(value = "incorrectQuestionIds",
            required = false) List<String> incorrectQuestionIds, Principal principal) {
        UserModel user = userRepository.findByUsername(principal.getName()).orElse(null);
        List<UUID> incorrectQuestionUUIDs = new ArrayList<>();

        if (user != null) {
            List<QuestionModel> incorrectQuestions = quizService.getIncorrectQuestions(user);
            incorrectQuestionUUIDs = incorrectQuestions.stream().map(QuestionModel::getQuestionId).collect(Collectors.toList());
        }

        if (incorrectQuestionIds != null) {
            incorrectQuestionUUIDs.addAll(incorrectQuestionIds.stream().map(UUID::fromString).collect(Collectors.toList()));
        }

        List<QuestionModel> allQuestions = quizService.getAllQuestions();
        List<QuestionModel> selectedQuestions = quizService.addIncorrectQuestionsFromPreviousAttempt(allQuestions, incorrectQuestionUUIDs, 80);

        return new ResponseEntity<>(selectedQuestions, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('USER')")
    @PostMapping("/submit")
    public ResponseEntity<?> submitQuiz(Principal principal, @RequestBody List<AnswerSubmission> answerSubmissions) {
        UserModel user = userRepository.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException("User not found."));

        List<QuestionModel> incorrectQuestions = new ArrayList<>();
        List<QuestionModel> allQuestions = quizService.getAllQuestions();
        int correctAnswers = 0;

        for (AnswerSubmission answerSubmission : answerSubmissions) {
            QuestionModel question = allQuestions.stream().filter(q -> q.getQuestionId().equals(answerSubmission.getQuestionId())).findFirst().orElse(null);

            if (question != null && question.getAnswer().equalsIgnoreCase(answerSubmission.getAnswer())) {
                correctAnswers++;
            } else {
                incorrectQuestions.add(question);
            }
        }

        if (user != null) {
            quizService.saveIncorrectQuestions(user, incorrectQuestions);
        }

        QuizResult quizResult = new QuizResult();

        int lastQuizNumber = quizResultRepository.findTopByUserIdOrderByQuizNumberDesc(user.getUserId()).map(QuizResult::getQuizNumber).orElse(0);
        quizResult.setQuizNumber(lastQuizNumber + 1);
        quizResult.setUserId(user.getUserId());
        quizResult.setCorrectAnswers(correctAnswers);
        quizResult.setTotalQuestions(answerSubmissions.size());
        quizResult.setStartTime(LocalDateTime.now());
        quizResult.setEndTime(LocalDateTime.now());
        quizResultRepository.save(quizResult);

        if (correctAnswers * 100 / answerSubmissions.size() < 80) {
            return new ResponseEntity<>(incorrectQuestions, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}