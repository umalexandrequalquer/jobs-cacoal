package umalexandre.empregos.entity;


import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.format.annotation.NumberFormat;
import umalexandre.empregos.dtos.JobDTO;

import java.util.UUID;

@Entity
@Table(name = "tb_jobs")
@Data
@AllArgsConstructor
@NoArgsConstructor


public class JobEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @NotNull
    @Column(unique = true, nullable = false)
    private String titulo;
    @NotNull
    private String descricao;
    @NotNull
    @DecimalMin(value = "1518.00", message = "O valor deve ser maior ou igual = R$:1.518,00")
    @NumberFormat(pattern = "#.###.00")
    private Double valor;
    @ManyToOne()
    @JoinColumn(name = "company_id")
    private CompanyEntity Company;

    public JobEntity(CompanyEntity Company, JobDTO vaga) {
        this.Company = Company;
        this.titulo = vaga.getTitulo();
        this.descricao = vaga.getDescricao();
        this.valor = vaga.getValor();
    }

}
