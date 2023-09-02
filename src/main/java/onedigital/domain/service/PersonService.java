package onedigital.domain.service;

import jakarta.transaction.Transactional;
import onedigital.data.repository.PersonRepository;
import onedigital.domain.dto.PersonRequest;
import onedigital.domain.dto.PersonResponse;
import onedigital.domain.interfaces.IPersonService;
import onedigital.domain.mapper.PersonMapper;
import onedigital.models.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class PersonService implements IPersonService {

    @Inject
    PersonRepository personRepository;

    @Inject
    PersonMapper mapper;

    @Override
    public List<PersonResponse> getAll() {
        log.info("PersonService - getAll()");
        List<Person> listEntity = personRepository.getAll();
        List<PersonResponse> list = mapper.toResponse(listEntity);
        log.info("PersonService - getAll() - Returning {} records", list.size());
        return list;
    }

    @Override
    public PersonResponse getById(UUID id) {
        log.info("PersonService - getById() - ID: {}", id);
        Person entity = personRepository.getById(id);
        if (entity != null) {
            log.info("PersonService - getById() - Found Person with ID: {}", id);
            PersonResponse personResponse = mapper.toResponse(entity);
            return personResponse;
        } else {
            log.info("PersonService - getById() - Person not found with ID: {}", id);
            return null;
        }
    }

    @Override
    @Transactional
    public PersonResponse create(PersonRequest request) {
        log.info("PersonService - create()");
        Person person = mapper.toEntity(request);
        person.setEnabled(true);
        person.setId(UUID.randomUUID());
        personRepository.save(person);
        log.info("PersonService - create() - Created Person with ID: {}", person.getId());
        PersonResponse response = mapper.toResponse(person);
        return response;
    }

    @Override
    @Transactional
    public boolean update(UUID id, PersonRequest request) {
        log.info("PersonService - update() - ID: {}", id);
        Person existingPerson = personRepository.getById(id);
        if (existingPerson != null) {
            existingPerson = mapper.toEntity(request);
            var ret = personRepository.updatePersonEntity(existingPerson, id);
            if (ret == 1) {
                log.info("PersonService - update() - Updated Person with ID: {}", id);
                return true;
            }
        }
        log.info("PersonService - update() - Person not found with ID: {}", id);
        return false;
    }

    @Override
    @Transactional
    public boolean delete(UUID id) {
        log.info("PersonService - delete() - ID: {}", id);
        Person existingPerson = personRepository.getById(id);
        if (existingPerson != null) {
            personRepository.deletePersonEntity(existingPerson);
            log.info("PersonService - delete() - Deleted Person with ID: {}", id);
            return true;
        }
        log.info("PersonService - delete() - Person not found with ID: {}", id);
        return false;
    }

    @Override
    @Transactional
    public boolean deleteByCpf(String cpf) {
        log.info("PersonService - deleteByCpf() - CPF: {}", cpf);
        var delete = personRepository.deleteEntityByCpf(cpf);
        if (delete == 1L) {
            log.info("PersonService - deleteByCpf() - Deleted Person with CPF: {}", cpf);
            return true;
        }
        log.info("PersonService - deleteByCpf() - Person not found with CPF: {}", cpf);
        return false;
    }
}