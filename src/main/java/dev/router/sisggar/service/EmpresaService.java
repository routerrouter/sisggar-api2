package dev.router.sisggar.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import dev.router.sisggar.domain.dto.EmpresaDto;
import dev.router.sisggar.domain.entity.Empresa;
import dev.router.sisggar.repository.EmpresaRepository;
import dev.router.sisggar.service.Impl.IEmpresaService;

@Service
public class EmpresaService implements IEmpresaService {

	@Autowired
	EmpresaRepository repository;

	@Override
	public EmpresaDto createEmpresa(EmpresaDto empresaDto) {
		Empresa empresa = new Empresa();
		BeanUtils.copyProperties(empresaDto, empresa);

		Optional<Empresa> existente = repository.findByDesignacao(empresaDto.getDesignacao());
		if (existente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,
					"Já existe uma empresa com esta designação!");
		}
		empresa = repository.save(empresa);

		BeanUtils.copyProperties(empresa, empresaDto);

		return empresaDto;
	}

	@Override
	public EmpresaDto getEmpresa(Long codigo) {
		Empresa empresa = repository.findByCodigo(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não existe!"));

		EmpresaDto dto = new EmpresaDto();
		BeanUtils.copyProperties(empresa, dto);

		return dto;
	}

	@Override
	public EmpresaDto updateEmpresa(EmpresaDto empresaDto) {
		Empresa empresa = repository.findByCodigo(empresaDto.getCodigo())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "A empresa inserida não existe!"));

		BeanUtils.copyProperties(empresaDto, empresa);

		empresa = repository.save(empresa);

		BeanUtils.copyProperties(empresa, empresaDto);

		return empresaDto;
	}

	@Override
	public void deleteEmpresa(Long codigo) {
		repository.findById(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não encontrada"));

		repository.deleteById(codigo);

	}

	@Override
	public List<EmpresaDto> getAllEmpresas() {
		Iterable<Empresa> itreable = repository.findAll();

		List<EmpresaDto> empresas = StreamSupport.stream(itreable.spliterator(), false).map(armazem -> {
			EmpresaDto dto = new EmpresaDto();
			BeanUtils.copyProperties(armazem, dto);
			return dto;
		}).collect(Collectors.toList());

		return empresas;
	}

	@Override
	public EmpresaDto getEmpresaByDesignacao(String designacao) {
		Empresa empresa = repository.findByDesignacao(designacao.toUpperCase())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não existe!"));

		EmpresaDto dto = new EmpresaDto();
		BeanUtils.copyProperties(empresa, dto);

		return dto;
	}

	@Override
	@Transactional
	public void enabled(Long codigo, boolean status) {

		repository.findById(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Empresa não existe!"));

		repository.enabled(codigo, status);
	}

}
