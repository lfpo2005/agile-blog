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


import javax.mail.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


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

  //  @Transactional
    @Override
    public List<EmailModel> receiveEmail() {
        List<EmailModel> receivedEmails = new ArrayList<>();


        Properties properties = new Properties();
        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "email-ssl.com.br");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.ssl.enable", "true");
        properties.put("mail.imaps.auth", "true");

        Session emailSession = Session.getDefaultInstance(properties);
        Store store;

        try {
            store = emailSession.getStore();
            store.connect("admin@metodologia-agil.com.br", "paRGQbTw6874");

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];

                EmailModel emailModel = new EmailModel();
                emailModel.setSubject(message.getSubject());
                emailModel.setText(message.getContent().toString());
                emailModel.setEmailFrom(message.getFrom()[0].toString());

                receivedEmails.add(emailModel);
            }

            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receivedEmails;
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