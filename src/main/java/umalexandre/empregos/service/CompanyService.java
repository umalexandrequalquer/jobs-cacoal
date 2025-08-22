package umalexandre.empregos.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import umalexandre.empregos.dtos.CompanyDTO;
import umalexandre.empregos.dtos.CompanyUpdateDTO;
import umalexandre.empregos.dtos.HomeDTO;
import umalexandre.empregos.entity.CompanyEntity;
import umalexandre.empregos.repository.CompanyRepository;
import umalexandre.empregos.repository.JobsRepository;

import java.util.UUID;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final JobsRepository jobsRepository;
    private  final BCryptPasswordEncoder PasswordEncoder;
    private final EmailConfirmService emailConfirmService;


    public CompanyService(
                          BCryptPasswordEncoder bCryptPasswordEncoder,
                          CompanyRepository companyRepository,
                          JobsRepository jobsRepository,
                          EmailConfirmService emailConfirmService)
    {
                         this.companyRepository = companyRepository;
                         this.jobsRepository = jobsRepository;
                         this.PasswordEncoder = bCryptPasswordEncoder;
                         this.emailConfirmService = emailConfirmService;
    }


    public Page<HomeDTO> homeDTO(String query, Pageable pageable) {
        if (query == null || query.isBlank()) {
            return companyRepository.findAll(pageable).map(HomeDTO::new);
        } else {
            return companyRepository.searchByQuery(query, pageable).map(HomeDTO::new);
        }
    }

    public ResponseEntity<HomeDTO> getCompanyByEmail(String email) {
        CompanyEntity empresa = companyRepository.findByEmail(email).get();
        return ResponseEntity.ok(new HomeDTO(empresa));
    }


    @Transactional
    public ResponseEntity<HttpStatus> makeEmpresa(@NotNull @Valid CompanyDTO companyDTO, String codeEmailConfirm) {
        if(companyRepository.findByEmail(companyDTO.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }


        if (!emailConfirmService.verifyCode(companyDTO.getEmail(), codeEmailConfirm)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }


        CompanyEntity company = new CompanyEntity(companyDTO);
        company.setPassword(PasswordEncoder.encode(companyDTO.getPassword()));
        companyRepository.save(company);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    public ResponseEntity<CompanyDTO> GetEmpresaDTO(@NotNull UUID id, @NotNull Jwt jwt) {
        if(id.equals(UUID.fromString(jwt.getSubject())) && companyRepository.existsById(id)) {
            return ResponseEntity.ok(new CompanyDTO(companyRepository.findById(id).get()));
            
        } else if (!id.equals(UUID.fromString(jwt.getSubject()))) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }


    @Transactional
    public ResponseEntity<HttpStatus> companyUpdateDTO(UUID id, CompanyUpdateDTO companyUpdateDTO, Jwt jwt) {
        if (!companyRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if (!id.equals(UUID.fromString(jwt.getSubject()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        CompanyEntity empresa = companyRepository.findById(id).get();

        if (companyUpdateDTO.getName() != null && !companyUpdateDTO.getName().isEmpty()) {
            empresa.setName(companyUpdateDTO.getName());
        }
        if (companyUpdateDTO.getEmail() != null && !companyUpdateDTO.getEmail().isEmpty()) {
            empresa.setEmail(companyUpdateDTO.getEmail());
        }
        if (companyUpdateDTO.getPassword() != null && !companyUpdateDTO.getPassword().isEmpty()) {
            empresa.setPassword(PasswordEncoder.encode(companyUpdateDTO.getPassword()));
        }
        if (companyUpdateDTO.getPhoneNumber() != null && !companyUpdateDTO.getPhoneNumber().isEmpty()) {
            empresa.setPhoneNumber(companyUpdateDTO.getPhoneNumber());
        }
        if (companyUpdateDTO.getAddress() != null && !companyUpdateDTO.getAddress().isEmpty()) {
            empresa.setAddress(companyUpdateDTO.getAddress());
        }
        companyRepository.save(empresa);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @Transactional
    public void deleteCompany(UUID id, Jwt jwt) {
        if (!companyRepository.existsById(id)) {
            throw new RuntimeException("Empresa não encontrada com ID: " + id);
        }
        companyRepository.deleteById(id);
    }
}
