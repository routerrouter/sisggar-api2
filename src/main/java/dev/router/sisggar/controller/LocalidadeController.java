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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.router.sisggar.domain.dto.LocalidadeDto;
import dev.router.sisggar.exception.api.ApiErrorResponse;
import dev.router.sisggar.service.LocalidadeServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

//@Api(tags = "LocalidadeEndpoint")
@RestController
@RequestMapping("/api/localidade/v1")
public class LocalidadeController {

	@Autowired
	private LocalidadeServices service;

	// Http post method - Create operation
	@Operation(summary = "Registar Localidade", description = "Registar nova localidade de entregas", method = "POST", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registo efectuado com sucesso!", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PostMapping(path = "/localidade", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<LocalidadeDto> criarLocalidade(
			@Parameter(name = "LocalidadeDto", description = "LocalidadeDto representação do corpo do recurso", required = true, content = @Content(schema = @Schema(implementation = LocalidadeDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid LocalidadeDto localidadeDto) {

		LocalidadeDto dto = service.criarLocalidade(localidadeDto);
		Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePeloCodigo(dto.getCodigo()))
				.withSelfRel();
		dto.add(link);
		return ResponseEntity.ok(dto);
	}

	// Http post method - Update operation
	@Operation(summary = "Actualizar Localidade", description = "Actualizar  localidade de entregas", method = "POST", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registo efectuado com sucesso!", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do armazem não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PutMapping(path = "/localidade", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<LocalidadeDto> actualizarLocalidade(
			@Parameter(name = "LocalidadeDto", description = "LocalidadeDto representação do corpo do recurso", required = true, content = @Content(schema = @Schema(implementation = LocalidadeDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid LocalidadeDto localidadeDto) {

		LocalidadeDto dto = service.actualizarLocalidade(localidadeDto);
		Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePeloCodigo(dto.getCodigo()))
				.withSelfRel();
		dto.add(link);
		return ResponseEntity.ok(dto);
	}

	// Http post method - Get operation
	@Operation(summary = "Buscar localidade", description = "Buscar localidade pelo código", method = "GET", tags = {
			"Localidade" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida!", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })

	@GetMapping(path = "/{codigo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<LocalidadeDto> buscarLocalidadePeloCodigo(@PathVariable(name = "codigo") @Positive Long codigo) {

		Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePeloCodigo(codigo)).withSelfRel();
		Link clientesLink = linkTo(methodOn(ClienteController.class).listagemPorLocalidade(codigo)).withRel("clientes");
		LocalidadeDto dto = service.buscarPeloCodigo(codigo);
		dto.add(link);
		dto.add(clientesLink);

		return EntityModel.of(dto);
	}

	// Http post method - Get operation
	@Operation(summary = "Buscar localidade", description = "Buscar localidade pela descrição", method = "GET", tags = {
			"Localidade" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida!", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })

	@GetMapping(path = "/buscar/{descricao}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<LocalidadeDto> buscarLocalidadePelaDescricao(
			@PathVariable(name = "descricao") @Positive String descricao) {
		Long codigo_armazem = service.buscarPelaDesignacao(descricao).getCodigo();
		Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePelaDescricao(descricao)).withSelfRel();
		Link clientesLink = linkTo(methodOn(ClienteController.class).listagemPorLocalidade(codigo_armazem))
				.withRel("clientes");
		LocalidadeDto dto = service.buscarPeloCodigo(codigo_armazem);
		dto.add(link);
		dto.add(clientesLink);

		return EntityModel.of(dto);
	}

	@Operation(summary = "Buscar todas as localidades registadas", description = "Buscar todos os registros das localidades", method = "GET", tags = {
			"Localidade" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "203", description = "Sem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do localidade não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "")
	public ResponseEntity<CollectionModel<LocalidadeDto>> listarTodos() {

		List<LocalidadeDto> list = service.buscarTodasLocalidades();
		for (LocalidadeDto dto : list) {
			Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePeloCodigo(dto.getCodigo()))
					.withSelfRel();
			Link clientesLink = linkTo(methodOn(ClienteController.class).listagemPorLocalidade(dto.getCodigo()))
					.withRel("clientes");

			dto.add(link);
			dto.add(clientesLink);
		}
		Link localidadeslink = linkTo(methodOn(LocalidadeController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, localidadeslink));
	}

	@Operation(summary = "Filtro de localidades", description = "Filtrar pela descrição todos os registros das localidades", method = "GET", tags = {
			"Localidade" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "203", description = "Sem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do localidade não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/filtrar")
	public ResponseEntity<CollectionModel<LocalidadeDto>> pesquisar(
			@RequestParam(required = false, defaultValue = "%") String descricao) {

		List<LocalidadeDto> list = service.pesquisarTodas(descricao);
		for (LocalidadeDto dto : list) {
			Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePeloCodigo(dto.getCodigo()))
					.withSelfRel();
			dto.add(link);
			Link clientesLink = linkTo(methodOn(ClienteController.class).listagemPorLocalidade(dto.getCodigo()))
					.withRel("clientes");
			dto.add(clientesLink);
		}
		Link todasLocalidadeslink = linkTo(methodOn(LocalidadeController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, todasLocalidadeslink));
	}

	@Operation(summary = "Filtro de localidades", description = "Filtrar localidade de um armazem pela descrição ", method = "GET", tags = {
			"Localidade" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "203", description = "Sem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do localidade não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/filtrar/armazem")
	public ResponseEntity<CollectionModel<LocalidadeDto>> pesquisarPeloArmazem(
			@RequestParam(required = false, defaultValue = "%") String descricao,
			@RequestParam("armazem") Long armazem) {

		List<LocalidadeDto> list = service.pesquisarPorArmazem(descricao, armazem);
		for (LocalidadeDto dto : list) {
			Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePeloCodigo(dto.getCodigo()))
					.withSelfRel();
			dto.add(link);
			Link clientesLink = linkTo(methodOn(ClienteController.class).listagemPorLocalidade(dto.getCodigo()))
					.withRel("clientes");
			dto.add(clientesLink);
		}
		Link todasLocalidadeslink = linkTo(methodOn(LocalidadeController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, todasLocalidadeslink));
	}
	
	@Operation(summary = "Buscar de localidades", description = "Buscar localidades de um armazem ", method = "GET", tags = {
	"Localidade" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "203", description = "Sem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do localidade não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/buscarPeloArmazem/{armazem}")
	public ResponseEntity<CollectionModel<LocalidadeDto>> buscarPeloArmazem(
			@PathVariable(name="armazem") Long armazem) {
		
		List<LocalidadeDto> list = service.buscarTodasLocalidadesPeloArmazem(armazem);
		for (LocalidadeDto dto : list) {
			Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePeloCodigo(dto.getCodigo()))
					.withSelfRel();
			dto.add(link);
			Link clientesLink = linkTo(methodOn(ClienteController.class).listagemPorLocalidade(dto.getCodigo()))
					.withRel("clientes");
			dto.add(clientesLink);
		}
		Link todasLocalidadeslink = linkTo(methodOn(LocalidadeController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, todasLocalidadeslink));
	}

	@Operation(summary = "Ativar ou desactivar localidade", description = "Ativa ou desactiva uma determinada localidade", method = "PATCH", tags = {
			"Localidade" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Dados actualizados com sucesso!", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = LocalidadeDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do armazem não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PatchMapping(path = "/status/{codigo}")
	public EntityModel<LocalidadeDto> enabled(
			@Parameter(name = "codigo", example = "5", required = true, description = "Código da localidade", in = ParameterIn.PATH) 
			@PathVariable(name = "codigo") @Positive Long codigo,
			@RequestParam("status") Boolean status) {

		LocalidadeDto dto_criado = service.enabled(codigo, status);
		Link link = linkTo(methodOn(LocalidadeController.class).buscarLocalidadePeloCodigo(dto_criado.getCodigo()))
				.withSelfRel();
		dto_criado.add(link);
		return EntityModel.of(dto_criado);

	}

}