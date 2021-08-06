package dev.router.sisggar.service.auth;


import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.router.sisggar.domain.dto.AuthorityDto;
import dev.router.sisggar.domain.dto.RoleDto;
import dev.router.sisggar.domain.entity.Authority;
import dev.router.sisggar.domain.entity.Role;
import dev.router.sisggar.repository.auth.IAuthorityRepository;
import dev.router.sisggar.repository.auth.IRoleRepository;
import dev.router.sisggar.service.Impl.auth.IRoleService;



@Service
public class RoleService implements IRoleService {
	
	@Autowired
	private IRoleRepository roleRepo;
	
	@Autowired
	private IAuthorityRepository authorityRepo;

	@Override
	public RoleDto createRole(RoleDto roleDto) {
		
		Role  role = new Role();
		role.setName(roleDto.getName());
		role.setAuthorities(roleDto.getAuthorities().stream().map(a-> {
			Authority authority = authorityRepo.findByName(a.getName());
			if(authority == null) {
				authority = authorityRepo.save(new Authority(a.getName()));
			}
			return authority;
		}).collect(Collectors.toSet()));
		
		System.out.println("RoleDto = "+roleDto);
		
		roleRepo.save(role);
		
		System.out.println("---------------------------");
		
		roleDto.setName(role.getName());
		
		roleDto.setAuthorities(role.getAuthorities().stream().map(a -> new AuthorityDto(a.getName())).collect(Collectors.toSet()));
		
		System.out.println("RoleDto in response = "+ roleDto);
		
		return roleDto;
	}

}
