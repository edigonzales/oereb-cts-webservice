package ch.so.agi.oereb.cts;

import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import ch.so.agi.oereb.cts.service.OerebValidatorService;

@EnableScheduling
@SpringBootApplication
public class OerebCtsWebserviceApplication {

    public static void main(String[] args) {
	    SpringApplication.run(OerebCtsWebserviceApplication.class, args);
	}

    // CommandLineRunner: Anwendung ist fertig gestartet. 
    // Kubernetes: Live aber nicht ready.
    @Bean
    CommandLineRunner init(OerebValidatorService validator, ServiceProperties serviceProperties) {
        return args -> {
            for(Map<String,String> service : serviceProperties.getServices()) {
                String identifier = service.get("identifier");
                String serviceEndpoint = service.get("SERVICE_ENDPOINT");
                
                //validator.validate(identifier, serviceEndpoint, service);
            }
        };
    }
}
