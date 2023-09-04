package ch.so.agi.oereb.cts.jsf;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ch.so.agi.oereb.cts.ProbeResult;
import ch.so.agi.oereb.cts.SummaryResult;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class DetailView {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ObjectMapper objectMapper;

    private List<ProbeResult> results;

    @PostConstruct
    public void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> paramMap = context.getExternalContext().getRequestParameterMap();
        String identifier = paramMap.get("identifier");
        String probe = paramMap.get("probe");

        if (identifier == null || probe == null) {
            return;
        }

        String probeClassName = null;
        switch (probe) {
        case "versions":
            probeClassName = "ch.so.agi.oereb.cts.GetVersionsProbe";
            break;
        case "capabilities":
            probeClassName = "ch.so.agi.oereb.cts.GetCapabilitiesProbe";
            break;
        case "egrid":
            probeClassName = "ch.so.agi.oereb.cts.GetEGRIDProbe";
            break;
        case "extract":
            probeClassName = "ch.so.agi.oereb.cts.GetExtractByIdProbe";
            break;
        default:
            throw new IllegalArgumentException("Invalid probe name: " + probe);
        }

        log.info("**** " + identifier);
        log.info("**** " + probeClassName);

        String query = getQuery();

        results = jdbcTemplate.query(query, new RowMapper<ProbeResult>() {
            @Override
            public ProbeResult mapRow(ResultSet rs, int rowNum) throws SQLException {
                String probeResults = rs.getString("probe_results");
                ProbeResult probeResult = null;
                try {
                    probeResult = objectMapper.readValue(probeResults, ProbeResult.class);
                    
                    
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
                return probeResult;
            }
        });

        log.info(results.toString());

    }

    public String getResults() {
        return "fubar";
    }

    private String getQuery() {
        // starttime wird nur für die Sortierung benötigt.
        String query = """
WITH probe AS 
(
    SELECT 
        t_id, classname, identifier, serviceendpoint, request, testsuitetime, starttime
    FROM 
        agi_oereb_cts_v1.proberesult 
    WHERE 
        identifier = 'AG'
)
SELECT 
    json_build_object('starttime', probe.starttime, 'request', probe.request, 'checkResults', json_agg(c.* ORDER BY t_seq)) AS probe_results
FROM 
    probe
    LEFT JOIN agi_oereb_cts_v1.checkresult AS c
    ON c.proberesult_checkresults = probe.t_id
WHERE 
    probe.classname = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'
GROUP BY 
    probe.request, probe.starttime
ORDER BY 
    probe.starttime ASC                
                                """;
        return query;
    }

//    private String getQuery() {
//        String query = """
//WITH latest AS 
//(
//    SELECT 
//        t_id, classname, identifier, serviceendpoint, request, max(testsuitetime) AS testsuitetime
//    FROM 
//        agi_oereb_cts_v1.proberesult 
//    WHERE 
//        identifier = 'TG'
//    GROUP BY 
//        t_id, classname, identifier, serviceendpoint, request  
//)
//
//
//SELECT 
//    *
//FROM 
//    latest
//    --LEFT JOIN agi_oereb_cts_v1.checkresult AS c
//    --ON c.proberesult_checkresults = latest.t_id
//WHERE 
//    latest.classname = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'
//
//                """;
//        return query;
//    }
}