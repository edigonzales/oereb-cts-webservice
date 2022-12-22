# oereb-cts-webservice

## Entwicklung

### Run

### Build

Use agent for creating native image config files:
```
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar build/libs/oereb-cts-webservice-0.0.LOCALBUILD.jar
```