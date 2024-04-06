package ch.so.agi.oereb.cts.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
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
    
}
