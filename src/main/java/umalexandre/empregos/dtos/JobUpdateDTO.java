package umalexandre.empregos.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JobUpdateDTO {
    @NotNull
    @Column(unique = true,nullable = false)
    private String titulo;
    @NotNull
    @Size(min = 10, max = 500)
    @Column(columnDefinition = "varchar(500)")
    private String descricao;
    @DecimalMin(value = "1518.00", message = "O valor deve ser maior ou igual = R$:1.518,00")
    @NotNull
    private Double Valor;

}
