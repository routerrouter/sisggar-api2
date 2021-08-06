package dev.router.sisggar.domain.dto;

import org.springframework.hateoas.RepresentationModel;

import dev.router.sisggar.domain.entity.GestorCliente;
import dev.router.sisggar.domain.entity.Localidade;
import dev.router.sisggar.domain.entity.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Data
@Schema(name = "ClienteDto" , description = "Objeto de modelo para recurso do armazem")
public class ClienteDto extends RepresentationModel<ClienteDto> {
	
	@EqualsAndHashCode.Include
	@Schema(name = "Codigo" , description = "Código do cliente")
	private Long codigo;
	
	@Schema(name="nome", description = "Nome do cliente")
	private String nome;
	
	@Schema(name="telefone", description = "Telefone de contacto do cliente")
	private String telefone;
	
	@Schema(name="email", description="Email de contacto do cliente")
	private String email;
	
	@Schema(name="endereco", description="Endereço fisico do cliente")
	private String endereco;
	
	@Schema(name="nif", description = "Número de contribuente do cliente")
	private String nif;
	
	@Schema(name="localidade", description = "Localidade fisica de entregas do cliente")
	private Localidade localidade;
	
	@Schema(name="enabled", description = "Status do cliente")
	private boolean enabled;
	
	@Schema(name="posse", description="Posse de garrafas do cliente")
	private Integer posse;
	
	@Schema(name="usuario", description="Usuário que o registou no sistema")
	private Usuario usuario;
	
	@Schema(name="gestor", description="Gestor responsável pelo cliente")
	private GestorCliente gestor;

}
