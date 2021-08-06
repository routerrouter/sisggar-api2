package dev.router.sisggar.domain.dto;

import org.springframework.hateoas.RepresentationModel;

import dev.router.sisggar.domain.entity.Armazem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name="LocalidadeDto", description = "Localidades de entregas e recolhas dos clientes")
public class LocalidadeDto extends RepresentationModel<LocalidadeDto>{

	@EqualsAndHashCode.Include
	@Schema(name = "Codigo" , description = "Código da localidade")
	private Long codigo;
	
	@Schema(name="descrição da localidade", description = "Nome do endereço fisico do cliente")
	private String descricao;
	
	@Schema(name="Código do armazem", description="Armazem de distribuição das localidades")
	private Armazem armazem;
	
	@Schema(name="enabled", description="Estado da localidade -> activo ou desactivado")
	private boolean enabled;
}
