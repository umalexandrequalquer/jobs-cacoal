package umalexandre.empregos.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import umalexandre.empregos.entity.CompanyEntity;

import java.util.List;
import java.util.stream.Collectors;
@Setter
@Getter
public class HomeDTO {

    private String name;
    private String cnpj;
    private String email;
    private String phoneNumber;
    private String address;
    private List<JobDTO> vagas; //


    public HomeDTO(@NotNull CompanyEntity empresa) {
        this.name = empresa.getName();
        this.cnpj = empresa.getCnpj();
        this.email = empresa.getEmail();
        this.phoneNumber = empresa.getPhoneNumber();
        this.address = empresa.getAddress();
        this.vagas = empresa.getVagas().stream()
                .map(JobDTO::new)
                .collect(Collectors.toList());
    }



}
