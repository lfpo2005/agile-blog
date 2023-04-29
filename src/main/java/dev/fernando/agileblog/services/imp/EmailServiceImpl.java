package dev.fernando.agileblog.services.imp;


import dev.fernando.agileblog.enums.StatusEmail;
import dev.fernando.agileblog.models.EmailModel;
import dev.fernando.agileblog.repositories.EmailRepository;
import dev.fernando.agileblog.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    JavaMailSender emailSender;


    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private static final String API_URL = "https://api.smtplw.com.br/v1/messages";
    private static final String API_TOKEN = "38fdc8e5b60e3ef9768cb2d3f9de7149"; // substitua pelo token

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
            } else {
                emailModel.setStatusEmail(StatusEmail.ERROR);
            }
        } catch (IOException e) {
            emailModel.setStatusEmail(StatusEmail.ERROR);
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