package org.acme;

import data.repository.PersonRepository;
import infra.models.Person;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.UUID;

@Path("/hello")
public class GreetingResource {

    @Inject
    private PersonRepository personRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Person hello() {
        var person = personRepository.findByName("Alex", UUID.fromString("31000000-0000-0000-0000-000000000000"));
        return person;
        //return new Person();
    }
}
