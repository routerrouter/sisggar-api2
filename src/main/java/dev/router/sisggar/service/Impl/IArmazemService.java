package dev.router.sisggar.service.Impl;

import java.util.List;

import dev.router.sisggar.domain.dto.ArmazemDto;

		


public interface IArmazemService {

	public ArmazemDto criarArmazem(ArmazemDto empresaDto);

	public ArmazemDto buscarPeloCodigo(Long codigo);

	public ArmazemDto actualizarArmazem(ArmazemDto empresaDto);

	public void eliminarArmazem(Long codigo);

	public List<ArmazemDto> buscarTodosArmazens();

	public ArmazemDto buscarPelaDesignacao(String designacao);
	
	public List<ArmazemDto> pesquisar(String designacao);
	
	public ArmazemDto enabled(Long codigo, boolean status);
	
}
