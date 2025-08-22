package umalexandre.empregos.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.br.CNPJ;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUpdateDTO {
    @NotNull
    @Column(unique = true)
    private String name;
    @CNPJ
    private String cnpj;
    @Email(message = "Email deve ser válido")
    private String email;
    private String password;
    @NotNull
    @Column(unique = true)
    private String phoneNumber;
    @NotNull
    @Column(unique = true)
    private String address;

}
