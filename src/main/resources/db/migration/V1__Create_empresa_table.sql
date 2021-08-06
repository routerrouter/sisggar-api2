CREATE TABLE IF NOT EXISTS empresa 
(
	codigo SERIAL  PRIMARY KEY,
	designacao VARCHAR(250) NOT NULL,
	email VARCHAR(250) NOT NULL,
	endereco VARCHAR(250) NOT NULL,
	telefone VARCHAR(250) NOT NULL,
	website VARCHAR(250) NULL DEFAULT NULL,
	nif VARCHAR(250) NOT NULL,
	logotipo_url VARCHAR(500) NULL DEFAULT NULL,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	"createdBy" VARCHAR(180),
	"createdDate" TIMESTAMP,
	"lastModifiedBy" VARCHAR(100),
	"lastModifiedDate" TIMESTAMP
);