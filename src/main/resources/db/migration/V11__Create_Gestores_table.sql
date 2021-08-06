CREATE TABLE IF NOT EXISTS gestor 
(
	codigo SERIAL  PRIMARY KEY,
	nome VARCHAR(250) NOT NULL,
	telefone VARCHAR(250) NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	codigo_armazem INTEGER,
	FOREIGN KEY (codigo_armazem) REFERENCES armazem(codigo)
);