package ch.so.agi.oereb.cts.ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import ch.so.agi.oereb.cts.ws.service.CtsService;

@EnableScheduling
@SpringBootApplication
public class OerebCtsWebserviceApplication {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.database.createOnStartup}")
    private boolean databaseCreateOnStartup;

    @Value("${app.database.testQuery}")
    private String databaseTestQuery;

    @Value("${app.testSuite.validateOnStartup}")
    private boolean testSuiteValidateOnStartup;

    @Autowired
    DataSource dataSource;

    public static void main(String[] args) {
	    SpringApplication.run(OerebCtsWebserviceApplication.class, args);
	}

    // CommandLineRunner: Anwendung ist fertig gestartet. 
    // Kubernetes: Live aber nicht ready.
    @Bean
    CommandLineRunner init(CtsService ctsService) {
        return args -> {    
            if (databaseCreateOnStartup) {
                try (Connection con = dataSource.getConnection(); Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(databaseTestQuery)) {
                    log.info("Database schema exists already.");
                } catch (SQLException e) {
                    log.info("Database schema does not exists. Schema will be created.");
                    try (Connection con = dataSource.getConnection(); Statement stmt = con.createStatement()) {
                        String query = Util.loadString("oereb-cts-postgres-dataset.sql");
                        stmt.execute(query);
                        log.info("Schema created.");
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }                
            }

            if (testSuiteValidateOnStartup) {
                log.info("Validate on startup.");
                ctsService.validate();
            }
        };
    }
}
