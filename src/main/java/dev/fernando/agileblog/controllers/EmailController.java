package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.dtos.EmailDto;
import dev.fernando.agileblog.models.EmailModel;
import dev.fernando.agileblog.services.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Log4j2
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("")
public class EmailController {

    @Autowired
    EmailService emailService;

    //    @Cacheable(value = "newsletterCache", key = "#emailModel.getEmailTo()")
    @PostMapping("/newsletter")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody @Valid EmailDto emailDto) {
        EmailModel emailModel = new EmailModel();
        BeanUtils.copyProperties(emailDto, emailModel);
        emailModel.setEmailFrom("Blog Agil" + " <contato@metodologia-agil.com.br>");
        emailModel.setEmailTo(emailDto.getEmailTo());
        emailModel.setSubject("Confirmação de Inscrição na Newsletter");
        emailModel.setText("Olá " + emailDto.getName() + ", obrigado por se inscrever na nossa newsletter!");
        emailService.sendEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }
}