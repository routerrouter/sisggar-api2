package dev.router.sisggar.repository.auth;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.domain.entity.Usuario;



@Repository
public interface IUserRepository extends JpaRepository<Usuario, Integer>{
	
	Usuario findByUsername(String username);
	
	Usuario findUserByEmail(String emailId);

}
