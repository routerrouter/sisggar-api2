package dev.router.sisggar.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.domain.entity.Armazem;



@Repository
public interface ArmazemRepository extends JpaRepository<Armazem, Long> {

	
	@Modifying
	@Query("UPDATE Armazem a SET a.enabled = :status WHERE a.codigo =:codigo")
	void enabled(@Param("codigo") Long codigo, @Param("status") Boolean status);
	
	
	public List<Armazem> findByDesignacaoIgnoreCaseContainingOrderByDesignacaoAsc(String nome);
	
	public Optional<Armazem> findArmazemByDesignacao(String designacao);
	
	public Optional<Armazem> findByCodigo(Long codigo);
	
	
	
	
	
	
	
	
}