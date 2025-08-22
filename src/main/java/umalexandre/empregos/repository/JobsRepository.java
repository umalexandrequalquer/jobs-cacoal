package umalexandre.empregos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import umalexandre.empregos.entity.JobEntity;

import java.util.UUID;

public interface JobsRepository extends JpaRepository<JobEntity, UUID> {


    @Override
    Page<JobEntity> findAll(Pageable pageable);
}
