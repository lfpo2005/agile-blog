package dev.fernando.agileblog.controllers;

import dev.fernando.agileblog.dtos.UpdateRoleDto;
import dev.fernando.agileblog.enums.UserType;
import dev.fernando.agileblog.models.UserModel;
import dev.fernando.agileblog.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

@Log4j2
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/update-role")
public class UpdateRoleController {


    @Autowired
    UserService userService;

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PatchMapping("/admin")
    public ResponseEntity<Object> saveSubscriptionInstructor(@RequestBody @Valid UpdateRoleDto updateRoleDto) {
        Optional<UserModel> userModelOptional = userService.findById(updateRoleDto.getUserId());
        if (!userModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }else {
            var userModel = userModelOptional.get();
            userModel.setUserType(UserType.ADMIN);
            userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
            userService.updateUser(userModel);
            return ResponseEntity.status(HttpStatus.OK).body(userModel);
        }
    }
    
}
