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

import dev.router.sisggar.domain.dto.ArmazemDto;
import dev.router.sisggar.exception.api.ApiErrorResponse;
import dev.router.sisggar.service.ArmazemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;


@RestController
@RequestMapping("/api/armazem/v1")
public class ArmazemController {

	@Autowired
	private ArmazemService service;

	// Http post method - Create operation
	@Operation(summary = "Registar Armazem", description = "Registar novo Armazem", method = "POST", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registo efectuado com sucesso!", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PostMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ArmazemDto> criarArmazem(
			@Parameter(name = "ArmazemDto", description = "Empresa Dto no corpo do pedido", 
			required = true, content = @Content(schema = @Schema(implementation = ArmazemDto.class)), in = ParameterIn.DEFAULT) 
			@RequestBody @Valid ArmazemDto armazemDto) {

		ArmazemDto dto = service.criarArmazem(armazemDto);
		Link link = linkTo(methodOn(ArmazemController.class).buscarArmazemPeloCodigo(dto.getCodigo())).withSelfRel();
		dto.add(link);
		return ResponseEntity.ok(dto);
	}

	@Operation(summary = "Actualizar dados do Armazem", description = "Actualização dos dados registados de um armazem de garrafas", method = "PUT", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Dados actualizados com sucesso!", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "400", description = "Erro na requisição! Verifique as informações enviadas.", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do armazem não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PutMapping(path = "")
	public EntityModel<ArmazemDto> actualizarArmazem(@RequestBody @Valid ArmazemDto dto) {
		ArmazemDto dto_criado = service.actualizarArmazem(dto);
		Link link = linkTo(methodOn(ArmazemController.class).buscarArmazemPeloCodigo(dto_criado.getCodigo()))
				.withSelfRel();
		dto_criado.add(link);
		return EntityModel.of(dto_criado);

	}

	@Operation(summary = "Eliminar armazem", description = "Eliminar dados de registo de um armazem", method = "DELETE", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Armazem eliminado com sucesso!", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do armazem não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@DeleteMapping(path = "/{codigo}")
	public void eliminarArmazem(@PathVariable("codigo") Long codigo) {
		service.eliminarArmazem(codigo);
	}

	@Operation(summary = "Ativar ou desactivar armazem", description = "Ativa ou desactiva um determinado armazem", method = "PATCH", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Dados actualizados com sucesso!", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do armazem não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PatchMapping(path = "/status/{codigo}")
	public EntityModel<ArmazemDto> enabled(
			@Parameter(name = "codigo", example = "5", required = true, description = "Código do armazem", in = ParameterIn.PATH)
			@PathVariable(name = "codigo") @Positive Long codigo,
			@RequestParam("status") Boolean status) {

		ArmazemDto dto_criado = service.enabled(codigo, status);
		Link link = linkTo(methodOn(ArmazemController.class).buscarArmazemPeloCodigo(dto_criado.getCodigo()))
				.withSelfRel();
		dto_criado.add(link);
		return EntityModel.of(dto_criado);

	}

	// Http post method - Create operation
	@Operation(summary = "Buscar Armazem pelo código", description = "Buscar Armazem pelo código", method = "GET", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida!", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })

	@GetMapping(path = "/{codigo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<ArmazemDto> buscarArmazemPeloCodigo(@PathVariable(name = "codigo") @Positive Long codigo) {

		Link link = linkTo(methodOn(ArmazemController.class).buscarArmazemPeloCodigo(codigo)).withSelfRel();
		Link link_localidades = linkTo(methodOn(LocalidadeController.class).buscarPeloArmazem(codigo))
				.withRel("localidades");
		ArmazemDto dto = service.buscarPeloCodigo(codigo);
		dto.add(link);
		dto.add(link_localidades);

		return EntityModel.of(dto);
	}

	@Operation(summary = "Buscar todos os Armazens registados", description = "Buscar todos os registros dos armazens de garrafas", method = "GET", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do armazem não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "")
	public ResponseEntity<CollectionModel<ArmazemDto>> listarTodos() {

		List<ArmazemDto> list = service.buscarTodosArmazens();
		for (ArmazemDto dto : list) {
			Link link = linkTo(methodOn(ArmazemController.class).buscarArmazemPeloCodigo(dto.getCodigo()))
					.withSelfRel();
			dto.add(link);
			/*dto.add(linkTo(methodOn(LocalidadeController.class).localidades(dto.getCodigo(), 0, 12, "asc"))
					.withRel("localidades"));*/
		}
		Link todosArmazenslink = linkTo(methodOn(ArmazemController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, todosArmazenslink));
	}

	@Operation(summary = "Pesquisar", description = "Filtro armazens pela designacao", method = "GET", tags = {
			"Armazem" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = ArmazemDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "403", description = "Não tem permissão para acessar este recurso", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do armazem não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/pesquisar")
	public ResponseEntity<CollectionModel<ArmazemDto>> pesquisar(
			@RequestParam(required = false, defaultValue = "%") String designacao) {

		List<ArmazemDto> list = service.pesquisar(designacao);
		for (ArmazemDto dto : list) {
			Link link = linkTo(methodOn(ArmazemController.class).buscarArmazemPeloCodigo(dto.getCodigo()))
					.withSelfRel();
			dto.add(link);
			/*dto.add(linkTo(methodOn(LocalidadeController.class).localidades(dto.getCodigo(), 0, 12, "asc"))
					.withRel("localidades"));*/
		}
		Link todosArmazenslink = linkTo(methodOn(ArmazemController.class).listarTodos()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, todosArmazenslink));
	}

}