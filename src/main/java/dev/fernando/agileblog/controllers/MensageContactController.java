package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.dtos.MessageContactDto;
import dev.fernando.agileblog.enums.AnsweredType;
import dev.fernando.agileblog.models.AnsweredTypeWrapper;
import dev.fernando.agileblog.models.MessageContactModel;
import dev.fernando.agileblog.services.MessageContactService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RestController
@RequestMapping("/contact")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MensageContactController {

    @Autowired
    MessageContactService messageContactService;
    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> saveMsgContact(@RequestBody MessageContactDto messageContactDto){
        log.debug("MsgContact saveMsgContact mensageContactDto received: ------> {}", messageContactDto.toString());
        var messageContactModel = new MessageContactModel();
        BeanUtils.copyProperties(messageContactDto, messageContactModel);
        messageContactModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        messageContactModel.setAnswered(AnsweredType.NOT);
//        messageContactModel.setAnswered(false);
        messageContactService.save(messageContactModel);
        log.debug("POST saveMsgContact getMessageContactId saved: ------> {}", messageContactModel.getMessageContactId());
        log.info("MsgContact saved successfully ------> messageContactId: ------- {} ", messageContactModel.getMessageContactId());
        return ResponseEntity.status(HttpStatus.CREATED).body(messageContactModel);
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllMsgContacts(){
        List<MessageContactModel> messageContactModels = messageContactService.findAll();
        if (messageContactModels.isEmpty()) {
            log.info("No MsgContacts found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        log.info("MsgContacts retrieved successfully.");
        return ResponseEntity.status(HttpStatus.OK).body(messageContactModels);
    }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{messageContactId}")
    public ResponseEntity<Object> getMsgContactById(@PathVariable(value = "messageContactId") UUID messageContactId){
        Optional<MessageContactModel> messageContactModelOptional = messageContactService.findById(messageContactId);
        if (!messageContactModelOptional.isPresent()) {
            log.info("MsgContact not found ------> messageContactId: ------- {} ", messageContactId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        MessageContactModel messageContactModel = messageContactModelOptional.get();
        messageContactModel.setVisualized(true);
        messageContactService.save(messageContactModel);

        log.info("MsgContact retrieved successfully ------> messageContactId: ------- {} ", messageContactId);
        return ResponseEntity.status(HttpStatus.OK).body(messageContactModel);
    }
    //@PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping("/{messageContactId}")
    public ResponseEntity<Object> updateMsgContact(@PathVariable(value = "messageContactId") UUID messageContactId,
                                                   @RequestBody MessageContactDto messageContactDto){
        Optional<MessageContactModel> messageContactModelOptional = messageContactService.findById(messageContactId);
        if (!messageContactModelOptional.isPresent()) {
            log.info("MsgContact not found for update ------> messageContactId: ------- {} ", messageContactId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        var messageContactModel = messageContactModelOptional.get();
        BeanUtils.copyProperties(messageContactDto, messageContactModel);
        messageContactModel = messageContactService.save(messageContactModel);

        log.info("MsgContact updated successfully ------> messageContactId: ------- {} ", messageContactId);
        return ResponseEntity.status(HttpStatus.OK).body(messageContactModel);
    }
   //@PreAuthorize("hasAnyRole('ADMIN')")
   @PatchMapping("/{messageContactId}")
   public ResponseEntity<Object> updateAnsweredStatus(@PathVariable(value = "messageContactId") UUID messageContactId,
                                                      @RequestBody AnsweredTypeWrapper answeredTypeWrapper){
       Optional<MessageContactModel> messageContactModelOptional = messageContactService.findById(messageContactId);
       if (!messageContactModelOptional.isPresent()) {
           log.info("MsgContact not found ------> messageContactId: ------- {} ", messageContactId);
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
       }

       MessageContactModel messageContactModel = messageContactModelOptional.get();
       messageContactModel.setAnswered(answeredTypeWrapper.getNewAnsweredType());
       messageContactService.save(messageContactModel);

       log.info("MsgContact AnsweredType updated successfully ------> messageContactId: ------- {} ", messageContactId);
       return ResponseEntity.status(HttpStatus.OK).body(messageContactModel);
   }

    //@PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/{messageContactId}")
    public ResponseEntity<Object> deleteMsgContact(@PathVariable(value = "messageContactId") UUID messageContactId){
        Optional<MessageContactModel> mensageContactModelOptional = messageContactService.findById(messageContactId);
        if (!mensageContactModelOptional.isPresent()) {
            log.info("MsgContact not found for delete ------> messageContactId: ------- {} ", messageContactId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("MsgContact not found for this Category.");
        }
        messageContactService.delete(mensageContactModelOptional.get());
        log.info("MsgContact deleted successfully ------> messageContactId: ------- {} ", messageContactId);
        return ResponseEntity.status(HttpStatus.OK).body("MsgContact deleted successfully.");
    }
}
