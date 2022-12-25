# oereb-cts-webservice

## Entwicklung

### Run

In Eclipse unter `Run Configurations` bei `Program arguments` das Spring Profile auf `dev` setzen: `--spring.profiles.active=dev`

### Build

Use agent for creating native image config files:
```
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar build/libs/oereb-cts-webservice-0.0.LOCALBUILD-exec.jar
```