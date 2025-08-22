package umalexandre.empregos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.MultiMap;
import org.hibernate.validator.constraints.br.CNPJ;
import org.springframework.security.crypto.password.PasswordEncoder;
import umalexandre.empregos.dtos.CompanyDTO;
import umalexandre.empregos.dtos.LoginRequestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_company")
@NoArgsConstructor
@Getter
@Setter

public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotBlank(message = "O campo nome da empresa é obrigatório")
    @Column(unique = true, nullable = false)
    private String name;
    @CNPJ
    @Column(unique = true, nullable = false)
    private String cnpj;
    @Email
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @Column(unique = true, nullable = false)
    private String address;

    @OneToMany(mappedBy = "Company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JobEntity> vagas = new ArrayList<>();

    public CompanyEntity(CompanyDTO companyDTO) {
        this.name = companyDTO.getName();
        this.cnpj = companyDTO.getCnpj();
        this.email = companyDTO.getEmail();
        this.password = companyDTO.getPassword();
        this.phoneNumber = companyDTO.getPhoneNumber();
        this.address = companyDTO.getAddress();
    }

    public boolean isLoginCorrect(LoginRequestDTO loginRequestDTO, PasswordEncoder passwordEncoder){
        return passwordEncoder.matches(loginRequestDTO.password(), this.password);

    }


}
