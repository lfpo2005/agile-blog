package dev.fernando.agileblog.services;


import dev.fernando.agileblog.models.EmailModel;

public interface EmailService {

    EmailModel sendEmail(EmailModel emailModel);

}
