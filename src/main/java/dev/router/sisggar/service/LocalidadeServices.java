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

import dev.router.sisggar.domain.dto.LocalidadeDto;
import dev.router.sisggar.domain.entity.Localidade;
import dev.router.sisggar.repository.LocalidadeRepository;
import dev.router.sisggar.service.Impl.ILocalidadeService;

@Service
public class LocalidadeServices implements ILocalidadeService {

	@Autowired
	LocalidadeRepository repository;

	@Override
	public LocalidadeDto criarLocalidade(LocalidadeDto localidadeDto) {
		Localidade model = new Localidade();
		BeanUtils.copyProperties(localidadeDto, model);

		Optional<Localidade> localidade = repository.findByDescricaoAndArmazem(localidadeDto.getDescricao(),
				localidadeDto.getArmazem().getCodigo());
		if (localidade.isPresent()) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED,
					"Já existe uma localidade com esta descrição para este armazem!");
		}
		model = repository.save(model);
		BeanUtils.copyProperties(model, localidadeDto);

		return localidadeDto;
	}

	@Override
	public LocalidadeDto buscarPeloCodigo(Long codigo) {
		Localidade model = repository.findByCodigo(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Localidade não existe!"));
		LocalidadeDto dto = new LocalidadeDto();

		BeanUtils.copyProperties(model, dto);
		return dto;
	}

	@Override
	public LocalidadeDto actualizarLocalidade(LocalidadeDto localidadeDto) {
		Localidade model = repository.findByCodigo(localidadeDto.getCodigo())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Localidade não existe!"));

		BeanUtils.copyProperties(localidadeDto, model);
		repository.save(model);
		BeanUtils.copyProperties(model, localidadeDto);

		return localidadeDto;
	}

	@Override
	public void eliminarLocalidade(Long codigo) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<LocalidadeDto> buscarTodasLocalidades() {
		// Usamos o iterator para mostrar o conteúdo do ArrayList
		Iterable<Localidade> itreable = repository.findAll();

		List<LocalidadeDto> armazens = StreamSupport.stream(itreable.spliterator(), false).map(armazem -> {
			LocalidadeDto dto = new LocalidadeDto();
			BeanUtils.copyProperties(armazem, dto);
			return dto;
		}).collect(Collectors.toList());

		return armazens;
	}

	@Override
	public List<LocalidadeDto> buscarTodasLocalidadesPeloArmazem(Long codigo_armazem) {
		// Usamos o iterator para mostrar o conteúdo do ArrayList
		Iterable<Localidade> itreable = repository.findByArmazem(codigo_armazem);

		List<LocalidadeDto> armazens = StreamSupport.stream(itreable.spliterator(), false).map(armazem -> {
			LocalidadeDto dto = new LocalidadeDto();
			BeanUtils.copyProperties(armazem, dto);
			return dto;
		}).collect(Collectors.toList());

		return armazens;
	}

	@Override
	public LocalidadeDto buscarPelaDesignacao(String descricao) {
		Localidade model = repository.findByDescricaoIgnoreCase(descricao).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Não existe localidade com esta descrição!"));
		LocalidadeDto dto = new LocalidadeDto();

		BeanUtils.copyProperties(model, dto);
		return dto;
	}

	@Override
	public List<LocalidadeDto> pesquisarTodas(String descricao) {
		Iterable<Localidade> itreable = repository.findByDescricaoIgnoreCaseContainingOrderByDescricaoAsc(descricao);

		List<LocalidadeDto> localidades = StreamSupport.stream(itreable.spliterator(), false).map(localidade -> {
			LocalidadeDto dto = new LocalidadeDto();
			BeanUtils.copyProperties(localidade, dto);
			return dto;
		}).collect(Collectors.toList());

		return localidades;
	}

	@Override
	public List<LocalidadeDto> pesquisarPorArmazem(String descricao, Long armazem) {
		Iterable<Localidade> itreable = repository
				.findByDescricaoIgnoreCaseContainingAndArmazemOrderByDescricaoAsc(descricao, armazem);

		List<LocalidadeDto> localidades = StreamSupport.stream(itreable.spliterator(), false).map(localidade -> {
			LocalidadeDto dto = new LocalidadeDto();
			BeanUtils.copyProperties(localidade, dto);
			return dto;
		}).collect(Collectors.toList());

		return localidades;
	}

	@Override
	public LocalidadeDto enabled(Long codigo, boolean status) {
		LocalidadeDto dto = new LocalidadeDto();

		Localidade model = repository.findByCodigo(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Localidade não existe"));
		repository.enabled(codigo, status);

		BeanUtils.copyProperties(model, dto);
		return dto;
	}

}
