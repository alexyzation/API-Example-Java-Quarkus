package data.repository;

import data.repository.base.BaseRepository;
import data.utils.QueryBuilder;
import infra.models.Person;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.Column;
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

    public int update(Person person){
        return update(person);
    }

    public void deleteEntity(Person person){
        delete(person);
    }

    public Long deleteEntityByCpf(String cpf){
        QueryBuilder builder = new QueryBuilder();
        builder.addParam("cpf", cpf);

        return delete(builder.getQuery(),builder.getParams());
    }
}