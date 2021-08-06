package dev.router.sisggar.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.router.sisggar.domain.dto.GestorClienteDto;
import dev.router.sisggar.exception.api.ApiErrorResponse;
import dev.router.sisggar.service.GestorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/gestor_cliente/v1")
public class GestorClienteController {

	@Autowired
	private GestorService gestorService;

	@Operation(summary = "Buscar todos os Gestores registados", description = "Buscar todos os registros de gestor de entregas", method = "GET", tags = {
			"Gestor Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = GestorClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do gestor não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "")
	public ResponseEntity<CollectionModel<GestorClienteDto>> findAll() {

		List<GestorClienteDto> list = gestorService.buscarTodosGestores();
		for (GestorClienteDto dto : list) {
			Link link = linkTo(methodOn(GestorClienteController.class).findByCodigo(dto.getCodigo())).withSelfRel();
			Link link_clientes = linkTo(methodOn(ClienteController.class).listagemPorGestor(dto.getCodigo())).withRel("clientes");
			dto.add(link);
			dto.add(link_clientes);
			/*
			 * LINK DAS ENTREGAS FEITAS PELO GESTOR..
			 * dto.add(linkTo(methodOn(LocalidadeController.class).localidades(dto.getCodigo
			 * (), 0, 12, "asc")) .withRel("localidades"));
			 */
		}
		Link todosArmazenslink = linkTo(methodOn(GestorClienteController.class).findAll()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, todosArmazenslink));
	}

	@Operation(summary = "Buscar gestor pelo Código", description = "Buscar um gestor especifico pelo seu código", method = "GET", tags = {
			"Gestor Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = GestorClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do gestor não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/{codigo}")
	public EntityModel<GestorClienteDto> findByCodigo(@PathVariable(name = "codigo") @Positive Long codigo) {

		Link link = linkTo(methodOn(ClienteController.class).listagemPorGestor(codigo)).withRel("clientes");
		GestorClienteDto dto = gestorService.buscarPeloCodigo(codigo);
		/*
		 * LINK DAS ENTREGAS FEITAS PELO GESTOR..
		 * dto.add(linkTo(methodOn(MovimentosController.class).entregas(dto.getCodigo
		 * (), 0, 12, "asc")) .withRel("movimentos"));
		 */
		dto.add(link);
		
		return EntityModel.of(dto);
	}
	
	@Operation(summary = "Buscar gestor pelo Armazém", description = "Listar gestores por Armazém", method = "GET", tags = {
	"Gestor Cliente" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = GestorClienteDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso do gestor não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/armazem/{codigo}")
	public EntityModel<GestorClienteDto> findByArmazem(@PathVariable(name = "codigo") @Positive Long codigo) {
		
		Link link = linkTo(methodOn(ClienteController.class).listagemPorGestor(codigo)).withRel("clientes");
		GestorClienteDto dto = gestorService.buscarPeloCodigo(codigo);
		/*
		 * LINK DAS ENTREGAS FEITAS PELO GESTOR..
		 * dto.add(linkTo(methodOn(MovimentosController.class).entregas(dto.getCodigo
		 * (), 0, 12, "asc")) .withRel("movimentos"));
		 */
		dto.add(link);
		
		return EntityModel.of(dto);
	}

}
