package ch.so.agi.oereb.cts.repository;

import ch.so.agi.oereb.cts.entity.ProbeResult;
import jakarta.persistence.Tuple;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ProbeResultRepository extends CrudRepository<ProbeResult, Long> {    
    void deleteByIdentifier(String identifier);
    
    @Transactional(readOnly = true)
    List<ProbeResult> findByIdentifierAndClassName(String identifier, String className);
    
    @Transactional(readOnly = true)
    @Query(value="SELECT \n"
            + "    extract.identifier, extract.service_endpoint, versions.success AS success_versions, capabilities.success AS success_capabilities, egrid.success AS success_egrid, extract.success AS success_extract, extract.end_time\n"
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
            + "            class_name = 'ch.so.agi.oereb.cts.GetVersionsProbe'\n"
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
            + "            class_name = 'ch.so.agi.oereb.cts.GetVersionsProbe'\n"
            + "    ) AS foo\n"
            + "    ON foo.identifier = bar.identifier\n"
            + ") AS versions\n"
            + "ON egrid.identifier = versions.identifier\n"
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
            + "            class_name = 'ch.so.agi.oereb.cts.GetCapabilitiesProbe'\n"
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
            + "            class_name = 'ch.so.agi.oereb.cts.GetCapabilitiesProbe'\n"
            + "    ) AS foo\n"
            + "    ON foo.identifier = bar.identifier\n"
            + ") AS capabilities\n"
            + "ON egrid.identifier = capabilities.identifier\n"
            + "WHERE \n"
            + "    extract.identifier IS NOT NULL\n"
            + "    AND \n"
            + "    versions.identifier IS NOT NULL \n"
            + "    AND \n"
            + "    capabilities.identifier IS NOT NULL\n"
            + "ORDER BY \n"
            + "    extract.identifier"
            + "", nativeQuery=true)
    List<Tuple> getResultSummary();
}


/*
SELECT 
    extract.identifier, extract.service_endpoint, versions.success AS success_versions, capabilities.success AS success_capabilities, egrid.success AS success_egrid, extract.success AS success_extract, extract.end_time
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
            class_name = 'ch.so.agi.oereb.cts.GetVersionsProbe'
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
            class_name = 'ch.so.agi.oereb.cts.GetVersionsProbe'
    ) AS foo
    ON foo.identifier = bar.identifier
) AS versions
ON egrid.identifier = versions.identifier
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
            class_name = 'ch.so.agi.oereb.cts.GetCapabilitiesProbe'
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
            class_name = 'ch.so.agi.oereb.cts.GetCapabilitiesProbe'
    ) AS foo
    ON foo.identifier = bar.identifier
) AS capabilities
ON egrid.identifier = capabilities.identifier
WHERE 
    extract.identifier IS NOT NULL
    AND 
    versions.identifier IS NOT NULL 
    AND 
    capabilities.identifier IS NOT NULL
ORDER BY 
    extract.identifier
*/