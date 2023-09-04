package ch.so.agi.oereb.cts.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.postgresql.PGProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.ehi.basics.settings.Settings;
import ch.ehi.ili2db.base.Ili2db;
import ch.ehi.ili2db.base.Ili2dbException;
import ch.ehi.ili2db.gui.Config;
import ch.ehi.ili2pg.PgMain;
import ch.so.agi.oereb.cts.ServiceProperties;
import ch.so.agi.oereb.cts.TestSuite;
import ch.so.agi.oereb.cts.Util;
import jakarta.annotation.PostConstruct;

@Service
public class CtsService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.workDirectory}")
    private String workDirectory;

    @Value("${app.folderPrefix}")
    private String folderPrefix;

    @Value("${app.database.schema}")
    private String databaseSchema;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    @Autowired
    ServiceProperties serviceProperties;

//    @Autowired
//    DataSource dataSource;
    
    private String dbHost;
    private String dbPort;
    private String dbDatabase;
    
    @PostConstruct
    public void init() {
        Properties props = org.postgresql.Driver.parseURL(datasourceUrl, null);
        dbHost = props.getProperty(PGProperty.PG_HOST.getName());
        dbPort = props.getProperty(PGProperty.PG_PORT.getName());
        dbDatabase = props.getProperty(PGProperty.PG_DBNAME.getName());
    }

    public void validate() throws IOException {
        Path outDirectory = Files.createTempDirectory(Paths.get(workDirectory), folderPrefix);            

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss");
        String testSuiteTime = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime().format(dtf);

        List<Map<String,String>> services = serviceProperties.getServices();
        
        for (Map<String,String> params : services) {
            log.info("params: {}", params);
            
            Settings settings = new Settings();
            String logFile = Paths.get(outDirectory.toFile().getAbsolutePath(), "cts-" + params.get(TestSuite.PARAM_IDENTIFIER) + ".xtf").toFile().getAbsolutePath();
            settings.setValue(TestSuite.SETTING_LOGFILE, logFile);
            log.debug("logFile: {}", logFile);
            
            // Die Zeit wird ausserhalb der Forschleife instanziert. Sie muss für alle
            // zu prüfenden Services innerhalb eines Test-Runs gleich sein.
            // Das ermöglicht einfachere Auswertungen (z.B. "latest" oder vorletztes etc).
            settings.setValue(TestSuite.SETTING_TESTSUITE_TIME, testSuiteTime);

            TestSuite testSuite = new TestSuite();
            testSuite.run(params, settings);
            
            File iliFile = Util.copyResourceToTmpDir("ili/SO_AGI_OEREB_CTS_20230819.ili"); // Die ili-Datei liegt in der Library.
            log.debug("iliFile: {}", iliFile.getAbsolutePath());

            Config config = new Config();
            new PgMain().initConfig(config);
            config.setFunction(Config.FC_REPLACE);
            //config.setValidation(false);
            config.setModeldir(iliFile.getParent()+";https://geo.so.ch/models;http://models.geo.admin.ch");
            config.setModels("SO_AGI_OEREB_CTS_20230819");
            config.setBasketHandling(Config.BASKET_HANDLING_READWRITE);
            System.out.println("**: "+params.get(TestSuite.PARAM_IDENTIFIER));
            config.setDatasetName(params.get(TestSuite.PARAM_IDENTIFIER));
            System.out.println("**: "+config.getDatasetName());
                        
            config.setDbhost(dbHost);
            config.setDbport(dbPort);
            config.setDbdatabase(dbDatabase);
            config.setDbschema(databaseSchema);
            config.setDbusr(datasourceUsername);
            config.setDbpwd(datasourcePassword);
            config.setDburl(datasourceUrl);
            config.setItfTransferfile(false);
            config.setXtffile(logFile);  

            try {
                Ili2db.readSettingsFromDb(config); // Sonst funktioniert dataset nicht.
                Ili2db.run(config, null);
            } catch (Ili2dbException e) {
                log.warn(e.getMessage());
            }
        }
    }
}
