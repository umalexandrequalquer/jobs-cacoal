package umalexandre.empregos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import umalexandre.empregos.dtos.LoginRequestDTO;
import umalexandre.empregos.dtos.LoginResponseDTO;
import umalexandre.empregos.service.TokenService;


@RestController()
public class TokenController {
    @Autowired
    TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> Login(@RequestBody LoginRequestDTO loginrequest) {
        return tokenService.getToken(loginrequest);
    }


}
