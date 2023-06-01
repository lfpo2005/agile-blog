package dev.fernando.agileblog.services.imp;

import dev.fernando.agileblog.enums.StatusEmail;
import dev.fernando.agileblog.models.EmailModel;
import dev.fernando.agileblog.repositories.EmailRepository;
import dev.fernando.agileblog.services.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
@Log4j2
@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    EmailRepository emailRepository;

    @Autowired
    JavaMailSender emailSender;

    @Transactional
    @Override
    public EmailModel sendEmail(EmailModel emailModel) {
        emailModel.setSendDateEmail(LocalDateTime.now());
        if (emailRepository.existsByEmailTo(emailModel.getEmailTo())) {
            return emailModel;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
            log.info("Email sending successfully -------------> emailId {}", emailModel.getEmailId());
        } catch (MailException e) {
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