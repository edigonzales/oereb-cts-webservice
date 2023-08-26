# oereb-cts-webservice

## Entwicklung

### Run

In Eclipse unter `Run Configurations` bei `Program arguments` das Spring Profile auf `dev` setzen: `--spring.profiles.active=dev`.

Man kann während des Entwickelns automatisch Container mit einem docker-compose-File hochfahren. Dazu ist eine spezielle Klasse notwendig. Sie liegt im Test-Ordner und muss für die Tests excludiert werden. Zusätzlich muss in Eclipse unter `Run Configurations` bei `Classpath` das Häckchen bei `Exclude Test Code` entfernt werden. 

Für Entwicklungen, die eher eine "statische" DB benötigen, kann die DB mit dem docker-compose-File manuell hochgefahren werden und Daten mit _ili2db_ importiert werden:

PostgreSQL:
```
java -jar /Users/stefan/Downloads/ili2pg-4.11.0.jar --dbhost localhost --dbdatabase edit --dbport 54321 --dbusr ddluser --dbpwd ddluser --defaultSrsCode 2056 --strokeArcs --createEnumTabs --createMetaInfo --createImportTabs	--createBasketCol --createFk --createFkIdx --models SO_AGI_OEREB_CTS_20230819 --dbschema agi_oereb_cts_v1 --modeldir ../oereb-cts/lib/src/main/resources/ili/ --schemaimport
```

```
java -jar /Users/stefan/Downloads/ili2pg-4.11.0.jar --dbhost localhost --dbdatabase edit --dbport 54321 --dbusr ddluser --dbpwd ddluser --defaultSrsCode 2056 --strokeArcs --createEnumTabs --createMetaInfo --models SO_AGI_OEREB_CTS_20230819 --dbschema agi_oereb_cts_v1 --modeldir ../oereb-cts/lib/src/main/resources/ili/  --import src/test/data/cts-SO.xtf
```

**FIXME**: modeldir nicht mehr notwendig, wenn in Repo.

GeoPackage:




Sql-Datei für erstmaligen DB-Start in Prod:
```
java -jar /Users/stefan/Downloads/ili2pg-4.11.0.jar --createscript oereb-cts-postgres.sql --defaultSrsCode 2056 --strokeArcs --createEnumTabs --createMetaInfo --createImportTabs --createBasketCol --createFk --createFkIdx --models SO_AGI_OEREB_CTS_20230819 --dbschema agi_oereb_cts_v1 --modeldir ../oereb-cts/lib/src/main/resources/ili/ 
```

```
java -jar /Users/stefan/apps/ili2gpkg-4.11.0/ili2gpkg-4.11.0.jar --createscript oereb-cts-gpkg.sql --defaultSrsCode 2056 --strokeArcs --createEnumTabs --createMetaInfo --createImportTabs --createBasketCol --createFk --createFkIdx --models SO_AGI_OEREB_CTS_20230819 --modeldir ../oereb-cts/lib/src/main/resources/ili/ 
```


### Build

Use agent for creating native image config files:
```
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar build/libs/oereb-cts-webservice-0.0.LOCALBUILD-exec.jar
```

SO.ch.so.agi.oereb.cts.GetCapabilitiesProbe.a782da0e-c283-3e37-9859-a006f897e47d