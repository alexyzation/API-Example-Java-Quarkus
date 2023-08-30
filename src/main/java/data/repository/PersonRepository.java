package data.repository;

import data.repository.base.BaseRepository;
import data.utils.QueryBuilder;
import infra.models.Person;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class PersonRepository extends BaseRepository<Person> {
    public Person findByName(String name, UUID id){
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
                .addParam("name",name)
                .addParam("id", id);

        var query = queryBuilder.getQuery();
        var param = queryBuilder.getParams();

        return find(query, param).firstResult();
    }
}