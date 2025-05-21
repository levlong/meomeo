package com.test.demo.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.test.demo.controller.AppointmentController;
import com.test.demo.entity.Appointment;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class AppointmentAssembler implements RepresentationModelAssembler<Appointment, EntityModel<Appointment>>{

    @Override
    public EntityModel<Appointment> toModel(Appointment appointment) {
        return EntityModel.of(appointment,
            linkTo(methodOn(AppointmentController.class).one(appointment.getId())).withSelfRel(),
            linkTo(methodOn(AppointmentController.class).all()).withRel("appointments")
        );
    }
    
}
