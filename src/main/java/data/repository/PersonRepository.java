package data.repository;

import data.repository.base.BaseRepository;
import data.utils.QueryBuilder;
import infra.models.Person;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.persistence.Column;

import java.util.UUID;

@ApplicationScoped
public class PersonRepository extends BaseRepository<Person> {

    private QueryBuilder.ComparisonOperators compOp;
    private QueryBuilder.LogicalOperators logicalOp;
    public Person findByName(String name, UUID id){
        QueryBuilder queryBuilder = new QueryBuilder();
        queryBuilder
//                .addParam("name", name)
//                .addParam("name", name, compOp.NOT_EQUALS)
//                .addParam("name", name, logicalOp.OR)
                //.addNotNullParam("name")
                //.addNotNullParam("id")
                .addLikeParam("name", name, QueryBuilder.TypeLike.BOTH)
                .addLikeParam("name", name, QueryBuilder.TypeLike.INIT, logicalOp.OR)
        ;

        var query = queryBuilder.getQuery();
        var param = queryBuilder.getParams();
        var iteration = queryBuilder.getIteration();
        return find(query, param).firstResult();
    }
}