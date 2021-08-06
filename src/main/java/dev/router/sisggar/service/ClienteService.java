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

import dev.router.sisggar.domain.dto.ClienteDto;
import dev.router.sisggar.domain.entity.Cliente;
import dev.router.sisggar.repository.ClienteRepository;
import dev.router.sisggar.service.Impl.IClienteService;

@Service
public class ClienteService implements IClienteService {

	@Autowired
	ClienteRepository repository;

	@Override
	public ClienteDto criarCliente(ClienteDto dto) {
		Cliente model = new Cliente();
		BeanUtils.copyProperties(dto, model);
		Optional<Cliente> cliente = repository.findByNif(dto.getNif());
		if (cliente.isPresent()) {
			throw new ResponseStatusException(HttpStatus.ALREADY_REPORTED, "Já existe um cliente com este NIF!");
		}

		BeanUtils.copyProperties(repository.save(model), dto);

		return dto;
	}

	@Override
	public ClienteDto actualizarCliente(ClienteDto dto) {

		Cliente model = repository.findById(dto.getCodigo())
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não existe!"));
		BeanUtils.copyProperties(dto, model);
		model = repository.save(model);
		BeanUtils.copyProperties(model, dto);

		return dto;
	}

	@Override
	public void eliminarCliente(Long codigo) {
		repository.findById(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não existe!"));
		repository.deleteById(codigo);
	}


	@Override
	public ClienteDto buscarPeloCodigo(Long codigo) {
		Cliente cliente = repository.findById(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não existe!"));
		ClienteDto dto = new ClienteDto();

		BeanUtils.copyProperties(cliente, dto);
		return dto;
	}

	@Override
	public List<ClienteDto> listagem() {
		Iterable<Cliente> itreable = repository.findAll();

		List<ClienteDto> clientes = StreamSupport.stream(itreable.spliterator(), false).map(cliente -> {
			ClienteDto dto = new ClienteDto();
			BeanUtils.copyProperties(cliente, dto);
			return dto;
		}).collect(Collectors.toList());

		return clientes;
	}

	@Override
	public List<ClienteDto> buscarPeloNome(String designacao) {
		Iterable<Cliente> iterable = repository.findByNomeIgnoreCaseContainingOrderByNomeAsc(designacao);

		List<ClienteDto> clientes = StreamSupport.stream(iterable.spliterator(), false).map(cliente -> {
			ClienteDto dto = new ClienteDto();
			BeanUtils.copyProperties(cliente, dto);
			return dto;
		}).collect(Collectors.toList());
		return clientes;
	}

	@Override
	public List<ClienteDto> buscarPeloNif(String designacao) {
		Iterable<Cliente> iterable = repository.findByNifIgnoreCaseContainingOrderByNifAsc(designacao);

		List<ClienteDto> clientes = StreamSupport.stream(iterable.spliterator(), false).map(cliente -> {
			ClienteDto dto = new ClienteDto();
			BeanUtils.copyProperties(cliente, dto);
			return dto;
		}).collect(Collectors.toList());
		return clientes;
	}

	@Override
	public List<ClienteDto> buscarPeloTelefone(String designacao) {
		Iterable<Cliente> iterable = repository.findByTelefoneIgnoreCaseContainingOrderByTelefoneAsc(designacao);

		List<ClienteDto> clientes = StreamSupport.stream(iterable.spliterator(), false).map(cliente -> {
			ClienteDto dto = new ClienteDto();
			BeanUtils.copyProperties(cliente, dto);
			return dto;
		}).collect(Collectors.toList());
		return clientes;
	}

	@Override
	public List<ClienteDto> listaPorLocalidades(Long codigo_localidade) {
		Iterable<Cliente> iterable = repository.findByLocalidade(codigo_localidade);

		List<ClienteDto> clientes = StreamSupport.stream(iterable.spliterator(), false).map(cliente -> {
			ClienteDto dto = new ClienteDto();
			BeanUtils.copyProperties(cliente, dto);
			return dto;
		}).collect(Collectors.toList());
		return clientes;
	}
	
	@Override
	public List<ClienteDto> listaPorGestor(Long codigo_gestor) {
		Iterable<Cliente> iterable = repository.findByGestor(codigo_gestor);
		
		List<ClienteDto> clientes = StreamSupport.stream(iterable.spliterator(), false).map(cliente -> {
			ClienteDto dto = new ClienteDto();
			BeanUtils.copyProperties(cliente, dto);
			return dto;
		}).collect(Collectors.toList());
		return clientes;
	}

	@Override
	public List<ClienteDto> clientesSemPosse() {
		Iterable<Cliente> iterable = repository.findClienteSemPosse();

		List<ClienteDto> clientes = StreamSupport.stream(iterable.spliterator(), false).map(cliente -> {
			ClienteDto dto = new ClienteDto();
			BeanUtils.copyProperties(cliente, dto);
			return dto;
		}).collect(Collectors.toList());
		return clientes;
	}
	
	@Override
	@Transactional
	public ClienteDto enabled(Long codigo, boolean status) {
		ClienteDto dto = new ClienteDto();
		Cliente armazem_model = repository.findById(codigo)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não existe!"));
		
		repository.enabled(codigo,status);
		BeanUtils.copyProperties(armazem_model, dto);

		return dto;
	}

}
