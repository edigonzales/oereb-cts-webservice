package ch.so.agi.oereb.cts.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;

import ch.so.agi.oereb.cts.ws.service.CtsService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RestController
@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.workDirectory}")
    private String workDirectory;

    @Value("${app.folderPrefix}")
    private String folderPrefix;

    @Autowired
    CtsService ctsService;
        
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<String>("oereb-cts-webservice", HttpStatus.OK);
    }

    @Scheduled(cron="${app.checkCronExpression}")
    //@Scheduled(cron="0 */4 * * * *")
    private void checkRepos() throws IOException {
        log.info("Validating by scheduler.");
        ctsService.validate();
    }
    
    @Scheduled(cron="0 0/6 * * * *")
    private void cleanUp() {    
        log.debug("Deleting files from previous validation runs...");
        // workDirectory und folderPrefix werden in den Properties gesteuert und
        // und müssen gleich sein wie in der cts library.
        // Falls sie nicht identisch sind, müssen sie entweder in den Properties
        // separat behandelt werden oder hier hardcodiert werden.
        File[] tmpDirs = Paths.get(workDirectory).toFile().listFiles();
        if(tmpDirs!=null) {
            for (java.io.File tmpDir : tmpDirs) {
                if (tmpDir.getName().startsWith(folderPrefix)) {
                    try {
                        FileTime creationTime = (FileTime) Files.getAttribute(Paths.get(tmpDir.getAbsolutePath()), "creationTime");                    
                        Instant now = Instant.now();
                        
                        long fileAge = now.getEpochSecond() - creationTime.toInstant().getEpochSecond();
                        if (fileAge > 60*60*4) {
                            log.info("deleting {}", tmpDir.getAbsolutePath());
                            FileSystemUtils.deleteRecursively(tmpDir);
                        }
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }
    }
}
