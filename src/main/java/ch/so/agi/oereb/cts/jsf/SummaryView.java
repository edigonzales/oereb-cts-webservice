package ch.so.agi.oereb.cts.jsf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ch.so.agi.oereb.cts.SummaryResult;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class SummaryView {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    DataSource dataSource;
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    private List<SummaryResult> results;

    @PostConstruct
    public void init() {
        String query = getQuery();
        results = jdbcTemplate.query(query, new RowMapper<SummaryResult>() {

            @Override
            public SummaryResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                String identifier = rs.getString("identifier");
                String serviceEndpoint = rs.getString("serviceendpoint");
                boolean successVersions = rs.getBoolean("success_versions");
                boolean successCapabilities = rs.getBoolean("success_capabilities");
                boolean successEgrid = rs.getBoolean("success_egrid");
                boolean successExtract = rs.getBoolean("success_extract");
                LocalDateTime testSuiteTime = rs.getTimestamp("testsuitetime").toLocalDateTime();
                LocalDateTime startTime = rs.getTimestamp("starttime").toLocalDateTime();
                
                SummaryResult result = new SummaryResult(
                        identifier, 
                        serviceEndpoint, 
                        successVersions, 
                        successCapabilities,
                        successEgrid,
                        successExtract,
                        testSuiteTime,
                        startTime
                        );
                return result;
            }
        });
    }
    
    public List<SummaryResult> getResults() {
        return results;
    }

    private String getQuery() {
        String query = """
WITH getversions AS 
(
    SELECT
        identifier,  serviceendpoint, bool_and(success) AS success_versions, testsuitetime, max(starttime) AS starttime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetVersionsProbe'
    GROUP BY 
        identifier, testsuitetime, serviceendpoint 
)
,
getcapabilities AS 
(
    SELECT
        identifier, bool_and(success) AS success_capabilities, testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetCapabilitiesProbe'
    GROUP BY 
        identifier, testsuitetime  
)
,
getegrid AS 
(
    SELECT
        identifier, bool_and(success) AS success_egrid, testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetEGRIDProbe'
    GROUP BY 
        identifier, testsuitetime  
)
,
getextract AS 
(
    SELECT
        identifier, bool_and(success) AS success_extract, testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'
    GROUP BY 
        identifier, testsuitetime  
)
SELECT 
    getversions.identifier,
    getversions.serviceendpoint,
    getversions.testsuitetime,
    getversions.starttime,
    getversions.success_versions,
    getcapabilities.success_capabilities,
    getegrid.success_egrid,
    getextract.success_extract
FROM 
    getversions
    LEFT JOIN getcapabilities
    ON getversions.identifier = getcapabilities.identifier
    LEFT JOIN getegrid
    ON getversions.identifier = getegrid.identifier
    LEFT JOIN getextract
    ON getversions.identifier = getextract.identifier
ORDER BY 
    getversions.identifier ASC                
                """;
        return query;
    }
}
