package dev.router.sisggar.domain.dto;



import java.util.Set;

import lombok.Data;

@Data
public class UsuarioDto {

	private Integer codigo;
	private String firstname;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private boolean enabled;
	private Set<RoleDto> roles;

	
}
