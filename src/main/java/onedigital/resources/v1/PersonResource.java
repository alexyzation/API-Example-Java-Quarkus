package onedigital.resources.v1;


import jakarta.validation.Valid;
import onedigital.domain.dto.PersonRequest;
import onedigital.domain.dto.PersonResponse;
import onedigital.domain.dto.commons.ErrorResponse;
import onedigital.domain.interfaces.IPersonService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import onedigital.models.Person;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;
import java.util.UUID;


@Path("v1/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Slf4j
public class PersonResource {

    @Inject
    IPersonService personService;

    @GET
    @Operation(summary = "Retrieve all people")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "People's list found", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @APIResponse(responseCode = "404", description = "People's list not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response getAll() {
        log.info("V1 - PersonResource - getAll()");
        List<PersonResponse> list = personService.getAll();
        if (list != null) {
            log.info("V1 - PersonResource - getAll() - Success");
            return Response.ok(list).build();
        }
        log.info("V1 - PersonResource - getAll() - Not Found");
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Retrieve a person by ID")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "People's list found", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @APIResponse(responseCode = "404", description = "Person not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Response getPersonById(@PathParam("id") UUID id) {
        log.info("V1 - PersonResource - getPersonById()");
        PersonResponse person = personService.getById(id);
        if (person != null) {
            log.info("V1 - PersonResource - getPersonById() - Success");
            return Response.ok(person).build();
        } else {
            log.info("V1 - PersonResource - getPersonById() - Not Found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Operation(summary = "Create a new person")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Person created successfully", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @APIResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public Response createPerson(@Valid PersonRequest person) {
        log.info("V1 - PersonResource - createPerson()");
        PersonResponse personResponse = personService.create(person);
        log.info("V1 - PersonResource - createPerson() - Created");
        return Response.status(Response.Status.CREATED).entity(personResponse).build();
    }

    @PUT
    @Path("/{id}")
    @Operation(summary = "Update a person by ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Person updated successfully", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
            @APIResponse(responseCode = "400", description = "Invalid request", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "404", description = "Person not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response updatePerson(@PathParam("id") UUID id, @Valid PersonRequest request) {
        log.info("V1 - PersonResource - updatePerson()");
        boolean updated = personService.update(id, request);
        if (updated) {
            log.info("V1 - PersonResource - updatePerson() - Updated");
            return Response.noContent().build();
        } else {
            log.info("V1 - PersonResource - updatePerson() - Not Found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a person by ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Person deleted successfully", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "404", description = "Person not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response deletePerson(@PathParam("id") UUID id) {
        log.info("V1 - PersonResource - deletePerson()");
        boolean deleted = personService.delete(id);
        if (deleted) {
            log.info("V1 - PersonResource - deletePerson() - Deleted");
            return Response.noContent().build();
        } else {
            log.info("V1 - PersonResource - deletePerson() - Not Found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/cpf-delete/{cpf}")
    @Operation(summary = "Delete a person by cpf")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Person deleted successfully", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "404", description = "Person not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
    })
    public Response deletePersonByCpf(@PathParam("cpf") String cpf) {
        log.info("V1 - PersonResource - deletePersonByCpf()");
        boolean deleted = personService.deleteByCpf(cpf);
        if (deleted) {
            log.info("V1 - PersonResource - deletePersonByCpf() - Deleted");
            return Response.noContent().build();
        } else {
            log.info("V1 - PersonResource - deletePersonByCpf() - Not Found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}