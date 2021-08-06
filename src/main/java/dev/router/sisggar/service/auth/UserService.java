package dev.router.sisggar.service.auth;



import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.router.sisggar.domain.dto.AuthorityDto;
import dev.router.sisggar.domain.dto.RoleDto;
import dev.router.sisggar.domain.dto.UsuarioDto;
import dev.router.sisggar.domain.entity.Authority;
import dev.router.sisggar.domain.entity.Role;
import dev.router.sisggar.domain.entity.Usuario;
import dev.router.sisggar.repository.auth.IRoleRepository;
import dev.router.sisggar.repository.auth.IUserRepository;
import dev.router.sisggar.security.UserPrincipal;
import dev.router.sisggar.service.Impl.auth.IUserService;


@Service
@Qualifier("UserDetailsService")
public class UserService implements IUserService {
	
	@Autowired
	IUserRepository userRepo;
	
	@Autowired
	IRoleRepository roleRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		Usuario usuario = userRepo.findByUsername(userName);
		
		if(usuario == null) {
			System.out.println("User Not Found "+userName);
			throw new UsernameNotFoundException("User Not Found "+ userName);
		}
		UsuarioDto dto = this.getUserDto(usuario);
		
		UserPrincipal principal = new UserPrincipal(dto);
		
		return principal;
	}
	
	

	@Override
	public UsuarioDto createUser(UsuarioDto usuarioDto) {
		
		if(checkIfUserNameExists(usuarioDto)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Nome de usuário já existe");
		}
		if(checkIfUserEmailIdExists(usuarioDto)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Email de usuário já existe");
		}
		Usuario usuario = new Usuario();
		
		BeanUtils.copyProperties(usuarioDto, usuario);
		
		usuario.setEmail(usuario.getEmail().toLowerCase());
		usuario.setUsername(usuario.getUsername().toLowerCase());
		
		Optional<Role> role = roleRepo.findById(6);
		Set<Role> roles  = new HashSet<>();
		
		roles.add(role.get());
		
		usuario.setRoles(roles);
		usuario.setEnabled(true);
		String password  = RandomStringUtils.random(7,true,true);
		System.out.println("Password = "+password);
		
		usuario.setPassword(passwordEncoder.encode(password));
		
		usuario = userRepo.save(usuario);
		
		usuarioDto = this.getUserDto(usuario);
		
		
		return usuarioDto;
	}
	
	private UsuarioDto getUserDto(Usuario usuario) {
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setEmail(usuario.getEmail());
		usuarioDto.setEnabled(usuario.isEnabled());
		usuarioDto.setFirstname(usuario.getFirstname());
		usuarioDto.setCodigo(usuario.getCodigo());
		usuarioDto.setLastname(usuario.getLastname());
		usuarioDto.setUsername(usuario.getUsername());
		usuarioDto.setPassword(usuario.getPassword());
		
		Set<RoleDto> rSet = new HashSet<>();
		for(Role r : usuario.getRoles()) {
			RoleDto rDto = new RoleDto();
			rDto.setName(r.getName());
			Set<AuthorityDto> aSet = new HashSet<>();
			
			for(Authority a : r.getAuthorities()) {
				AuthorityDto aDto = new AuthorityDto(a.getName());
				aSet.add(aDto);
			}
			rDto.setAuthorities(aSet);
			rSet.add(rDto);
		}
		usuarioDto.setRoles(rSet);
		
		return usuarioDto;
	}

	
	public boolean checkIfUserNameExists(UsuarioDto usuarioDto) {
		return StringUtils.isNotBlank(usuarioDto.getUsername()) && userRepo.findByUsername(usuarioDto.getUsername().toLowerCase()) !=null;
	}
	private boolean checkIfUserEmailIdExists(UsuarioDto usuarioDto) {
		return StringUtils.isNotBlank(usuarioDto.getEmail()) && userRepo.findUserByEmail(usuarioDto.getEmail().toLowerCase()) !=null;
	}

	@Override
	public UsuarioDto updateUser(UsuarioDto usuarioDto) {
		
		Usuario usuario = userRepo.findByUsername(usuarioDto.getUsername().toLowerCase());
		usuario.setFirstname(usuarioDto.getFirstname());
		usuario.setLastname(usuarioDto.getLastname());
		
		Set<Role> rSet = new HashSet<>();
		for(RoleDto rDto : usuarioDto.getRoles()) {
			rSet.add(roleRepo.findByName(rDto.getName()));
		}
		usuario.setRoles(rSet);

		usuario = userRepo.save(usuario);
		
		usuarioDto.setCodigo(usuario.getCodigo());
		
		return usuarioDto;
	}
	@Override
	public UsuarioDto findByUsername(String username) {

		return this.getUserDto(this.userRepo.findByUsername(username));
		
	}

	
}
