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

import dev.router.sisggar.domain.dto.EmpresaDto;
import dev.router.sisggar.exception.api.ApiErrorResponse;
import dev.router.sisggar.service.Impl.IEmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

//@Api(tags = "EmpresaEndpoint")
@RestController
@RequestMapping("/api/empresa/v1")
public class EmpresaController {

	@Autowired
	private IEmpresaService service;

	@Operation(summary = "Buscar Empresa", description = "Busque o registro da Empresa por código", method = "GET", tags = {
			"Empresa" })
	@Parameter(name = "codigo", example = "8", required = true, description = "Código da Empresa", in = ParameterIn.PATH)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = EmpresaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso da empresa não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/{codigo}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public EntityModel<EmpresaDto> getEmpresa(@PathVariable(name = "codigo") @Positive Long codigo) {

		Link link = linkTo(methodOn(EmpresaController.class).getEmpresa(codigo)).withSelfRel();
		EmpresaDto dto = service.getEmpresa(codigo);
		dto.add(link);

		return EntityModel.of(dto);
	}

	@Operation(summary = "Buscar todas Empresas registadas", description = "Buscar todos os registros da Empresa", method = "GET", tags = {
			"Empresa" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "A busca foi bem-sucedida", content = @Content(schema = @Schema(implementation = EmpresaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso da empresa não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção de Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "")
	public ResponseEntity<CollectionModel<EmpresaDto>> getAllEmpresas() {

		List<EmpresaDto> list = service.getAllEmpresas();
		for (EmpresaDto dto : list) {
			Link link = linkTo(methodOn(EmpresaController.class).getEmpresa(dto.getCodigo())).withSelfRel();
			dto.add(link);
		}
		Link link = linkTo(methodOn(EmpresaController.class).getAllEmpresas()).withSelfRel();
		return ResponseEntity.ok(CollectionModel.of(list, link));
	}

	@Operation(summary = "Buscar Empresa", description = "Obter o registro da Empresa pela designação", method = "GET", tags = {
			"Empresa" })
	@Parameter(name = "empresaDesignacao", example = "AFC", required = true, description = "Empresa Designacao", in = ParameterIn.PATH)
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Busca bem-sucedida", content = @Content(schema = @Schema(implementation = EmpresaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso da Empresa não encontrada", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@GetMapping(path = "/pesquisa/{designacao}")
	public EntityModel<EmpresaDto> getEmpresaByEmpresaDesignacao(@PathVariable(name = "designacao") String designacao) {
		EmpresaDto dto = service.getEmpresaByDesignacao(designacao);
		Link link = linkTo(methodOn(EmpresaController.class).getEmpresaByEmpresaDesignacao(designacao)).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

	// Http post method - Create operation
	@Operation(summary = "Registar Empresa", description = "Registar nova Empresa", method = "POST", tags = {
			"Empresa" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registo efectuado com sucesso!", content = @Content(schema = @Schema(implementation = EmpresaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PostMapping(path = "", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<EmpresaDto> createEmpresa(
			@Parameter(name = "empresaDto", description = "Empresa Dto no corpo do pedido", required = true, content = @Content(schema = @Schema(implementation = EmpresaDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid EmpresaDto empresaDto) {

		EmpresaDto dto = service.createEmpresa(empresaDto);
		Link link = linkTo(methodOn(EmpresaController.class).getEmpresa(dto.getCodigo())).withSelfRel();
		dto.add(link);

		return ResponseEntity.ok(dto);
	}

	// Http put method - Update operation
	@Operation(summary = "Actualizar dados da Empresa", description = "Atualize o registro da Empresa", method = "PUT", tags = {
			"Empresa" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro da empresa actualizado com sucesso", content = @Content(schema = @Schema(implementation = EmpresaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso da empresa não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PutMapping(path = "/empresa", consumes = { MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	// @PreAuthorize("hasAuthority('empresa_update')")
	public EntityModel<EmpresaDto> updateEmpresa(
			@Parameter(name = "empresaDto", description = "Empresa Dto no corpo do pedido", required = true, content = @Content(schema = @Schema(implementation = EmpresaDto.class)), in = ParameterIn.DEFAULT) @RequestBody @Valid EmpresaDto empresaDto) {

		EmpresaDto dto = service.updateEmpresa(empresaDto);

		Link link = linkTo(methodOn(EmpresaController.class).getEmpresa(dto.getCodigo())).withSelfRel();
		dto.add(link);

		return EntityModel.of(dto);
	}

	@Operation(summary = "Activar ou Desactivar uma Empresa", description = "Atualize o staus do registro da Empresa", method = "PATCH", tags = {
			"Empresa" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Status actualizado com sucesso!", content = @Content(schema = @Schema(implementation = EmpresaDto.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "404", description = "Recurso da empresa não encontrado", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),
			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@PatchMapping(path = "/status/{codigo}", produces = { "application/json", "application/xml", "application/x-yaml" })
	// @PreAuthorize("hasAuthority('empresa_update')")
	public void enabled(
			@Parameter(name = "codigo", example = "5", required = true, description = "Código da Empresa", in = ParameterIn.PATH) 
			@PathVariable(name = "codigo") @Positive Long codigo,
			@RequestParam("status") Boolean status) {

		service.enabled(codigo, status);

	}

	// Http delete method - delete operation
	@Operation(summary = "Eliminar dados da Empresa", description = "Exclua o registro da Empresa", method = "DELETE", tags = {
			"Empresa" })
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Registro da empresa eliminado com sucesso", content = @Content()),
			@ApiResponse(responseCode = "404", description = "Recursos da Empresa não encontrados", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)),

			@ApiResponse(responseCode = "500", description = "Exceção do Servidor", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class), mediaType = MediaType.APPLICATION_JSON_VALUE)) })
	@DeleteMapping(path = "/{codigo}")
	public void deleteEmpresa(
			@Parameter(name = "codigo", example = "8", required = true, description = "Código da Empresa", in = ParameterIn.PATH) @PathVariable(name = "codigo") @Positive Long empresaId) {
		service.deleteEmpresa(empresaId);
	}

}