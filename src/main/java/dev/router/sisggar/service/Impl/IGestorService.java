package dev.router.sisggar.service.Impl;

import java.util.List;

import dev.router.sisggar.domain.dto.GestorClienteDto;

		


public interface IGestorService {

	public GestorClienteDto criarGestor(GestorClienteDto gestorDto);

	public GestorClienteDto buscarPeloCodigo(Long codigo);

	public GestorClienteDto actualizarGestor(GestorClienteDto gestorDto);

	public void eliminarGestor(Long codigo);

	public List<GestorClienteDto> buscarTodosGestores();
	
	public List<GestorClienteDto> listarGestorPorArmazem(Long armazem);
	
	public List<GestorClienteDto> filtrarPeloNome(String nome);

	public GestorClienteDto buscarPeloNome(String nome);
	
	
	public GestorClienteDto enabled(Long codigo, boolean status);
	
}
