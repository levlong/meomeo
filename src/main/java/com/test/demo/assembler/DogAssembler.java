package com.test.demo.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.test.demo.controller.DogController;
import com.test.demo.entity.Dog;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class DogAssembler implements RepresentationModelAssembler<Dog, EntityModel<Dog>> {
    
    @Override
    public EntityModel<Dog> toModel(Dog dog) {
        return EntityModel.of(dog,
          linkTo(methodOn(DogController.class).one(dog.getId())).withSelfRel(),
          linkTo(methodOn(DogController.class).all()).withRel("dogs")
        );
    }
}
