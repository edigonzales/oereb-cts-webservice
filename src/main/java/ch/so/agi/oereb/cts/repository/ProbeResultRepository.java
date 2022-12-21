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
    List<ProbeResult> findByIdentifierAndClassName(String identifier, String className);
    
    @Transactional(readOnly = true)
    @Query(value="SELECT \n"
            + "    extract.identifier, extract.service_endpoint, egrid.success AS success_egrid, extract.success AS success_extract, extract.end_time\n"
            + "FROM \n"
            + "(\n"
            + "    SELECT \n"
            + "        foo.identifier, \n"
            + "        foo.service_endpoint, \n"
            + "        CASE \n"
            + "            WHEN bar.success IS NULL THEN TRUE\n"
            + "            ELSE FALSE \n"
            + "        END AS success,\n"
            + "        foo.class_name, \n"
            + "        foo.end_time\n"
            + "    FROM \n"
            + "    (\n"
            + "        SELECT \n"
            + "            identifier, class_name, success \n"
            + "        FROM \n"
            + "            PROBE_RESULT\n"
            + "        WHERE \n"
            + "            class_name = 'ch.so.agi.oereb.cts.GetEGRIDProbe'\n"
            + "        GROUP BY \n"
            + "            identifier, class_name, success \n"
            + "        HAVING \n"
            + "            success IS FALSE    \n"
            + "    ) AS bar \n"
            + "    RIGHT JOIN \n"
            + "    (\n"
            + "        SELECT DISTINCT ON (identifier) \n"
            + "            identifier, service_endpoint, success, class_name, end_time \n"
            + "        FROM \n"
            + "            PROBE_RESULT\n"
            + "        WHERE \n"
            + "            class_name = 'ch.so.agi.oereb.cts.GetEGRIDProbe'\n"
            + "    ) AS foo\n"
            + "    ON foo.identifier = bar.identifier\n"
            + ") AS egrid\n"
            + "LEFT JOIN \n"
            + "(\n"
            + "    SELECT \n"
            + "        foo.identifier, \n"
            + "        foo.service_endpoint, \n"
            + "        CASE \n"
            + "            WHEN bar.success IS NULL THEN TRUE\n"
            + "            ELSE FALSE \n"
            + "        END AS success,\n"
            + "        foo.class_name, \n"
            + "        foo.end_time\n"
            + "    FROM \n"
            + "    (\n"
            + "        SELECT \n"
            + "            identifier, class_name, success \n"
            + "        FROM \n"
            + "            PROBE_RESULT\n"
            + "        WHERE \n"
            + "            class_name = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'\n"
            + "        GROUP BY \n"
            + "            identifier, class_name, success \n"
            + "        HAVING \n"
            + "            success IS FALSE    \n"
            + "    ) AS bar \n"
            + "    RIGHT JOIN \n"
            + "    (\n"
            + "        SELECT DISTINCT ON (identifier) \n"
            + "            identifier, service_endpoint, success, class_name, end_time \n"
            + "        FROM \n"
            + "            PROBE_RESULT\n"
            + "        WHERE \n"
            + "            class_name = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'\n"
            + "    ) AS foo\n"
            + "    ON foo.identifier = bar.identifier\n"
            + ") AS extract\n"
            + "ON egrid.identifier = extract.identifier\n"
            + "WHERE \n"
            + "    extract.identifier IS NOT NULL\n"
            + "ORDER BY \n"
            + "    extract.identifier\n"
            + "", nativeQuery=true)
    List<Tuple> getResultSummary();
}


/*
SELECT 
    extract.identifier, extract.service_endpoint, egrid.success AS success_egrid, extract.success AS success_extract, extract.end_time
FROM 
(
    SELECT 
        foo.identifier, 
        foo.service_endpoint, 
        CASE 
            WHEN bar.success IS NULL THEN TRUE
            ELSE FALSE 
        END AS success,
        foo.class_name, 
        foo.end_time
    FROM 
    (
        SELECT 
            identifier, class_name, success 
        FROM 
            PROBE_RESULT
        WHERE 
            class_name = 'ch.so.agi.oereb.cts.GetEGRIDProbe'
        GROUP BY 
            identifier, class_name, success 
        HAVING 
            success IS FALSE    
    ) AS bar 
    RIGHT JOIN 
    (
        SELECT DISTINCT ON (identifier) 
            identifier, service_endpoint, success, class_name, end_time 
        FROM 
            PROBE_RESULT
        WHERE 
            class_name = 'ch.so.agi.oereb.cts.GetEGRIDProbe'
    ) AS foo
    ON foo.identifier = bar.identifier
) AS egrid
LEFT JOIN 
(
    SELECT 
        foo.identifier, 
        foo.service_endpoint, 
        CASE 
            WHEN bar.success IS NULL THEN TRUE
            ELSE FALSE 
        END AS success,
        foo.class_name, 
        foo.end_time
    FROM 
    (
        SELECT 
            identifier, class_name, success 
        FROM 
            PROBE_RESULT
        WHERE 
            class_name = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'
        GROUP BY 
            identifier, class_name, success 
        HAVING 
            success IS FALSE    
    ) AS bar 
    RIGHT JOIN 
    (
        SELECT DISTINCT ON (identifier) 
            identifier, service_endpoint, success, class_name, end_time 
        FROM 
            PROBE_RESULT
        WHERE 
            class_name = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'
    ) AS foo
    ON foo.identifier = bar.identifier
) AS extract
ON egrid.identifier = extract.identifier
WHERE 
    extract.identifier IS NOT NULL
ORDER BY 
    extract.identifier
*/