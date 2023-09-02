package onedigital.domain.service;


import jakarta.transaction.Transactional;
import onedigital.data.repository.PersonRepository;
import onedigital.domain.dto.PersonRequest;
import onedigital.domain.dto.PersonResponse;
import onedigital.domain.interfaces.IPersonService;
//import onedigital.domain.mapper.PersonMapper;
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
        List<Person> listEntity = personRepository.getAll();
        List<PersonResponse> list = mapper.toResponse(listEntity);
        //PersonResponse list = mapper.toResponse(listEntity.get(0));
        return list;
    }

    @Override
    public PersonResponse getById(UUID id) {
        Person entity = personRepository.getById(id);
        PersonResponse personResponse = mapper.toResponse(entity);
        return personResponse;
    }

    @Override
    @Transactional
    public PersonResponse create(PersonRequest request) {
        Person person = mapper.toEntity(request);
        person.setEnabled(true);
        person.setId(UUID.randomUUID());
        personRepository.save(person);
        PersonResponse response = mapper.toResponse(person);
        return response;
    }

    @Override
    @Transactional
    public boolean update(UUID id, PersonRequest request) {
        Person existingPerson = personRepository.getById(id);
        if (existingPerson != null) {
            existingPerson = mapper.toEntity(request);
            var ret = personRepository.updatePersonEntity(existingPerson, id);
            if(ret==1){
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional
    public boolean delete(UUID id) {
        Person existingPerson = personRepository.getById(id);
        if (existingPerson != null) {
            personRepository.deletePersonEntity(existingPerson);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean deleteByCpf(String cpf) {
        var delete = personRepository.deleteEntityByCpf(cpf);
        if(delete==1L){
            return true;
        }
        return false;
    }
}
