package dev.router.sisggar.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.domain.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

	@Query("Select e FROM Empresa e where upper(e.designacao) like %?1% ")
	Optional<Empresa> findByDesignacao(String designacao);
	
	@Modifying
	@Query("UPDATE Empresa e SET e.enabled = :status WHERE e.codigo =:codigo")
	void enabled(@Param("codigo") Long codigo, @Param("status") Boolean status);
	
	public Optional<Empresa> findByEnabled(Boolean enabled);
	
	@Query("Select e FROM Empresa e where e.enabled = true")
	public Empresa buscarEmpresa();

	
	public Optional<Empresa> findByCodigo(Long codigo);
}
