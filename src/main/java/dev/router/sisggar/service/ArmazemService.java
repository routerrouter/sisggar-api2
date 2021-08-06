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

import dev.router.sisggar.domain.dto.ArmazemDto;
import dev.router.sisggar.domain.entity.Armazem;
import dev.router.sisggar.repository.ArmazemRepository;
import dev.router.sisggar.service.Impl.IArmazemService;

@Service
public class ArmazemService implements IArmazemService{

	@Autowired
	ArmazemRepository repository;

	@Override
	public ArmazemDto criarArmazem(ArmazemDto armazemDto) {
		Armazem armazem_model = new Armazem();
		BeanUtils.copyProperties(armazemDto, armazem_model);

		Optional<Armazem> existente = repository.findArmazemByDesignacao(armazemDto.getDesignacao());
		if (existente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "Já existe um Armazem com esta designação!");
		}
		armazem_model = repository.save(armazem_model);

		BeanUtils.copyProperties(armazem_model, armazemDto);

		return armazemDto;
	}

	@Override
	public ArmazemDto buscarPeloCodigo(Long codigo) {
		Armazem armazem_model = repository.findByCodigo(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Armazem não existe!"));
		ArmazemDto dto = new ArmazemDto();
		
		BeanUtils.copyProperties(armazem_model, dto);
		return dto;
	}

	@Override
	public ArmazemDto actualizarArmazem(ArmazemDto armazemDto) {
		Armazem armazem_model = repository.findByCodigo(armazemDto.getCodigo())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Armazem não existe!"));
		
		BeanUtils.copyProperties(armazemDto,armazem_model);
		
		armazem_model = repository.save(armazem_model);
		BeanUtils.copyProperties(armazem_model, armazemDto);

		return armazemDto;
	}

	@Override
	public void eliminarArmazem(Long codigo) {
		repository.findByCodigo(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Armazem não existe"));
		repository.deleteById(codigo);
		
	}

	@Override
	public List<ArmazemDto> buscarTodosArmazens() {
		Iterable<Armazem> itreable = repository.findAll();

		List<ArmazemDto> armazens = StreamSupport.stream(itreable.spliterator(), false).map(armazem -> {
			ArmazemDto dto = new ArmazemDto();
			BeanUtils.copyProperties(armazem, dto);
			return dto;
		}).collect(Collectors.toList());

		return armazens;
	}

	@Override
	public ArmazemDto buscarPelaDesignacao(String designacao) {
		Armazem armazem_model = repository.findArmazemByDesignacao(designacao).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Armazem não existe!"));
		ArmazemDto dto = new ArmazemDto();
		
		BeanUtils.copyProperties(armazem_model, dto);
		return dto;
	}

	@Override
	public List<ArmazemDto> pesquisar(String designacao) {
		Iterable<Armazem> itreable = repository.findByDesignacaoIgnoreCaseContainingOrderByDesignacaoAsc(designacao);

		List<ArmazemDto> armazens = StreamSupport.stream(itreable.spliterator(), false).map(armazem -> {
			ArmazemDto dto = new ArmazemDto();
			BeanUtils.copyProperties(armazem, dto);
			return dto;
		}).collect(Collectors.toList());

		return armazens;
	}

	
	@Override
	@Transactional
	public ArmazemDto enabled(Long codigo, boolean status) {
		ArmazemDto dto = new ArmazemDto();
		Armazem armazem_model = repository.findByCodigo(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Armazem não existe!"));
			
		repository.enabled(codigo,status);
		
		BeanUtils.copyProperties(armazem_model, dto);

		return dto;
	}
	
	


}
