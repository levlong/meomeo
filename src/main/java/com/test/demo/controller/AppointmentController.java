package com.test.demo.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.test.demo.assembler.AppointmentAssembler;
import com.test.demo.entity.Appointment;
import com.test.demo.exception.AppointmentNotFoundException;
import com.test.demo.repository.AppointmentRepository;

import lombok.AllArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@AllArgsConstructor
public class AppointmentController {
    private final AppointmentRepository repository;
    private final AppointmentAssembler assembler;

    @GetMapping("/appointments")
    public CollectionModel<EntityModel<Appointment>> all() {
        List<EntityModel<Appointment>> appointments = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
                
        return CollectionModel.of(appointments,
                linkTo(methodOn(AppointmentController.class).all()).withSelfRel());
    }

    @GetMapping("/appointments/{id}")
    public EntityModel<Appointment> one(@PathVariable Long id) {
        Appointment appointment = repository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id));
        return assembler.toModel(appointment);
    }

    @PostMapping("/appointments")
    public ResponseEntity<?> add(@RequestBody Appointment newAppointment) {
        EntityModel<Appointment> appointmentEntityModel = assembler.toModel(repository.save(newAppointment));
        return ResponseEntity.created(appointmentEntityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(appointmentEntityModel);
    }

    @PutMapping("/appointments/{id}")
    public ResponseEntity<?> update(@RequestBody Appointment updateAppointment, @PathVariable Long id) {
        Appointment appointment = repository.findById(id)
                .map(a -> {
                    a.setReason(updateAppointment.getReason());
                    a.setStatus(updateAppointment.getStatus());
                    return repository.save(a);
                }).orElseGet(() -> {
                    return repository.save(updateAppointment);
                });

        EntityModel<Appointment> entity = assembler.toModel(appointment);
        return ResponseEntity.created(entity.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entity);
    }

    @DeleteMapping("/appointments/{id}")
    public ResponseEntity<Appointment> delete(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
