package onedigital.resources;

import com.sun.net.httpserver.Authenticator;
import data.repository.PersonRepository;
import data.repository.PhoneRepository;
import domain.usecase.PersonUseCase;
import infra.models.Person;
import infra.models.Phone;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;
import java.util.UUID;

@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Slf4j
public class PersonResource {

    @Inject
    PersonUseCase personUseCase;

    @GET
    @Operation(summary = "Retrieve all people")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Persons found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getAllPersons() {
        List<Person> persons = personUseCase.getAllPersons();
        return Response.ok(persons).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a person by ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Person found"),
            @APIResponse(responseCode = "404", description = "Person not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getPersonById(@PathParam("id") UUID id) {
        Person person = personUseCase.getPersonById(id);
        if (person != null) {
            return Response.ok(person).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Operation(summary = "Create a new person")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Person created successfully"),
            @APIResponse(responseCode = "400", description = "Invalid request"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response createPerson(@Valid Person person) {
        Person createdPerson = personUseCase.createPerson(person);
        return Response.status(Response.Status.CREATED).entity(createdPerson).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a person by ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Person updated successfully"),
            @APIResponse(responseCode = "400", description = "Invalid request"),
            @APIResponse(responseCode = "404", description = "Person not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response updatePerson(@PathParam("id") UUID id, @Valid Person person) {
        boolean updated = personUseCase.updatePerson(id, person);
        if (updated) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a person by ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Person deleted successfully"),
            @APIResponse(responseCode = "404", description = "Person not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response deletePerson(@PathParam("id") UUID id) {
        boolean deleted = personUseCase.deletePerson(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/cpf-delete/{cpf}")
    @Operation(summary = "Delete a person by cpf")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Person deleted successfully"),
            @APIResponse(responseCode = "404", description = "Person not found"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    public Response deletePersonByCpf(@PathParam("cpf") String cpf) {
        boolean deleted = personUseCase.deletePersonByCpf(cpf);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}