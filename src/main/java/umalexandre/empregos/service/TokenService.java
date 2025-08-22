package umalexandre.empregos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import umalexandre.empregos.dtos.LoginRequestDTO;
import umalexandre.empregos.dtos.LoginResponseDTO;
import umalexandre.empregos.repository.CompanyRepository;

import java.time.Instant;

@Service()
public class TokenService {

    @Autowired
     private CompanyRepository companyRepository;
    @Autowired
   private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private JwtEncoder jwtEncode;

    public ResponseEntity<LoginResponseDTO> getToken(LoginRequestDTO loginrequest) {
        var Userlogin = companyRepository.findByEmail(loginrequest.email());
        if (!companyRepository.findByEmail(loginrequest.email()).isPresent()) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(Userlogin.isEmpty() || !Userlogin.get().isLoginCorrect(loginrequest, bCryptPasswordEncoder)){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var now = Instant.now();

        var expiresIn = 600L;

        var clamis = JwtClaimsSet.builder()
                .issuer("https://github.com/umalexandre/empregos")
                .subject(Userlogin.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();
        var jwtValue = jwtEncode.encode(JwtEncoderParameters.from(clamis)).getTokenValue();

        return ResponseEntity.ok( new LoginResponseDTO(jwtValue, expiresIn));
    }



}
