package ch.so.agi.oereb.cts.repository;

import ch.so.agi.oereb.cts.entity.ProbeResult;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProbeResultRepository extends JpaRepository<ProbeResult, Long> {
    
    @Transactional
    void deleteByIdentifier(String identifier);
}
