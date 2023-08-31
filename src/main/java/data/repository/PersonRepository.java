package data.repository;

import data.repository.base.BaseRepository;
import data.utils.QueryBuilder;
import infra.models.Person;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.Column;

import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class PersonRepository extends BaseRepository<Person> {

    public List<Person> getAll(){
        return findAll().list();
    }

    public Integer updateName(String name, UUID id){
        QueryBuilder builder = new QueryBuilder();
        builder
                .addLikeParam("name", name, QueryBuilder.TypeLike.BOTH)
        ;
        builder
                .addUpdateParam("name","Alexe")
        ;

        var query = builder.getQuery();
        var param = builder.getParams();
        var iteration = builder.getIteration();
        return update(query, param);
    }
}