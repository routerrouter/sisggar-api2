package dev.router.sisggar.service.Impl.auth;

import org.springframework.security.core.userdetails.UserDetailsService;

import dev.router.sisggar.domain.dto.UsuarioDto;

public interface IUserService extends UserDetailsService{

	public UsuarioDto createUser(UsuarioDto usuarioDto);
	public UsuarioDto updateUser(UsuarioDto usuarioDto);
	public UsuarioDto findByUsername(String userName);
}
