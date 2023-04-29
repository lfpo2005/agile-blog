package dev.fernando.agileblog.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class EmailDto {

    
    private String ownerRef;
    
    @Email
    private String emailFrom;
    
    @Email
    private String emailTo;
    
    private String subject;
    
    private String text;
    
    private String name;

    private boolean activeNewsletter;
}
