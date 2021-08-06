package dev.router.sisggar.domain.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import dev.router.sisggar.auditor.Auditable;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Entity
@Table(name="gestor")
@Data
public class GestorCliente extends Auditable<String> implements Serializable{

	
	private static final long serialVersionUID = 1L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(generator = "gestor_seq", strategy = GenerationType.AUTO)
	private Long codigo;
	
	@NotBlank
	@Column(name = "nome", nullable = false, length = 200)
	private String nome;
	
	@NotBlank
	@Size(max = 12)
	@Column(name = "telefone", nullable = false, length = 40,unique = true)
	private String telefone;
	
	@Column(nullable = false)
	private Boolean enabled;
	
	
	@ManyToOne()
	@JoinColumn(name = "codigo_armazem", nullable = false)
	private Armazem armazem;
	
	
	@OneToMany(mappedBy = "gestor")
	@JsonManagedReference
	private List<Cliente> clientes;
}
