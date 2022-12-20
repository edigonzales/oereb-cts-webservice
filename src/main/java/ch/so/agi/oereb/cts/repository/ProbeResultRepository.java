package ch.so.agi.oereb.cts.repository;

import ch.so.agi.oereb.cts.entity.ProbeResult;
import jakarta.persistence.Tuple;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProbeResultRepository extends JpaRepository<ProbeResult, Long> {    
    @Transactional
    void deleteByIdentifier(String identifier);
    
    @Transactional(readOnly = true)
    @Query(value="SELECT extract.identifier, extract.service_endpoint, egrid.success AS success_egrid, extract.success AS success_extract, extract.end_time\n"
            + "FROM \n"
            + "(\n"
            + "SELECT DISTINCT ON (foo.identifier, foo.class_name) foo.identifier, foo.service_endpoint, foo.success, foo.class_name, foo.end_time FROM PROBE_RESULT AS foo\n"
            + "LEFT JOIN (SELECT identifier, class_name, success FROM PROBE_RESULT GROUP BY identifier, class_name, success HAVING success IS FALSE) AS bar\n"
            + "ON bar.identifier = foo.identifier\n"
            + "WHERE foo.class_name = 'ch.so.agi.oereb.cts.GetEGRIDProbe'\n"
            + ") AS egrid\n"
            + "LEFT JOIN \n"
            + "(\n"
            + "SELECT DISTINCT ON (foo.identifier, foo.class_name) foo.identifier, foo.service_endpoint, foo.success, foo.class_name, foo.end_time FROM PROBE_RESULT AS foo\n"
            + "LEFT JOIN (SELECT identifier, class_name, success FROM PROBE_RESULT GROUP BY identifier, class_name, success HAVING success IS FALSE) AS bar\n"
            + "ON bar.identifier = foo.identifier\n"
            + "WHERE foo.class_name = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'\n"
            + ") AS extract\n"
            + "ON egrid.identifier = extract.identifier\n"
            + "ORDER BY extract.identifier", nativeQuery=true)
    List<Tuple> getResultSummary();
}


/*
SELECT extract.identifier, extract.service_endpoint, egrid.success AS success_egrid, extract.success AS success_extract, extract.end_time
FROM 
(
SELECT DISTINCT ON (foo.identifier, foo.class_name) foo.identifier, foo.service_endpoint, foo.success, foo.class_name, foo.end_time FROM PROBE_RESULT AS foo
LEFT JOIN (SELECT identifier, class_name, success FROM PROBE_RESULT GROUP BY identifier, class_name, success HAVING success IS FALSE) AS bar
ON bar.identifier = foo.identifier
WHERE foo.class_name = 'ch.so.agi.oereb.cts.GetEGRIDProbe'
) AS egrid
LEFT JOIN 
(
SELECT DISTINCT ON (foo.identifier, foo.class_name) foo.identifier, foo.service_endpoint, foo.success, foo.class_name, foo.end_time FROM PROBE_RESULT AS foo
LEFT JOIN (SELECT identifier, class_name, success FROM PROBE_RESULT GROUP BY identifier, class_name, success HAVING success IS FALSE) AS bar
ON bar.identifier = foo.identifier
WHERE foo.class_name = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'
) AS extract
ON egrid.identifier = extract.identifier
ORDER BY extract.identifier
*/