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
        return findAll().list();
    }

    public Person getById(UUID id){
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("id", id);
        return find(builder.getQuery(),builder.getParams()).firstResult();
    }

    public List<Person> getAllEnabled(){
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("enabled", true);
        return find(builder.getQuery(),builder.getParams()).list();
    }

    public void save(Person person){
        persist(person);
    }

    public Integer updatePersonEntity(Person person, UUID id){
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("id", id);
        builder
                .addUpdateParam("cpf", person.getCpf())
                .addUpdateParam("birthDate", person.getBirthDate())
                .addUpdateParam("name", person.getName())
                .addUpdateParam("surName", person.getSurName());
        ;
        return update(builder.getQuery(),builder.getParams());
    }

    public void deletePersonEntity(Person person){
        delete(person);
    }

    public Long deleteEntityByCpf(String cpf){
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("cpf", cpf);

        return delete(builder.getQuery(),builder.getParams());
    }
}