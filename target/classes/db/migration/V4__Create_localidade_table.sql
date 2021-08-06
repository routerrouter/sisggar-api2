CREATE TABLE IF NOT EXISTS localidade (
	codigo serial NOT NULL,
	descricao VARCHAR(255) NULL DEFAULT NULL,
	codigo_armazem BIGINT NOT NULL,
	enabled BOOLEAN NOT NULL DEFAULT TRUE,
	CONSTRAINT localidade_pkey PRIMARY KEY (codigo),
	FOREIGN KEY (codigo_armazem) REFERENCES armazem(codigo)
);