package umalexandre.empregos.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import umalexandre.empregos.repository.CompanyRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailConfirmService {


    @Autowired
    JavaMailSender mailSender;

    private final Map<String, String> verificationCodes = new HashMap<>();
    @Autowired
    private CompanyRepository companyRepository;

    private  String generateVerificationCode() {
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }

    public ResponseEntity<HttpStatus> sendVerificationCode(String email) {
        if(companyRepository.findByEmail(email).isPresent()) {
         return  ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        String code = generateVerificationCode();

        verificationCodes.put(email, code);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Verification Code");
        message.setText("Your verification code is: " + code);

        mailSender.send(message);

        return ResponseEntity.ok().build();
    }

    public Boolean verifyCode( String email, String codeEmailConfirm) {
        String code = verificationCodes.get(email);
        if (code != null && code.equals(codeEmailConfirm)) {
            System.out.println("Email verified");
            verificationCodes.remove(email);
            return true;
        }
        System.out.println("Email not verified");
        return false;
    }


}
