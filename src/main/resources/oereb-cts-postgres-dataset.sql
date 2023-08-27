CREATE SCHEMA IF NOT EXISTS agi_oereb_cts_v1;
CREATE SEQUENCE agi_oereb_cts_v1.t_ili2db_seq;;
-- SO_AGI_OEREB_CTS_20230819.CheckResult
CREATE TABLE agi_oereb_cts_v1.checkresult (
  T_Id bigint PRIMARY KEY DEFAULT nextval('agi_oereb_cts_v1.t_ili2db_seq')
  ,T_basket bigint NOT NULL
  ,T_datasetname varchar(200) NOT NULL
  ,T_Seq bigint NULL
  ,adescription text NULL
  ,statuscode integer NULL
  ,message text NULL
  ,classname text NULL
  ,success boolean NULL
  ,starttime timestamp NULL
  ,endtime timestamp NULL
  ,processingtimesecs decimal(7,3) NULL
  ,proberesult_checkresults bigint NULL
)
;
CREATE INDEX checkresult_t_basket_idx ON agi_oereb_cts_v1.checkresult ( t_basket );
CREATE INDEX checkresult_t_datasetname_idx ON agi_oereb_cts_v1.checkresult ( t_datasetname );
CREATE INDEX checkresult_proberesult_checkresults_idx ON agi_oereb_cts_v1.checkresult ( proberesult_checkresults );
-- SO_AGI_OEREB_CTS_20230819.ProbeResult
CREATE TABLE agi_oereb_cts_v1.proberesult (
  T_Id bigint PRIMARY KEY DEFAULT nextval('agi_oereb_cts_v1.t_ili2db_seq')
  ,T_basket bigint NOT NULL
  ,T_datasetname varchar(200) NOT NULL
  ,T_Type varchar(60) NOT NULL
  ,T_Ili_Tid varchar(200) NULL
  ,T_Seq bigint NULL
  ,identifier text NULL
  ,testsuitetime timestamp NULL
  ,serviceendpoint varchar(1023) NULL
  ,request varchar(1023) NULL
  ,resultfilelocation text NULL
  ,classname text NULL
  ,success boolean NULL
  ,starttime timestamp NULL
  ,endtime timestamp NULL
  ,processingtimesecs decimal(7,3) NULL
)
;
CREATE INDEX proberesult_t_basket_idx ON agi_oereb_cts_v1.proberesult ( t_basket );
CREATE INDEX proberesult_t_datasetname_idx ON agi_oereb_cts_v1.proberesult ( t_datasetname );
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_BASKET (
  T_Id bigint PRIMARY KEY
  ,dataset bigint NULL
  ,topic varchar(200) NOT NULL
  ,T_Ili_Tid varchar(200) NULL
  ,attachmentKey varchar(200) NOT NULL
  ,domains varchar(1024) NULL
)
;
CREATE INDEX T_ILI2DB_BASKET_dataset_idx ON agi_oereb_cts_v1.t_ili2db_basket ( dataset );
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_DATASET (
  T_Id bigint PRIMARY KEY
  ,datasetName varchar(200) NULL
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_IMPORT (
  T_Id bigint PRIMARY KEY
  ,dataset bigint NOT NULL
  ,importDate timestamp NOT NULL
  ,importUser varchar(40) NOT NULL
  ,importFile varchar(200) NULL
)
;
CREATE INDEX T_ILI2DB_IMPORT_dataset_idx ON agi_oereb_cts_v1.t_ili2db_import ( dataset );
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_IMPORT_BASKET (
  T_Id bigint PRIMARY KEY
  ,importrun bigint NOT NULL
  ,basket bigint NOT NULL
  ,objectCount integer NULL
)
;
CREATE INDEX T_ILI2DB_IMPORT_BASKET_importrun_idx ON agi_oereb_cts_v1.t_ili2db_import_basket ( importrun );
CREATE INDEX T_ILI2DB_IMPORT_BASKET_basket_idx ON agi_oereb_cts_v1.t_ili2db_import_basket ( basket );
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_INHERITANCE (
  thisClass varchar(1024) PRIMARY KEY
  ,baseClass varchar(1024) NULL
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_SETTINGS (
  tag varchar(60) PRIMARY KEY
  ,setting varchar(8000) NULL
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_TRAFO (
  iliname varchar(1024) NOT NULL
  ,tag varchar(1024) NOT NULL
  ,setting varchar(1024) NOT NULL
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_MODEL (
  filename varchar(250) NOT NULL
  ,iliversion varchar(3) NOT NULL
  ,modelName text NOT NULL
  ,content text NOT NULL
  ,importDate timestamp NOT NULL
  ,PRIMARY KEY (modelName,iliversion)
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_CLASSNAME (
  IliName varchar(1024) PRIMARY KEY
  ,SqlName varchar(1024) NOT NULL
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (
  IliName varchar(1024) NOT NULL
  ,SqlName varchar(1024) NOT NULL
  ,ColOwner varchar(1024) NOT NULL
  ,Target varchar(1024) NULL
  ,PRIMARY KEY (ColOwner,SqlName)
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (
  tablename varchar(255) NOT NULL
  ,subtype varchar(255) NULL
  ,columnname varchar(255) NOT NULL
  ,tag varchar(1024) NOT NULL
  ,setting varchar(8000) NOT NULL
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_TABLE_PROP (
  tablename varchar(255) NOT NULL
  ,tag varchar(1024) NOT NULL
  ,setting varchar(8000) NOT NULL
)
;
CREATE TABLE agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (
  ilielement varchar(255) NOT NULL
  ,attr_name varchar(1024) NOT NULL
  ,attr_value varchar(8000) NOT NULL
)
;
ALTER TABLE agi_oereb_cts_v1.checkresult ADD CONSTRAINT checkresult_T_basket_fkey FOREIGN KEY ( T_basket ) REFERENCES agi_oereb_cts_v1.T_ILI2DB_BASKET DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE agi_oereb_cts_v1.checkresult ADD CONSTRAINT checkresult_proberesult_checkresults_fkey FOREIGN KEY ( proberesult_checkresults ) REFERENCES agi_oereb_cts_v1.proberesult DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE agi_oereb_cts_v1.proberesult ADD CONSTRAINT proberesult_T_basket_fkey FOREIGN KEY ( T_basket ) REFERENCES agi_oereb_cts_v1.T_ILI2DB_BASKET DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE agi_oereb_cts_v1.T_ILI2DB_BASKET ADD CONSTRAINT T_ILI2DB_BASKET_dataset_fkey FOREIGN KEY ( dataset ) REFERENCES agi_oereb_cts_v1.T_ILI2DB_DATASET DEFERRABLE INITIALLY DEFERRED;
CREATE UNIQUE INDEX T_ILI2DB_DATASET_datasetName_key ON agi_oereb_cts_v1.T_ILI2DB_DATASET (datasetName)
;
ALTER TABLE agi_oereb_cts_v1.T_ILI2DB_IMPORT_BASKET ADD CONSTRAINT T_ILI2DB_IMPORT_BASKET_importrun_fkey FOREIGN KEY ( importrun ) REFERENCES agi_oereb_cts_v1.T_ILI2DB_IMPORT DEFERRABLE INITIALLY DEFERRED;
ALTER TABLE agi_oereb_cts_v1.T_ILI2DB_IMPORT_BASKET ADD CONSTRAINT T_ILI2DB_IMPORT_BASKET_basket_fkey FOREIGN KEY ( basket ) REFERENCES agi_oereb_cts_v1.T_ILI2DB_BASKET DEFERRABLE INITIALLY DEFERRED;
CREATE UNIQUE INDEX T_ILI2DB_MODEL_modelName_iliversion_key ON agi_oereb_cts_v1.T_ILI2DB_MODEL (modelName,iliversion)
;
CREATE UNIQUE INDEX T_ILI2DB_ATTRNAME_ColOwner_SqlName_key ON agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (ColOwner,SqlName)
;
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_CLASSNAME (IliName,SqlName) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult','proberesult');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_CLASSNAME (IliName,SqlName) VALUES ('SO_AGI_OEREB_CTS_20230819.Result','aresult');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_CLASSNAME (IliName,SqlName) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult','checkresult');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_CLASSNAME (IliName,SqlName) VALUES ('SO_AGI_OEREB_CTS_20230819.Results.ProbeResult','so_g_rb0230819results_proberesult');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.resultFileLocation','resultfilelocation','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.processingTimeSecs','processingtimesecs','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.testSuiteTime','testsuitetime','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.statusCode','statuscode','checkresult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.serviceEndpoint','serviceendpoint','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.identifier','identifier','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.startTime','starttime','checkresult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.startTime','starttime','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.processingTimeSecs','processingtimesecs','checkresult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.message','message','checkresult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.description','adescription','checkresult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.request','request','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.className','classname','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.checkResults','proberesult_checkresults','checkresult','proberesult');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.className','classname','checkresult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.success','success','checkresult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.success','success','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.endTime','endtime','proberesult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_ATTRNAME (IliName,SqlName,ColOwner,Target) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.endTime','endtime','checkresult',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_TRAFO (iliname,tag,setting) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult','ch.ehi.ili2db.inheritance','newClass');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_TRAFO (iliname,tag,setting) VALUES ('SO_AGI_OEREB_CTS_20230819.Result','ch.ehi.ili2db.inheritance','subClass');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_TRAFO (iliname,tag,setting) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult','ch.ehi.ili2db.inheritance','newClass');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_TRAFO (iliname,tag,setting) VALUES ('SO_AGI_OEREB_CTS_20230819.Results.ProbeResult','ch.ehi.ili2db.inheritance','superClass');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_INHERITANCE (thisClass,baseClass) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult','SO_AGI_OEREB_CTS_20230819.Result');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_INHERITANCE (thisClass,baseClass) VALUES ('SO_AGI_OEREB_CTS_20230819.Results.ProbeResult','SO_AGI_OEREB_CTS_20230819.ProbeResult');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_INHERITANCE (thisClass,baseClass) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult','SO_AGI_OEREB_CTS_20230819.Result');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_INHERITANCE (thisClass,baseClass) VALUES ('SO_AGI_OEREB_CTS_20230819.Result',NULL);
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'identifier','ch.ehi.ili2db.typeKind','TEXT');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'message','ch.ehi.ili2db.textKind','MTEXT');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'message','ch.ehi.ili2db.typeKind','MTEXT');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'processingtimesecs','ch.ehi.ili2db.unit','s');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'processingtimesecs','ch.ehi.ili2db.typeKind','NUMERIC');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'serviceendpoint','ch.ehi.ili2db.typeKind','URI');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'resultfilelocation','ch.ehi.ili2db.typeKind','TEXT');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult','proberesult','success','ch.ehi.ili2db.enumDomain','INTERLIS.BOOLEAN');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'success','ch.ehi.ili2db.typeKind','BOOLEAN');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'starttime','ch.ehi.ili2db.typeKind','DATETIME');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'starttime','ch.ehi.ili2db.typeKind','DATETIME');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'testsuitetime','ch.ehi.ili2db.typeKind','DATETIME');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'proberesult_checkresults','ch.ehi.ili2db.foreignKey','proberesult');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'adescription','ch.ehi.ili2db.typeKind','TEXT');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'endtime','ch.ehi.ili2db.typeKind','DATETIME');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'processingtimesecs','ch.ehi.ili2db.unit','s');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'processingtimesecs','ch.ehi.ili2db.typeKind','NUMERIC');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'success','ch.ehi.ili2db.typeKind','BOOLEAN');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult','so_g_rb0230819results_proberesult','success','ch.ehi.ili2db.enumDomain','INTERLIS.BOOLEAN');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'classname','ch.ehi.ili2db.typeKind','TEXT');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'endtime','ch.ehi.ili2db.typeKind','DATETIME');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'T_Ili_Tid','ch.ehi.ili2db.oidDomain','SO_AGI_OEREB_CTS_20230819.SOOID');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'request','ch.ehi.ili2db.typeKind','URI');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult',NULL,'statuscode','ch.ehi.ili2db.typeKind','NUMERIC');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('checkresult','checkresult','success','ch.ehi.ili2db.enumDomain','INTERLIS.BOOLEAN');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'classname','ch.ehi.ili2db.typeKind','TEXT');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_COLUMN_PROP (tablename,subtype,columnname,tag,setting) VALUES ('proberesult',NULL,'T_Type','ch.ehi.ili2db.types','["proberesult","so_g_rb0230819results_proberesult"]');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_TABLE_PROP (tablename,tag,setting) VALUES ('checkresult','ch.ehi.ili2db.tableKind','STRUCTURE');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_TABLE_PROP (tablename,tag,setting) VALUES ('proberesult','ch.ehi.ili2db.tableKind','STRUCTURE');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_MODEL (filename,iliversion,modelName,content,importDate) VALUES ('SO_AGI_OEREB_CTS_20230819.ili','2.3','SO_AGI_OEREB_CTS_20230819','INTERLIS 2.3;

/** !!------------------------------------------------------------------------------
 *  !! Version    | wer | Änderung
 *  !!------------------------------------------------------------------------------
 *  !! 2023-08-19 | sz  | Ersterfassung
 *  !!==============================================================================
 */
!!@ technicalContact=mailto:agi@bd.so.ch
!!@ furtherInformation=http://geo.so.ch/models/AGI/SO_AGI_OEREB_CTS_20230819.uml

MODEL SO_AGI_OEREB_CTS_20230819 (de)
AT "https://agi.so.ch"
VERSION "2023-08-19" =

  DOMAIN 
    SOOID = OID TEXT*255;

  STRUCTURE Result (ABSTRACT) =
    className : MANDATORY TEXT;
    success : MANDATORY BOOLEAN;
    startTime : MANDATORY FORMAT INTERLIS.XMLDateTime "2000-01-01T00:00:00.000" .. "2100-12-31T23:59:59.999";
    endTime : FORMAT INTERLIS.XMLDateTime "2000-01-01T00:00:00.000" .. "2100-12-31T23:59:59.999";
    processingTimeSecs : MANDATORY 0.000 .. 3600.000 [INTERLIS.s];
  END Result;

  STRUCTURE CheckResult EXTENDS Result =
    description : TEXT;
    statusCode : 100 .. 999;
    message : MTEXT;
  END CheckResult;

  STRUCTURE ProbeResult EXTENDS Result =
    identifier : MANDATORY TEXT;
    testSuiteTime : MANDATORY FORMAT INTERLIS.XMLDateTime "2000-01-01T00:00:00.000" .. "2100-12-31T23:59:59.999";
    serviceEndpoint : MANDATORY URI;
    request : MANDATORY URI;
    resultFileLocation : MANDATORY TEXT;
    checkResults : BAG {1..*} OF SO_AGI_OEREB_CTS_20230819.CheckResult;
  END ProbeResult;

  TOPIC Results =
    OID AS SO_AGI_OEREB_CTS_20230819.SOOID;

    CLASS ProbeResult EXTENDS SO_AGI_OEREB_CTS_20230819.ProbeResult =
      UNIQUE identifier, request;
    END ProbeResult;

  END Results;

END SO_AGI_OEREB_CTS_20230819.','2023-08-27 19:22:53.456');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.createMetaInfo','True');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.SqlNull','enable');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.interlis.ili2c.ilidirs','../oereb-cts/lib/src/main/resources/ili/');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.arrayTrafo','coalesce');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.importTabs','simple');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.createForeignKeyIndex','yes');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.createDatasetCols','addDatasetCol');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.localisedTrafo','expand');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.jsonTrafo','coalesce');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.sender','ili2pg-4.11.0-eb9c1ad2e5db64e1826ef16cd9d77ab25879547a');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.BasketHandling','readWrite');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.createForeignKey','yes');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.defaultSrsCode','2056');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.createEnumDefs','multiTable');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.defaultSrsAuthority','EPSG');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.maxSqlNameLength','60');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.uuidDefaultValue','uuid_generate_v4()');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.inheritanceTrafo','smart1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.catalogueRefTrafo','coalesce');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.StrokeArcs','enable');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.multiPointTrafo','coalesce');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.multiLineTrafo','coalesce');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.multiSurfaceTrafo','coalesce');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_SETTINGS (tag,setting) VALUES ('ch.ehi.ili2db.multilingualTrafo','expand');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Results','ili2db.ili.topicClasses','so_g_rb0230819results_proberesult');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.identifier','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.identifier','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.serviceEndpoint','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.serviceEndpoint','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.request','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.request','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.success','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.success','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.className','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.className','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.message','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.message','ili2db.ili.attrCardinalityMin','0');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819','furtherInformation','http://geo.so.ch/models/AGI/SO_AGI_OEREB_CTS_20230819.uml');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819','technicalContact','mailto:agi@bd.so.ch');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.startTime','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.startTime','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.endTime','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.endTime','ili2db.ili.attrCardinalityMin','0');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.processingTimeSecs','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.Result.processingTimeSecs','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.description','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.description','ili2db.ili.attrCardinalityMin','0');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.resultFileLocation','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.resultFileLocation','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.testSuiteTime','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.testSuiteTime','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.checkResults','ili2db.ili.attrCardinalityMax','*');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.ProbeResult.checkResults','ili2db.ili.attrCardinalityMin','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.statusCode','ili2db.ili.attrCardinalityMax','1');
INSERT INTO agi_oereb_cts_v1.T_ILI2DB_META_ATTRS (ilielement,attr_name,attr_value) VALUES ('SO_AGI_OEREB_CTS_20230819.CheckResult.statusCode','ili2db.ili.attrCardinalityMin','0');
