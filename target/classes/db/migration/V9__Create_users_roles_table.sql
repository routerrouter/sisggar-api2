CREATE TABLE "usuarios_roles" (
	usuario_codigo INTEGER NOT NULL,
	role_codigo INTEGER NOT NULL,
	PRIMARY KEY (usuario_codigo, role_codigo),
	FOREIGN KEY (usuario_codigo) REFERENCES "usuario"(codigo),
	FOREIGN KEY (role_codigo) REFERENCES "role"(codigo)
);
