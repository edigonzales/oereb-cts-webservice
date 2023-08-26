package ch.so.agi.oereb.cts.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ch.ehi.basics.settings.Settings;
import ch.so.agi.oereb.cts.ServiceProperties;
import ch.so.agi.oereb.cts.TestSuite;

@Service
public class CtsService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.workDirectory}")
    private String workDirectory;

    @Value("${app.folderPrefix}")
    private String folderPrefix;

    @Autowired
    ServiceProperties serviceProperties;

    public void validate() throws IOException {
        Path outDirectory = Files.createTempDirectory(Paths.get(workDirectory), folderPrefix);            

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'HH:mm:ss");
        String testSuiteTime = Instant.now().atZone(ZoneId.systemDefault()).toLocalDateTime().format(dtf);

        List<Map<String,String>> services = serviceProperties.getServices();
        
        for (Map<String,String> params : services) {
            log.info(params.toString());
            
            Settings settings = new Settings();
            String logFile = Paths.get(outDirectory.toFile().getAbsolutePath(), "cts-" + params.get(TestSuite.PARAM_IDENTIFIER) + ".xtf").toFile().getAbsolutePath();
            settings.setValue(TestSuite.SETTING_LOGFILE, logFile);
            
            settings.setValue(TestSuite.SETTING_TESTSUITE_TIME, testSuiteTime);

            TestSuite testSuite = new TestSuite();
            //testSuite.run(params, settings);

            
            
        }
    }
}
