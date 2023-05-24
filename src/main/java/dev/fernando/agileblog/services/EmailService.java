package dev.fernando.agileblog.services;


import dev.fernando.agileblog.models.EmailModel;

import java.util.List;

public interface EmailService {

    EmailModel sendEmail(EmailModel emailModel);

    List<EmailModel> receiveEmail();
}
