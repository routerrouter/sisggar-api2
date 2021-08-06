package dev.router.sisggar.repository.auth;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.domain.entity.Authority;



@Repository
public interface IAuthorityRepository extends JpaRepository<Authority, Integer>{

	Authority findByName(String name);
}
