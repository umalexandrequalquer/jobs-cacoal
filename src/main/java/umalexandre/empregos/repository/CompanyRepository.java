package umalexandre.empregos.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import umalexandre.empregos.entity.CompanyEntity;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {



    Optional<CompanyEntity> findByEmail(String email);

    @Query("""
    SELECT c FROM CompanyEntity c 
    LEFT JOIN c.vagas j 
    WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :query, '%'))
       OR LOWER(j.titulo) LIKE LOWER(CONCAT('%', :query, '%'))""")
    Page<CompanyEntity> searchByQuery(@Param("query") String query, Pageable pageable);

    

}
