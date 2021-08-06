CREATE TABLE IF NOT EXISTS "roles_authorities" 
(
	role_codigo INTEGER NOT NULL,
	authority_codigo INTEGER NOT NULL,
	PRIMARY KEY (role_codigo, authority_codigo),
	FOREIGN KEY (role_codigo) REFERENCES "role"(codigo),
	FOREIGN KEY (authority_codigo) REFERENCES "authority"(codigo)
);
