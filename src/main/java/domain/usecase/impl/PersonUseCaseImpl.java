package domain.usecase.impl;


import data.repository.PersonRepository;
import domain.usecase.PersonUseCase;
import infra.models.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class PersonUseCaseImpl implements PersonUseCase {

    @Inject
    private PersonRepository personRepository;

    @Override
    public List<Person> getAllPersons() {
        return personRepository.getAll();
    }

    @Override
    public Person getPersonById(UUID id) {
        return personRepository.getById(id);
    }

    @Override
    @Transactional
    public Person createPerson(Person person) {
        personRepository.save(person);
        return person;
    }

    @Override
    @Transactional
    public boolean updatePerson(UUID id, Person person) {
        Person existingPerson = getPersonById(id);
        if (existingPerson != null) {
            person.setId(existingPerson.getId());
            personRepository.update(person);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deletePerson(UUID id) {
        Person existingPerson = getPersonById(id);
        if (existingPerson != null) {
            personRepository.deleteEntity(existingPerson);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deletePersonByCpf(String cpf) {
        var delete = personRepository.deleteEntityByCpf(cpf);
        if(delete==1L){
            return true;
        }
        return false;
    }
}
