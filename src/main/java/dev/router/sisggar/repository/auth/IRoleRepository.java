package dev.router.sisggar.repository.auth;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.domain.entity.Role;



@Repository
public interface IRoleRepository extends JpaRepository<Role, Integer>{

	Role findByName(String name);
	
}
