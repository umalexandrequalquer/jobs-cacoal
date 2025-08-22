package umalexandre.empregos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import umalexandre.empregos.service.EmailConfirmService;


@RestController
@RequestMapping("/email-confirm")
public class EmailConfirmController {

    @Autowired
   private EmailConfirmService emailConfirmService;


    @PostMapping("/send-code")
    public ResponseEntity<HttpStatus> sendVerificationCode(@RequestParam String email) {
        return emailConfirmService.sendVerificationCode(email);

    }

    @PostMapping("/verify-code")
    public ResponseEntity<HttpStatus> verifyCode(@RequestParam String email, @RequestParam String codeEmailConfirm) {
        return ResponseEntity.ok(emailConfirmService.verifyCode(email, codeEmailConfirm) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED);
    }

}


