package ch.so.agi.oereb.cts.repository;

import ch.so.agi.oereb.cts.entity.ProbeResult;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProbeResultRepository extends JpaRepository<ProbeResult, Long> {
    
    @Transactional
    void deleteByIdentifier(String identifier);
    
//    @Override
//    @Transactional
//    @Query(value="SELECT id, identifier, class_name, description, service_endpoint, success, message, request, status_code, start_time, end_time, processing_time_secs, result_file_location FROM probe_result r", nativeQuery=true)
//    List<ProbeResult> findAll();
}


/*
SELECT DISTINCT ON (foo.identifier, foo.class_name) foo.identifier, foo.success, foo.class_name, foo.end_time FROM PROBE_RESULT AS foo
LEFT JOIN (SELECT identifier AS ident, class_name, success FROM PROBE_RESULT GROUP BY identifier, class_name, success HAVING success IS FALSE) AS bar
ON bar.ident = foo.identifier
*/