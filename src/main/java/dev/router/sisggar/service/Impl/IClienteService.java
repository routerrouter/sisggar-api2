package dev.router.sisggar.service.Impl;

import java.util.List;

import dev.router.sisggar.domain.dto.ClienteDto;


public interface IClienteService {

	public ClienteDto criarCliente(ClienteDto clienteDto);
	public ClienteDto actualizarCliente(ClienteDto clienteDto);
	public void eliminarCliente(Long codigo);
	public ClienteDto enabled(Long codigo, boolean status);
	public ClienteDto buscarPeloCodigo(Long codigo);
	public List<ClienteDto> listagem();
	public List<ClienteDto> buscarPeloNome(String designacao);
	public List<ClienteDto> buscarPeloNif(String designacao);
	public List<ClienteDto> buscarPeloTelefone(String designacao);
	public List<ClienteDto> listaPorLocalidades(Long codigo_localidade);
	public List<ClienteDto> clientesSemPosse();
	public List<ClienteDto> listaPorGestor(Long codigo_gestor);
	
	
	
}