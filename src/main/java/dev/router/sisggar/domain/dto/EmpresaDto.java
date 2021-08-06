package dev.router.sisggar.domain.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.springframework.hateoas.RepresentationModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Schema(name = "EmpresaDto" , description = "Objeto de modelo para recurso da empresa")
public class EmpresaDto extends RepresentationModel<EmpresaDto>{

	@EqualsAndHashCode.Include
	@Schema(name = "Codigo" , description = "Empresa Code of Empresa Resource")
	private Long codigo;
	
	@NotBlank
	@Schema(name = "Descrição" , description = "Empresa Description of Empresa Resource")
	private String designacao;
	
	@NotBlank
	@Schema(name = "Endereço" , description = "Empresa Address of Empresa Resource")
	private String endereco;
	
	@NotBlank
	@Schema(name = "Telefone" , description = "Empresa Telephone of Empresa Resource")
	private String telefone;
	
	@Email
	@Schema(name = "Email" , description = "Empresa Email of Empresa Resource")
	private String email;
	
	@NotBlank
	@Schema(name = "Nif" , description = "Empresa Nif of Empresa Resource")
	private String nif;
	
	@Schema(name = "Website" , description = "Empresa Website of Empresa Resource")
	private String website;
	

	@Schema(name = "Logotipo" , description = "Empresa image of Empresa Resource")
	private String logotipo_url;

	@Schema(name = "Estado" , description = "Empresa Status of Empresa Resource")
	private Boolean enabled;
}
