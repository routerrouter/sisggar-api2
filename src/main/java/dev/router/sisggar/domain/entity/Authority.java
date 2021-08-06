package dev.router.sisggar.domain.entity;



import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity(name = "authority")
public class Authority implements Serializable {
	private static final long serialVersionUID = -3278564974606393621L;

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer codigo;

	private String name;

	@ManyToMany(mappedBy = "authorities")
	private Set<Role> roles;

	public Authority() {
		super();
	}
	
	

	public Authority(String name) {
		super();
		this.name = name;
	}



	public Authority(String name, Set<Role> roles) {
		super();
		this.name = name;
		this.roles = roles;
	}

}
