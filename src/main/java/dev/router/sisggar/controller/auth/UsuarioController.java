package dev.router.sisggar.controller.auth;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.router.sisggar.domain.dto.UsuarioDto;
import dev.router.sisggar.security.UserPrincipal;
import dev.router.sisggar.service.Impl.auth.IUserService;
import dev.router.sisggar.util.JWTTokenUtil;



@RestController
@RequestMapping("/api/auth/v1")
public class UsuarioController {
	
	@Autowired
	IUserService userService;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTTokenUtil jwtTokenUtil;
	
	@PostMapping(path = {"/register-user"}, consumes = {MediaType.APPLICATION_JSON_VALUE} , produces= {MediaType.APPLICATION_JSON_VALUE})
	public EntityModel<UsuarioDto> createUser(@RequestBody UsuarioDto usuarioDto) {
		usuarioDto = userService.createUser(usuarioDto);
		return EntityModel.of(usuarioDto);
	}
	@PutMapping(path = {"/update-user"}, consumes = {MediaType.APPLICATION_JSON_VALUE} , produces= {MediaType.APPLICATION_JSON_VALUE})
	public EntityModel<UsuarioDto> updateUser(@RequestBody UsuarioDto usuarioDto) {
		usuarioDto = userService.updateUser(usuarioDto);
		return EntityModel.of(usuarioDto);
	}

	@PostMapping(path="/login" , consumes = {MediaType.APPLICATION_JSON_VALUE} , produces= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<UsuarioDto> login(@RequestBody UsuarioDto usuarioDto) {
		
		this.authenticate(usuarioDto.getUsername(), usuarioDto.getPassword());	
		
		UsuarioDto user = this.userService.findByUsername(usuarioDto.getUsername());
		
		user.setPassword(null);
		String jwtToken = this.jwtTokenUtil.generateToken(new UserPrincipal(user));
		System.out.println(jwtToken);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+jwtToken);
		
		return ResponseEntity.ok().headers(headers).body(user);
		
	}
	private void authenticate(String username, String password) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
}
