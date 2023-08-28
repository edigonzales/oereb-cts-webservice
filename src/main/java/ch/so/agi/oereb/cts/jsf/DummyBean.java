package ch.so.agi.oereb.cts.jsf;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component("dummy")
public class DummyBean {
    
    public String getText() {
        return "Hello from Spring: " + LocalDateTime.now();
    }
}
