package dev.router.sisggar.service.Impl;

import java.util.List;

import dev.router.sisggar.domain.dto.EmpresaDto;


public interface IEmpresaService {

	public EmpresaDto createEmpresa(EmpresaDto empresaDto);

	public EmpresaDto getEmpresa(Long codigo);

	public EmpresaDto updateEmpresa(EmpresaDto empresaDto);

	public void deleteEmpresa(Long codigo);

	public List<EmpresaDto> getAllEmpresas();

	public EmpresaDto getEmpresaByDesignacao(String designacao);
	
	public void enabled(Long codigo, boolean status);
	
}
