package umalexandre.empregos.service;


import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import umalexandre.empregos.dtos.JobDTO;
import umalexandre.empregos.dtos.JobUpdateDTO;
import umalexandre.empregos.entity.JobEntity;
import umalexandre.empregos.repository.CompanyRepository;
import umalexandre.empregos.repository.JobsRepository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class JobService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private JobsRepository jobsRepository;

    public ResponseEntity<List<JobDTO>> getAllJobs() {

        return ResponseEntity.ok(jobsRepository.findAll().stream().map(JobDTO::new).collect(Collectors.toList()));
    }


    @Transactional
    public ResponseEntity<HttpStatus> newJob(UUID id, JobDTO vagaDto, Jwt jwt) {
        if(!id.equals(UUID.fromString(jwt.getSubject()))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if(!companyRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        companyRepository.findById(id).ifPresent(company -> {
            JobEntity vaga = new JobEntity(company,vagaDto);
            jobsRepository.save(vaga);});
        return  ResponseEntity.status(HttpStatus.OK).build();
    }

    @Transactional
    public ResponseEntity<HttpStatus> updateJob(UUID idcompany, UUID idjob, @Valid JobUpdateDTO jobDTO , Jwt jwt) {
        UUID checkIdToken = UUID.fromString(jwt.getSubject());

        if(!companyRepository.existsById(idcompany) || !jobsRepository.existsById(idjob)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        if(!idcompany.equals(checkIdToken)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        JobEntity job = jobsRepository.getReferenceById(idjob);

        if(job.getCompany().getId().equals(checkIdToken)){
            if(jobDTO.getTitulo() != null && !jobDTO.getTitulo().isBlank()){
                job.setTitulo(jobDTO.getTitulo());
            };
            if (jobDTO.getDescricao() != null && !jobDTO.getDescricao().isBlank()){
                job.setDescricao(jobDTO.getDescricao());
            };
            if(jobDTO.getValor() != null){
                job.setValor(jobDTO.getValor());
            }
        }

        jobsRepository.save(job);

        return ResponseEntity.ok().build();
    }
    @Transactional
    public void deleteJob(UUID idEmpresa, UUID idVaga, Jwt jwt) {
        companyRepository.findById(idEmpresa).ifPresent(empresa -> {
            empresa.getVagas().removeIf(vaga -> vaga.getId().equals(idVaga));
            companyRepository.save(empresa);
        });
    }

}
