-- liquibase formatted sql
-- changeset serathiuk:000000000001
CREATE TABLE PESSOAS (
     ID VARCHAR(100) NOT NULL,
     NOME VARCHAR(64) NOT NULL,
     TIPO_PESSOA VARCHAR(24),
     DOCUMENTO VARCHAR(32) NOT NULL
);
ALTER TABLE PESSOAS ADD CONSTRAINT PESSOAS_PK PRIMARY KEY(ID);
ALTER TABLE PESSOAS ADD CONSTRAINT PESSOAS_UN1 UNIQUE(TIPO_PESSOA, DOCUMENTO);

CREATE TABLE FILIAIS (
     ID VARCHAR(100) NOT NULL,
     NOME VARCHAR(64) NOT NULL
);
ALTER TABLE FILIAIS ADD CONSTRAINT FILIAIS_PK PRIMARY KEY(ID);
ALTER TABLE FILIAIS ADD CONSTRAINT FILIAIS_UN1 UNIQUE(NOME);