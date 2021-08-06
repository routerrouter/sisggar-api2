package dev.router.sisggar.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.router.sisggar.domain.entity.Cliente;



@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
	
	Page<Cliente> findAll(Pageable pageable);
	
	@Modifying
	@Query("UPDATE Cliente c SET c.enabled = :status WHERE c.codigo =:codigo")
	void enabled(@Param("codigo") Long codigo, @Param("status") Boolean status);
	
	public List<Cliente>findByNomeIgnoreCaseContainingOrderByNomeAsc(String nome);
	public List<Cliente>findByNifIgnoreCaseContainingOrderByNifAsc(String nif);
	public List<Cliente>findByTelefoneIgnoreCaseContainingOrderByTelefoneAsc(String telefone);
	public List<Cliente>findByEnderecoIgnoreCaseContainingOrderByEnderecoAsc(String endereco);
	
	@Query("SELECT  c FROM Cliente c WHERE c.enabled = :status")
	List<Cliente> buscarPeloStatus(@Param("status") boolean status);
	@Query("SELECT  c FROM Cliente c WHERE  c.localidade.codigo =:localidade")
	List<Cliente> findByLocalidade(@Param("localidade") Long localidade);
	@Query("SELECT  c FROM Cliente c WHERE c.posse = 0")
	List<Cliente> findClienteSemPosse();
	Optional<Cliente> findByTelefone(@Param("telefone") String telefone);
	Optional<Cliente> findByNif(@Param("nif") String nif);
	Optional<Cliente> findByNome(String nome);
	
	@Query("SELECT c FROM Cliente c WHERE c.gestor.codigo =:gestor")
	List<Cliente> findByGestor(@Param("gestor") Long gestor);
	

	


}
