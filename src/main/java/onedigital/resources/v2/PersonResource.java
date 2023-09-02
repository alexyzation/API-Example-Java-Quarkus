//package onedigital.resources.v2;
//
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.Consumes;
//import jakarta.ws.rs.GET;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import lombok.extern.slf4j.Slf4j;
//import onedigital.domain.dto.PersonResponse;
//import onedigital.domain.dto.commons.ErrorResponse;
//import onedigital.domain.interfaces.IPersonService;
//import org.eclipse.microprofile.openapi.annotations.Operation;
//import org.eclipse.microprofile.openapi.annotations.media.Content;
//import org.eclipse.microprofile.openapi.annotations.media.Schema;
//import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
//import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
//
//import java.util.List;
//
//@Path("v2/person")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//@ApplicationScoped
//@Slf4j
//public class PersonResource {
//    @Inject
//    IPersonService personService;
//
//    @GET
//    @Operation(summary = "Retrieve all people")
//    @APIResponses({
//            @APIResponse(responseCode = "200", description = "People's list found", content = @Content(schema = @Schema(implementation = PersonResponse.class))),
//            @APIResponse(responseCode = "404", description = "People's list not found", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//            @APIResponse(responseCode = "500", description = "Internal server error", content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
//    })
//    public Response getAll() {
//        log.info("V1 - PersonResource - getAll()");
//        List<PersonResponse> list = personService.getAll();
//        if (list != null) {
//            log.info("V1 - PersonResource - getAll() - Success");
//            return Response.ok(list).build();
//        }
//        log.info("V1 - PersonResource - getAll() - Not Found");
//        return Response.status(Response.Status.NOT_FOUND).build();
//    }
//}
