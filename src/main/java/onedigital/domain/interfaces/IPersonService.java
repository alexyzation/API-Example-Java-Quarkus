package onedigital.domain.interfaces;

import onedigital.domain.dto.PersonRequest;
import onedigital.domain.dto.PersonResponse;
import onedigital.models.Person;

import java.util.List;
import java.util.UUID;

public interface IPersonService {

    List<PersonResponse> getAll();

    PersonResponse getById(UUID id);

    PersonResponse create(PersonRequest person);

    boolean update(UUID id, PersonRequest person);

    boolean delete(UUID id);

    boolean deleteByCpf(String cpf);

}