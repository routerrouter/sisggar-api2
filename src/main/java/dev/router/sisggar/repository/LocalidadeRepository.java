package dev.router.sisggar.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.domain.entity.Localidade;




@Repository
public interface LocalidadeRepository extends JpaRepository<Localidade, Long>{

	
	@Modifying
	@Query("UPDATE Localidade l SET l.enabled = :status WHERE l.codigo=:codigo")
	void enabled(@Param("codigo") Long codigo, @Param("status") Boolean status);
	
	List<Localidade> findByDescricaoIgnoreCaseContainingOrderByDescricaoAsc(String descricao);
	
	@Query("SELECT  l FROM Localidade l WHERE lower(l.descricao) LIKE LOWER(CONCAT ('%',:descricao, '%')) and l.armazem.codigo=:armazem")
	List<Localidade> findByDescricaoIgnoreCaseContainingAndArmazemOrderByDescricaoAsc(@Param("descricao")String descricao, @Param("armazem") Long armazem);
	
	Optional<Localidade> findByDescricaoIgnoreCase(String descricao);
	
	@Query("SELECT l from Localidade l WHERE l.descricao =:descricao and l.armazem.codigo=:armazem")
	Optional<Localidade> findByDescricaoAndArmazem(@Param("descricao") String descricao,@Param("armazem") Long armazem);
	
	@Query("SELECT  l FROM Localidade l WHERE  l.armazem.codigo =:armazem")
	List<Localidade> findByArmazem(@Param("armazem") Long armazem);
	
	@Query("SELECT  l FROM Localidade l WHERE l.descricao=:descricao and l.armazem.codigo =:armazem")
	List<Localidade> findByDecricaoAndArmazem(@Param("descricao") String descricao, @Param("armazem") Long armazem);

	Optional<Localidade> findByCodigo(Long codigo);
	
}