package dev.router.sisggar.controller.auth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.router.sisggar.domain.dto.RoleDto;
import dev.router.sisggar.service.Impl.auth.IRoleService;


@RestController
public class RoleController {

	@Autowired
	private IRoleService roleService;

	@PostMapping(path = {"/create-role"} , consumes = {MediaType.APPLICATION_JSON_VALUE} , produces = {MediaType.APPLICATION_JSON_VALUE})
	public EntityModel<RoleDto> createRole(@RequestBody RoleDto roleDto) {
		roleDto = roleService.createRole(roleDto);
		return EntityModel.of(roleDto);
	}
}
