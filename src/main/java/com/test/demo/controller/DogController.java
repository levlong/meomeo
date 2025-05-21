package com.test.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

import com.test.demo.assembler.DogAssembler;
import com.test.demo.entity.Dog;
import com.test.demo.exception.DogNotFoundException;
import com.test.demo.repository.DogRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class DogController {

    private final DogRepository repository;
    private final DogAssembler assembler;

    @GetMapping("/dogs")
    public CollectionModel<EntityModel<Dog>> all() {
        List<EntityModel<Dog>> dogs = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(dogs,
                linkTo(methodOn(DogController.class).all()).withSelfRel());
    }

    @GetMapping("/dogs/{id}")
    public EntityModel<Dog> one(@PathVariable Long id) {
        Dog dog = repository.findById(id).orElseThrow(() -> new DogNotFoundException(id));

        return assembler.toModel(dog);
    }

    @PostMapping("/dogs")
    public ResponseEntity<?> add(@RequestBody Dog dog) {
        EntityModel<Dog> entity = assembler.toModel(repository.save(dog));

        return ResponseEntity.created(entity.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entity);
    }

    @PutMapping("/dogs/{id}")
    public ResponseEntity<?> update(@RequestBody Dog newDog, @PathVariable Long id) {
        Dog dog = repository.findById(id)
                .map(d -> {
                    d.setName(newDog.getName());
                    d.setBreed(newDog.getBreed());
                    d.setFur_color(newDog.getFur_color());
                    d.setAge(newDog.getAge());
                    return repository.save(d);
                }).orElseGet(() -> {
                    return repository.save(newDog);
                });

        EntityModel<Dog> dogEntity = assembler.toModel(dog);

        return ResponseEntity.created(dogEntity.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(dogEntity);
    }

    @DeleteMapping("/dogs/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
