package dev.router.sisggar.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.router.sisggar.domain.dto.ClienteDto;
import dev.router.sisggar.exception.api.ApiErrorResponse;
import dev.router.sisggar.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/cliente/v1")
public class ClienteController {

	@Autowired
	private ClienteService service;

	// Http post method - Create operation
	@Operation(summary = "Registar Cliente", description = "Registar novo cliente", method = "POST", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registo efectuado com sucesso!", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PostMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ClienteDto> criarCliente(
			@Parameter(name = "ClienteDto", description = "Cliente Dto", required = true, content = @Content(schema = @Schema(implementation = ClienteDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid ClienteDto armazemDto) {

		ClienteDto dto = service.criarCliente(armazemDto);
		Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
		dto.add(link);
		return ResponseEntity.ok(dto);
	}

	// Http post method - Update operation
	@Operation(summary = "Actualizar dados cliente", description = "Actualizar dados do cliente", method = "PUT", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registo actualizada com sucesso!", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PutMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ClienteDto> actualizarCliente(
			@Parameter(name = "ClienteDto", description = "Cliente Dto", required = true, content = @Content(schema = @Schema(implementation = ClienteDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid ClienteDto armazemDto) {

		ClienteDto dto = service.actualizarCliente(armazemDto);
		Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
		dto.add(link);
		return ResponseEntity.ok(dto);
	}

	// Http post method - Delete operation
	@Operation(summary = "Eliminar registo do cliente", description = "Eliminar dados do cliente", method = "DELETE", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registo eliminado com sucesso!", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@DeleteMapping(path = "/{codigo}")
	public void eliminarCliente(@PathVariable("codigo") Long codigo) {
		service.eliminarCliente(codigo);
	}

	// Http post method - Find by cod operation
	@Operation(summary = "Buscar Cliente pelo código", description = "Buscar Cliente pelo código", method = "GET", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida!", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })

	@GetMapping(path = "/{codigo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<ClienteDto> buscarPeloCodigo(@PathVariable(name = "codigo") @Positive Long codigo) {

		Link link = linkTo(methodOn(ArmazemController.class).buscarArmazemPeloCodigo(codigo)).withSelfRel();
		/*
		 * Link link_movimentos =
		 * linkTo(methodOn(MovimentosController.class).movimentacoesCliente(codigo, 0,
		 * 12, "asc")) .withRel("movimentos"); Listar todas as movimentacoes do
		 * cliente...
		 */
		ClienteDto dto = service.buscarPeloCodigo(codigo);
		dto.add(link);
		// dto.add(link_movimentos);

		return EntityModel.of(dto);
	}

	// Http post method - Find operation
	@Operation(summary = "Listagem dos Clientes registados", description = "Listar dados dos clientes", method = "GET", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "")
	public ResponseEntity<CollectionModel<ClienteDto>> listarTodos() {

		List<ClienteDto> list = service.listagem();
		for (ClienteDto dto : list) {

			Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
			dto.add(link);
			// dto.add(linkTo(methodOn(MovimentosController.class).movimentacoesCliente(dto.getCodigo(),
			// 0, 12, "asc")) .withRel("movimentos"));

		}
		Link listagemlink = linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, listagemlink));
	}

	// Http post method - Find operation
	@Operation(summary = "Filtro pelo nome", description = "Filtro pelo  nome", method = "GET", tags = { "Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/pesquisar/nome")
	public ResponseEntity<CollectionModel<ClienteDto>> filtrarNome(
			@RequestParam(required = false, defaultValue = "%") String nome) {

		List<ClienteDto> list = service.buscarPeloNome(nome);
		for (ClienteDto dto : list) {

			Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
			dto.add(link);
			// dto.add(linkTo(methodOn(MovimentosController.class).movimentacoesCliente(dto.getCodigo(),
			// 0, 12, "asc")) .withRel("movimentos"));

		}
		Link listagemlink = linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, listagemlink));
	}

	// Http post method - Find operation
	@Operation(summary = "Filtro pelo NIF", description = "Filtro pelo  nif", method = "GET", tags = { "Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/pesquisar/nif")
	public ResponseEntity<CollectionModel<ClienteDto>> filtrarNif(
			@RequestParam(required = false, defaultValue = "5") String nif) {

		List<ClienteDto> list = service.buscarPeloNif(nif);
		for (ClienteDto dto : list) {

			Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
			dto.add(link);
			// dto.add(linkTo(methodOn(MovimentosController.class).movimentacoesCliente(dto.getCodigo(),
			// 0, 12, "asc")) .withRel("movimentos"));

		}
		Link listagemlink = linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, listagemlink));
	}

	// Http post method - Find operation
	@Operation(summary = "Filtro pelo Telefone", description = "Filtro pelo  telefone", method = "GET", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/pesquisar/telefone")
	public ResponseEntity<CollectionModel<ClienteDto>> filtrarTelefone(
			@RequestParam(required = false, defaultValue = "9") String telefone) {

		List<ClienteDto> list = service.buscarPeloTelefone(telefone);
		for (ClienteDto dto : list) {

			Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
			dto.add(link);
			// dto.add(linkTo(methodOn(MovimentosController.class).movimentacoesCliente(dto.getCodigo(),
			// 0, 12, "asc")) .withRel("movimentos"));

		}
		Link listagemlink = linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, listagemlink));
	}
	// Http post method - Find operation
	@Operation(summary = "Listagem por Localidade", description = "Listar clientes por localidades", method = "GET", tags = {
	"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/clientesBylocalidade")
	public ResponseEntity<CollectionModel<ClienteDto>> listagemPorLocalidade(
			@RequestParam(required = false) Long localidade) {
		
		List<ClienteDto> list = service.listaPorLocalidades(localidade);
		for (ClienteDto dto : list) {
			
			Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
			dto.add(link);
			// dto.add(linkTo(methodOn(MovimentosController.class).movimentacoesCliente(dto.getCodigo(),
			// 0, 12, "asc")) .withRel("movimentos"));
			
		}
		Link listagemlink = linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, listagemlink));
	}
	// Http post method - Find operation
	@Operation(summary = "Listagem por Gestor", description = "Listar clientes por Gestor", method = "GET", tags = {
	"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/clientesByGestor")
	public ResponseEntity<CollectionModel<ClienteDto>> listagemPorGestor(
			@RequestParam(required = false) Long gestor) {
		
		List<ClienteDto> list = service.listaPorGestor(gestor);
		for (ClienteDto dto : list) {
			
			Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
			dto.add(link);
			// dto.add(linkTo(methodOn(MovimentosController.class).movimentacoesCliente(dto.getCodigo(),
			// 0, 12, "asc")) .withRel("movimentos"));
			
		}
		Link listagemlink = linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, listagemlink));
	}
	
	// Http post method - Find operation
	@Operation(summary = "Clientes sem Posses", description = "Listar clientes sem posses de garrafas", method = "GET", tags = {
	"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/semPosse")
	public ResponseEntity<CollectionModel<ClienteDto>> clientesSemPosse() {
		
		List<ClienteDto> list = service.clientesSemPosse();
		for (ClienteDto dto : list) {
			
			Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto.getCodigo())).withSelfRel();
			dto.add(link);
			// dto.add(linkTo(methodOn(MovimentosController.class).movimentacoesCliente(dto.getCodigo(),
			// 0, 12, "asc")) .withRel("movimentos"));
			
		}
		Link listagemlink = linkTo(methodOn(ClienteController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, listagemlink));
	}

	@Operation(summary = "Ativar ou desactivar cliente", description = "Ativa ou desactiva um determinado cliente", method = "PATCH", tags = {
			"Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Dados actualizados com sucesso!", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do armazem não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PatchMapping(path = "/status/{codigo}")
	public EntityModel<ClienteDto> enabled(
			@Parameter(name = "codigo", example = "5", required = true, description = "Código do cliente", in = ParameterIn.PATH) 
			@PathVariable(name = "codigo") @Positive Long codigo,
			@RequestParam("status") Boolean status) {

		ClienteDto dto_criado = service.enabled(codigo, status);
		Link link = linkTo(methodOn(ClienteController.class).buscarPeloCodigo(dto_criado.getCodigo())).withSelfRel();
		dto_criado.add(link);
		return EntityModel.of(dto_criado);

	}

}