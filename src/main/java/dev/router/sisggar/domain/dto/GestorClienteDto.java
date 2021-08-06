package dev.router.sisggar.domain.dto;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import dev.router.sisggar.domain.entity.Armazem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;



@Data
@Schema(name = "GestorClienteDto" , description = "Objeto de modelo para recurso do Gestor")
public class GestorClienteDto  extends RepresentationModel<GestorClienteDto> {

	@EqualsAndHashCode.Include
	@Schema(name="codigo", description = "Código representativo do gestor")
	private Long codigo;
	
	@Schema(name="nome", description = "Nome do gestor")
	private String nome;
	
	@Schema(name="telefone", description = "Contacto telefónico do gestor")
	private String telefone;
	
	@Schema(name="status", description = "Status actual do gestor activo|inactivo")
	private Boolean enabled;
	
	@Schema(name="armazem", description = "Armazem de gestão do gestor")
	private Armazem armazem;
}
