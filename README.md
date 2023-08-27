# oereb-cts-webservice

## Verbesserungen

- Brauche ich Connection-Pool / Datasource? Wenn es nur um das Importieren geht, dann eher nicht. Wenn das GUI auch hier implementiert wird, wohl schon.
- Vielleicht gibt es sinnvolle Unterscheidungen bei den Exception, wenn beim Hochfahren gleich auch validiert wird. Stand heute wird bei einer Exception der Server wieder runtergefahren.
- JobRunr: damit können einfach mehrere Worker die Arbeit erledigen.


## Entwicklung

### Run

In Eclipse unter `Run Configurations` bei `Program arguments` das Spring Profile auf `dev` setzen: `--spring.profiles.active=dev`.

Man kann während des Entwickelns automatisch Container mit einem docker-compose-File hochfahren. Dazu ist eine spezielle Klasse notwendig. Sie liegt im Test-Ordner und muss für die Tests excludiert werden. Zusätzlich muss in Eclipse unter `Run Configurations` bei `Classpath` das Häckchen bei `Exclude Test Code` entfernt werden. 

Für Entwicklungen, die eher eine "statische" DB benötigen, kann die DB mit dem docker-compose-File manuell hochgefahren werden und Daten mit _ili2db_ importiert werden:

```
java -jar /Users/stefan/Downloads/ili2pg-4.11.0.jar --dbhost localhost --dbdatabase edit --dbport 54321 --dbusr ddluser --dbpwd ddluser --defaultSrsCode 2056 --strokeArcs --createEnumTabs --createMetaInfo --createImportTabs	--createBasketCol --createFk --createFkIdx --sqlEnableNull --models SO_AGI_OEREB_CTS_20230819 --dbschema agi_oereb_cts_v1 --modeldir ../oereb-cts/lib/src/main/resources/ili/ --schemaimport
```

```
java -jar /Users/stefan/Downloads/ili2pg-4.11.0.jar --dbhost localhost --dbdatabase edit --dbport 54321 --dbusr ddluser --dbpwd ddluser --defaultSrsCode 2056 --strokeArcs --createEnumTabs --createMetaInfo --models SO_AGI_OEREB_CTS_20230819 --dbschema agi_oereb_cts_v1 --modeldir ../oereb-cts/lib/src/main/resources/ili/  --import src/test/data/cts-SO.xtf
```

**FIXME**: modeldir nicht mehr notwendig, wenn in Repo.





Sql-Datei für erstmaligen DB-Start in Prod:
```
java -jar /Users/stefan/Downloads/ili2pg-4.11.0.jar --createscript oereb-cts-postgres.sql --defaultSrsCode 2056 --strokeArcs --createEnumTabs --createMetaInfo --createImportTabs --createBasketCol --createDatasetCol --createFk --createFkIdx --sqlEnableNull --models SO_AGI_OEREB_CTS_20230819 --dbschema agi_oereb_cts_v1 --modeldir ../oereb-cts/lib/src/main/resources/ili/ 

java -jar /Users/stefan/Downloads/ili2pg-4.11.0.jar --createscript oereb-cts-postgres.sql --defaultSrsCode 2056 --strokeArcs --createEnumTabs --createMetaInfo --createImportTabs --createBasketCol --createFk --createFkIdx --sqlEnableNull --models SO_AGI_OEREB_CTS_20230819 --dbschema agi_oereb_cts_v1 --modeldir ../oereb-cts/lib/src/main/resources/ili/ 
```




### Build

Use agent for creating native image config files:
```
java -agentlib:native-image-agent=config-output-dir=src/main/resources/META-INF/native-image -jar build/libs/oereb-cts-webservice-0.0.LOCALBUILD-exec.jar
```

SO.ch.so.agi.oereb.cts.GetCapabilitiesProbe.a782da0e-c283-3e37-9859-a006f897e47d


```
/*
    SELECT
        identifier, bool_and(success) AS success_versions, testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetVersionsProbe'
    GROUP BY 
        identifier, testsuitetime  
*/
        
WITH latest AS 
(
    SELECT 
        identifier, max(testsuitetime) AS testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult 
    GROUP BY 
        identifier 
)
,
getversions AS 
(
    SELECT
        identifier, bool_and(success) AS success_versions, testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetVersionsProbe'
    GROUP BY 
        identifier, testsuitetime  
)
,
getcapabilities AS 
(
    SELECT
        identifier, bool_and(success) AS success_capabilities, testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetCapabilitiesProbe'
    GROUP BY 
        identifier, testsuitetime  
)
,
getegrid AS 
(
    SELECT
        identifier, bool_and(success) AS success_egrid, testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetEGRIDProbe'
    GROUP BY 
        identifier, testsuitetime  
)
,
getextract AS 
(
    SELECT
        identifier, bool_and(success) AS success_extract, testsuitetime
    FROM 
        agi_oereb_cts_v1.proberesult
    WHERE 
        classname = 'ch.so.agi.oereb.cts.GetExtractByIdProbe'
    GROUP BY 
        identifier, testsuitetime  
)
SELECT 
    latest.identifier,
    latest.testsuitetime,
    getversions.success_versions,
    getcapabilities.success_capabilities,
    getegrid.success_egrid,
    getextract.success_extract
FROM 
    latest
    LEFT JOIN getversions
    ON latest.identifier = getversions.identifier
    AND latest.testsuitetime = getversions.testsuitetime
    LEFT JOIN getcapabilities
    ON latest.identifier = getcapabilities.identifier
    AND latest.testsuitetime = getcapabilities.testsuitetime
    LEFT JOIN getegrid
    ON latest.identifier = getegrid.identifier
    AND latest.testsuitetime = getegrid.testsuitetime
    LEFT JOIN getextract
    ON latest.identifier = getextract.identifier
    AND latest.testsuitetime = getextract.testsuitetime

        

```