package dev.router.sisggar.domain.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "ArmazemDto" , description = "Objeto de modelo para recurso do armazem")
public class ArmazemDto extends RepresentationModel<ArmazemDto>{

	@EqualsAndHashCode.Include
	@Schema(name = "Codigo" , description = "Código do recurso armazem")
	private Long codigo;
	
	
	@NotBlank
	@Schema(name = "Designação" , description = "Designação do armazem")
	private String designacao;
	
	@Schema(name = "Capacidade" , description = "Capacidade de armazenamento de garrafas do recurso de armazem")
	@NotNull
	@PositiveOrZero(message = "Valor de capacidade do armazem inválido")
	private Integer capacidade;
	
	
	private Boolean enabled;


	
	
}
