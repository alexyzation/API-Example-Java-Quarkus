package onedigital.data.repository;

import onedigital.data.repository.base.BaseRepository;
import onedigital.data.utils.QueryBuilder;
import onedigital.models.Person;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
@Slf4j
public class PersonRepository extends BaseRepository<Person> {

    public List<Person> getAll(){
        log.info("PersonRepository - getAll()");
        return findAll().list();
    }

    public Person getById(UUID id){
        log.info("PersonRepository - getById() - ID: {}", id);
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("id", id);
        return find(builder.getQuery(),builder.getParams()).firstResult();
    }

    public List<Person> getAllEnabled(){
        log.info("PersonRepository - getAllEnabled()");
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("enabled", true);
        return find(builder.getQuery(),builder.getParams()).list();
    }

    public void save(Person person){
        log.info("PersonRepository - save() - Saving Person with ID: {}", person.getId());
        persist(person);
    }

    public Integer updatePersonEntity(Person person, UUID id){
        log.info("PersonRepository - updatePersonEntity() - ID: {}", id);
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("id", id);
        builder
                .addUpdateParam("cpf", person.getCpf())
                .addUpdateParam("birthDate", person.getBirthDate())
                .addUpdateParam("name", person.getName())
                .addUpdateParam("surName", person.getSurName());
        ;
        int result = update(builder.getQuery(),builder.getParams());
        if (result > 0) {
            log.info("PersonRepository - updatePersonEntity() - Updated Person with ID: {}", id);
        } else {
            log.info("PersonRepository - updatePersonEntity() - Person not found with ID: {}", id);
        }
        return result;
    }

    public void deletePersonEntity(Person person){
        log.info("PersonRepository - deletePersonEntity() - Deleting Person with ID: {}", person.getId());
        delete(person);
    }

    public Long deleteEntityByCpf(String cpf){
        log.info("PersonRepository - deleteEntityByCpf() - Deleting Person with CPF: {}", cpf);
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("cpf", cpf);

        long result = delete(builder.getQuery(),builder.getParams());
        if (result > 0) {
            log.info("PersonRepository - deleteEntityByCpf() - Deleted Person with CPF: {}", cpf);
        } else {
            log.info("PersonRepository - deleteEntityByCpf() - Person not found with CPF: {}", cpf);
        }
        return result;
    }
}