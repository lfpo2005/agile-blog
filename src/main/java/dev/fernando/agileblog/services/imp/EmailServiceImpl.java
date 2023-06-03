package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.enums.StatusEmail;
import dev.fernando.agileblog.models.EmailModel;
import dev.fernando.agileblog.repositories.EmailRepository;
import dev.fernando.agileblog.services.EmailService;
import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Log4j2
@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    EmailRepository emailRepository;

    @Autowired
    JavaMailSender emailSender;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Value("${blog.email.url}")
    private String API_URL;

    @Value("${blog.email.token}")
    private String API_TOKEN;
    @Transactional
    @Override
    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        OkHttpClient client = new OkHttpClient();

        String json = "{"
                + "\"subject\": \"" + emailModel.getSubject() + "\","
                + "\"body\": \"" + emailModel.getText() + "\","
                + "\"from\": \"" + emailModel.getEmailFrom() + "\","
                + "\"to\": \"" + emailModel.getEmailTo() + "\","
                + "\"headers\": {\"Content-Type\": \"text/html\"}"
                + "}";

        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(API_URL)
                .header("x-auth-token", API_TOKEN)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
            emailModel.setStatusEmail(StatusEmail.SENT);
            log.info("Email sending successfully -------------> emailId {}", emailModel.getEmailId());

            } else {
                emailModel.setStatusEmail(StatusEmail.ERROR);
            }
        } catch (IOException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
            log.error("Error sending email", e);
            log.error("Error sending email Service  --------------> emailId: {}", emailModel.getEmailId());
        } finally {
            return emailRepository.save(emailModel);
        }
    }
/*

    @Override
    public Page<EmailModel> findAll(Pageable pageable) {
        return  emailRepository.findAll(pageable);
    }
    @Override
    public Optional<EmailModel> findById(UUID emailId) {
        return emailRepository.findById(emailId);
    }
*/

}