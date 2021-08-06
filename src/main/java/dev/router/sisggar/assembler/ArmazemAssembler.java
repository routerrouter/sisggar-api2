package dev.router.sisggar.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.SimpleRepresentationModelAssembler;
import org.springframework.stereotype.Component;

import dev.router.sisggar.controller.ArmazemController;
import dev.router.sisggar.domain.entity.Armazem;

@Component
public class ArmazemAssembler implements SimpleRepresentationModelAssembler<Armazem>{
	
	@Autowired
	private ApplicationEventPublisher publisher;

	@Override
	   public void addLinks(EntityModel<Armazem> resource) {
	       Long codigo_armazem = resource.getContent().getCodigo();
	       resource.add(linkTo(methodOn(ArmazemController.class).buscarArmazemPeloCodigo(codigo_armazem)).withSelfRel());
	       resource.add(linkTo(methodOn(ArmazemController.class).listarTodos()).withRel("armazens"));
	   }
	 
	   @Override
	   public void addLinks(CollectionModel<EntityModel<Armazem>> resources) {
	       resources.add(linkTo(methodOn(ArmazemController.class).listarTodos()).withSelfRel());
	      
	   }
}
