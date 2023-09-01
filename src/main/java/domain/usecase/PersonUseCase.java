package domain.usecase;

import infra.models.Person;

import java.util.List;
import java.util.UUID;

public interface PersonUseCase {

    List<Person> getAllPersons();

    Person getPersonById(UUID id);

    Person createPerson(Person person);

    boolean updatePerson(UUID id, Person person);

    boolean deletePerson(UUID id);

    boolean deletePersonByCpf(String cpf);

}