CREATE TABLE IF NOT EXISTS armazem (
	codigo SERIAL  PRIMARY KEY,
	capacidade INTEGER NOT NULL,
	designacao VARCHAR(180) NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	"createdBy" VARCHAR(180),
	"createdDate" TIMESTAMP,
	"lastModifiedBy" VARCHAR(100),
	"lastModifiedDate" TIMESTAMP
);