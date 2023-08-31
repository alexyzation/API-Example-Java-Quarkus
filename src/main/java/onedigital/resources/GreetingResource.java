package onedigital.resources;

import data.repository.PersonRepository;
import data.repository.PhoneRepository;
import infra.models.Person;
import infra.models.Phone;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.UUID;

@Path("/person")
public class GreetingResource {

    @Inject
    private PersonRepository personRepository;
    @Inject
    private PhoneRepository phoneRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Person> getAll() {
        var list = personRepository.getAll();
        return list;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/phones")
    public List<Phone> getphones() {
        var list = phoneRepository.getAll();
        return list;
    }


}
