package ch.so.agi.oereb.cts;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "oereb")
public class EndpointProperties {
    private List<Map<String,String>> endpoints;

    public List<Map<String, String>> getEndpoints() {
        return endpoints;
    }

    public void setEndpoints(List<Map<String, String>> endpoints) {
        this.endpoints = endpoints;
    }
}
