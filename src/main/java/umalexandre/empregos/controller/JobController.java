package umalexandre.empregos.controller;


import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import umalexandre.empregos.dtos.JobDTO;
import umalexandre.empregos.dtos.JobUpdateDTO;
import umalexandre.empregos.service.JobService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/jobs/")
@Validated
public class JobController {

    @Autowired
    private JobService jobService;


    @GetMapping("alljobs")
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return jobService.getAllJobs();
    }


    @PostMapping("{empresaId}/vagas")
    public ResponseEntity<HttpStatus> newJob(@NotNull @PathVariable UUID empresaId,@RequestBody JobDTO jobDTO , @AuthenticationPrincipal Jwt jwt) {
        jobService.newJob(empresaId, jobDTO, jwt);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("{empresaId}/vagas/{idVaga}")
    public ResponseEntity<HttpStatus> updateJob(
            @PathVariable UUID empresaId,
            @PathVariable UUID idVaga,
            @Valid @RequestBody JobUpdateDTO jobUpdateDTO,
            @AuthenticationPrincipal Jwt jwt) {

        jobService.updateJob(empresaId, idVaga, jobUpdateDTO,jwt);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{empresaId}/vagas/{idVaga}")
    public ResponseEntity<HttpStatus> deleteJob(@NotNull @PathVariable UUID empresaId, @PathVariable UUID idVaga, @AuthenticationPrincipal Jwt jwt) {
        jobService.deleteJob(empresaId, idVaga, jwt);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }}
