package dev.router.sisggar.service.Impl;

import java.util.List;

import dev.router.sisggar.domain.dto.LocalidadeDto;

		


public interface ILocalidadeService {

	public LocalidadeDto criarLocalidade(LocalidadeDto empresaDto);

	public LocalidadeDto buscarPeloCodigo(Long codigo);

	public LocalidadeDto actualizarLocalidade(LocalidadeDto empresaDto);

	public void eliminarLocalidade(Long codigo);

	public List<LocalidadeDto> buscarTodasLocalidades();
	
	public List<LocalidadeDto> buscarTodasLocalidadesPeloArmazem(Long armazem);

	public LocalidadeDto buscarPelaDesignacao(String designacao);
	
	public List<LocalidadeDto> pesquisarTodas(String designacao);
	
	public List<LocalidadeDto> pesquisarPorArmazem(String designacao, Long armazem);
	
	public LocalidadeDto enabled(Long codigo, boolean status);
	
}
