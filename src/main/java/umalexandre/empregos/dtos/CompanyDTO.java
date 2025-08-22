package umalexandre.empregos.dtos;

import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.jetbrains.annotations.NotNull;
import umalexandre.empregos.entity.CompanyEntity;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompanyDTO {
    @NotNull
    @Column(unique = true, nullable = false)
    private String name;
    @CNPJ
    private String cnpj;
    @Email
    private String email;
    @NotNull
    @Column(nullable = false)
    private String password;
    @NotNull
    @Column(unique = true, nullable = false)
    private String phoneNumber;
    @NotNull
    @Column(unique = true, nullable = false)
    private String address;
    private List<JobDTO> vagas; //

    public CompanyDTO(@NotNull @Valid CompanyEntity empresa) {
        this.name = empresa.getName();
        this.cnpj = empresa.getCnpj();
        this.email = empresa.getEmail();
        this.password = empresa.getPassword();
        this.phoneNumber = empresa.getPhoneNumber();
        this.address = empresa.getAddress();

        this.vagas = empresa.getVagas().stream()
                .map(JobDTO::new)
                .collect(Collectors.toList());
    }


}
