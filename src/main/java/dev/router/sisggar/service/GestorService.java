package dev.router.sisggar.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import dev.router.sisggar.domain.dto.GestorClienteDto;
import dev.router.sisggar.domain.entity.GestorCliente;
import dev.router.sisggar.repository.GestorRepository;
import dev.router.sisggar.service.Impl.IGestorService;

@Service
public class GestorService implements IGestorService {

	@Autowired
	private GestorRepository repository;

	@Override
	public GestorClienteDto criarGestor(GestorClienteDto gestorDto) {
		GestorCliente model = new GestorCliente();
		BeanUtils.copyProperties(gestorDto, model);

		Optional<GestorCliente> exist = repository.findByTelefone(gestorDto.getTelefone());
		if (exist.isPresent()) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "Já existe um Gestor com este telefone!");
		}

		model = repository.save(model);
		BeanUtils.copyProperties(model, gestorDto);
		return gestorDto;
	}

	@Override
	public GestorClienteDto buscarPeloCodigo(Long codigo) {
		GestorClienteDto dto = new GestorClienteDto();
		GestorCliente model = repository.findById(codigo).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe gestor com este código"));
		BeanUtils.copyProperties(model, dto);
		return dto;
	}

	@Override
	public GestorClienteDto actualizarGestor(GestorClienteDto gestorDto) {

		GestorCliente model = repository.findById(gestorDto.getCodigo()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe gestor com este código"));

		BeanUtils.copyProperties(gestorDto, model);
		model = repository.save(model);
		BeanUtils.copyProperties(model, gestorDto);
		return gestorDto;
	}

	@Override
	public void eliminarGestor(Long codigo) {
		GestorCliente model = repository.findById(codigo).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe gestor com este código"));

		repository.delete(model);

	}

	@Override
	public List<GestorClienteDto> buscarTodosGestores() {
		Iterable<GestorCliente> itreable = repository.findAll();

		List<GestorClienteDto> gestores = StreamSupport.stream(itreable.spliterator(), false).map(gestor -> {
			GestorClienteDto dto = new GestorClienteDto();
			BeanUtils.copyProperties(gestor, dto);
			return dto;
		}).collect(Collectors.toList());

		return gestores;
	}

	@Override
	public List<GestorClienteDto> listarGestorPorArmazem(Long armazem) {
		Iterable<GestorCliente> itreable = repository.findByArmazem(armazem);

		List<GestorClienteDto> gestores = StreamSupport.stream(itreable.spliterator(), false).map(gestor -> {
			GestorClienteDto dto = new GestorClienteDto();
			BeanUtils.copyProperties(gestor, dto);
			return dto;
		}).collect(Collectors.toList());

		return gestores;
	}

	@Override
	public List<GestorClienteDto> filtrarPeloNome(String nome) {
		Iterable<GestorCliente> itreable = repository.findByNomeIgnoreCaseContainingOrderByNomeAsc(nome);

		List<GestorClienteDto> gestores = StreamSupport.stream(itreable.spliterator(), false).map(gestor -> {
			GestorClienteDto dto = new GestorClienteDto();
			BeanUtils.copyProperties(gestor, dto);
			return dto;
		}).collect(Collectors.toList());

		return gestores;
	}

	@Override
	public GestorClienteDto buscarPeloNome(String nome) {
		GestorClienteDto dto = new GestorClienteDto();
		GestorCliente model = repository.findByNome(nome).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe gestor com este código"));

		BeanUtils.copyProperties(dto, model);
		model = repository.save(model);
		BeanUtils.copyProperties(model, dto);
		return dto;
	}

	@Override
	public GestorClienteDto enabled(Long codigo, boolean status) {
		// TODO Auto-generated method stub
		return null;
	}

}
