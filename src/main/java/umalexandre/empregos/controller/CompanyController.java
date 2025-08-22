package umalexandre.empregos.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umalexandre.empregos.dtos.*;
import umalexandre.empregos.service.CompanyService;

import java.util.UUID;

@RestController
@RequestMapping("/api/company")
@Validated
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("make-company")
    public ResponseEntity<HttpStatus> makeCompany(@Valid @RequestBody CompanyDTO companyDTo, @RequestParam String codeEmailConfirm) {
        return  companyService.makeEmpresa(companyDTo, codeEmailConfirm);
    }
    @PutMapping("update/{id}")
    public ResponseEntity<HttpStatus> updateCompany(
            @PathVariable UUID id,
            @RequestBody CompanyUpdateDTO companyUpdateDTO,
            @AuthenticationPrincipal Jwt jwt
    )
    {
        companyService.companyUpdateDTO(id, companyUpdateDTO, jwt);
        return ResponseEntity.ok().body(HttpStatus.ACCEPTED);
    }
    @GetMapping("find/{id}")
    public ResponseEntity<CompanyDTO> GetCompanyId(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        return companyService.GetEmpresaDTO(id, jwt);
    }


    @GetMapping("alljobscompany/{email}")
    public ResponseEntity<HomeDTO> allJobsByCompany(@PathVariable String email) {
        return companyService.getCompanyByEmail(email);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteCompany(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        companyService.deleteCompany(id, jwt);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
