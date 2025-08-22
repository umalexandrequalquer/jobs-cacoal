package umalexandre.empregos.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import umalexandre.empregos.entity.JobEntity;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JobDTO {
    private UUID id;
    @NotNull
    @NotBlank()
    private String titulo;
    @NotBlank()
    @NotNull
    @Size(min = 10, max = 500)
    @Column(columnDefinition = "varchar(500)")
    private String descricao;
    @DecimalMin(value = "1518.00", message = "O valor deve ser maior ou igual = R$:1.518,00")
    private Double valor;

    public JobDTO(JobEntity vaga) {
        this.id = vaga.getId();
        this.titulo = vaga.getTitulo();
        this.descricao = vaga.getDescricao();
        this.valor = vaga.getValor();
    }
}
