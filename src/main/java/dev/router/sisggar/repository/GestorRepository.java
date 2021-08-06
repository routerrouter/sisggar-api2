package dev.router.sisggar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.router.sisggar.domain.entity.GestorCliente;

public interface GestorRepository extends JpaRepository<GestorCliente, Long>{

	
	@Modifying
	@Query("UPDATE GestorCliente g SET g.enabled = :status WHERE g.codigo =:codigo")
	void enabled(@Param("codigo") Long codigo, @Param("status") Boolean status);
	
	public List<GestorCliente>findByNomeIgnoreCaseContainingOrderByNomeAsc(String nome);
	
	public List<GestorCliente>findByTelefoneIgnoreCaseContainingOrderByTelefoneAsc(String telefone);
	
	/*@Query("SELECT * GestorCliente g inner join Armazem a on a.codigo=g.codigo_armazem where g.nome like '%=:nome%'  and a.codigo=:armazem")
	public List<GestorCliente> findByArmazemAndNomeIgnoreCaseContainingOrderByNomeAsc(@Param("nome")String nome, @Param("armazem") Long armazem);
	*/
	/*@Query("SELECT g GestorCliente g WHERE lower(g.telefone) LIKE LOWER(CONCAT ('%',:telefone,'%')) and g.armazem.codigo=:armazem" )
	public List<GestorCliente> filtrarGestoresDoArmazemPeloTelefone(@Param("telefone")String telefone, @Param("armazem") Long armazem);*/
	
	@Query("SELECT  g FROM GestorCliente g WHERE g.enabled = :status")
	List<GestorCliente> buscarPeloStatus(@Param("status") boolean status);
	
	@Query("SELECT  g FROM GestorCliente g WHERE  g.armazem.codigo =:armazem")
	List<GestorCliente> findByArmazem(@Param("armazem") Long armazem);
	
	Optional<GestorCliente> findByTelefone(@Param("telefone") String telefone);
	
	Optional<GestorCliente> findByNome(String nome);
	
}
