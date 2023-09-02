package onedigital.data.utils;

import io.quarkus.panache.common.Parameters;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class QueryBuilder {

    private final List<Params> params = new ArrayList<>();
    private final List<Query> queries = new ArrayList<>();
    private final List<Query> updateQueries = new ArrayList<>();
    private Integer iteration = 0;

    public QueryBuilder addParam(String paramName, Object paramValue) {
        var logicalOperator = LogicalOperators.AND.value;

        var param = newParams(paramName,paramValue);
        params.add(param);

        var query = newQuery(paramName, ComparisonOperators.EQUALS.value, logicalOperator);
        queries.add(query);

        iteration++;
        return this;
    }
    public QueryBuilder addParam(String paramName, Object paramValue, ComparisonOperators comparisonOperator) {
        var logicalOperator = LogicalOperators.AND.value;

        var param = newParams(paramName,paramValue);
        params.add(param);

        var query = newQuery(paramName, comparisonOperator.value, logicalOperator);
        queries.add(query);

        iteration++;
        return this;
    }
    public QueryBuilder addParam(String paramName, Object paramValue, LogicalOperators logicalOperator) {
        var param = newParams(paramName,paramValue);
        params.add(param);

        var query = newQuery(paramName, ComparisonOperators.EQUALS.value, logicalOperator.value);
        queries.add(query);

        iteration++;
        return this;
    }
    public QueryBuilder addParam(String paramName, Object paramValue, ComparisonOperators comparisonOperator, LogicalOperators logicalOperator) {
        var param = newParams(paramName,paramValue);
        params.add(param);

        var query = newQuery(paramName, comparisonOperator.value, logicalOperator.value);
        queries.add(query);

        iteration++;
        return this;
    }
    public QueryBuilder addNullParam(String paramName) {
        var query = newQuery(paramName, SpecialOperations.IS_NULL.value, LogicalOperators.AND.value);
        queries.add(query);
        return this;
    }
    public QueryBuilder addNullParam(String paramName, LogicalOperators logicalOperator) {
        var query = newQuery(paramName, SpecialOperations.IS_NULL.value, logicalOperator.value);
        queries.add(query);
        return this;
    }
    public QueryBuilder addNotNullParam(String paramName) {
        var query = newQuery(paramName, SpecialOperations.NOT_NULL.value, LogicalOperators.AND.value);
        queries.add(query);
        return this;
    }
    public QueryBuilder addNotNullParam(String paramName, LogicalOperators logicalOperator) {
        var query = newQuery(paramName, SpecialOperations.NOT_NULL.value, logicalOperator.value);
        queries.add(query);
        return this;
    }
    public QueryBuilder addLikeParam(String paramName, Object paramValue, TypeLike typeLike) {
        var logicalOperator = LogicalOperators.AND.value;

        var paramFinal = paramValue.toString();
        switch (typeLike){
            case BOTH -> paramFinal = '%'+paramFinal+'%';
            case END ->  paramFinal = paramFinal+'%';
            case INIT -> paramFinal = '%'+paramFinal;
        }
        var param = newParams(paramName,paramFinal);
        params.add(param);

        var query = newQuery(paramName, SpecialOperations.LIKE.value, logicalOperator);
        queries.add(query);

        iteration++;
        return this;
    }
    public QueryBuilder addLikeParam(String paramName, Object paramValue, TypeLike typeLike, LogicalOperators logicalOperator) {
        var paramFinal = paramValue.toString();
        switch (typeLike){
            case BOTH -> paramFinal = '%'+paramFinal+'%';
            case END ->  paramFinal = paramFinal+'%';
            case INIT -> paramFinal = '%'+paramFinal;
        }
        var param = newParams(paramName,paramFinal);
        params.add(param);

        var query = newQuery(paramName, SpecialOperations.LIKE.value, logicalOperator.value);
        queries.add(query);

        iteration++;
        return this;
    }

    public QueryBuilder addUpdateParam(String paramName, Object paramValue){
        var logicalOperator = LogicalOperators.AND.value;

        var param = newParams(paramName,paramValue);
        params.add(param);

        var query = newQuery(paramName, ComparisonOperators.EQUALS.value, logicalOperator);
        updateQueries.add(query);

        iteration++;
        return this;
    }

    public String formatQuery(String key, String comparator, Object value){
        if(comparator.toLowerCase()==SpecialOperations.IS_NULL.value || comparator.toLowerCase()==SpecialOperations.NOT_NULL.value){
            return  String.format("%s %s", key, comparator);
        }
        return String.format("%s %s :%s", key, comparator, value);
    }
    public Query newQuery(String paramName, String comparionOperator, String logicalOperator){
        var query = new Query();
        query.setQuery(formatQuery(paramName, comparionOperator, paramName+iteration));
        query.setComparisonOperator(comparionOperator);
        query.setLogicalOperator(logicalOperator);
        return query;
    }
    public Params newParams(String paramName, Object paramValue){
        Params param = new Params();
        param.setKey(paramName);
        param.setValue(paramValue);
        param.setKeyUnique(paramName+iteration);
        return param;
    }
    public String getQuery() {
        StringBuilder finalString = new StringBuilder();

        if(!updateQueries.isEmpty()){
            var loop = 0;
            for (Query query: updateQueries) {
                if(loop==0){
                    query.setLogicalOperator("");
                    finalString.append(query.getQuery());
                }else{
                    finalString
                            .append(" ")
                            .append(",")
                            .append(" ")
                            .append(query.getQuery());
                }
                loop++;
            }
            finalString.append(" WHERE ");
        }

        var loop = 0;
        for (Query query: queries) {
            if(loop==0){
                query.setLogicalOperator("");
            }
            finalString
                    .append(" ")
                    .append(query.getLogicalOperator())
                    .append(" ")
                    .append(query.getQuery());
            loop++;
        }

        return finalString.toString();
    }
    public Parameters getParams() {
        Parameters parameters = new Parameters();
        for (Params param: params) {
            parameters.and(param.keyUnique, param.value);
        }
        return parameters;
    }
    public Integer getIteration() {
        return iteration;
    }
    @Getter
    @AllArgsConstructor
    public enum ComparisonOperators{
        //Comparsion
        EQUALS("="),
        GREATER_THAN(">"),
        LESS_THAN("<"),
        GREATER_THEN_EQUALS(">="),
        LESS_THAN_EQUALS("<="),
        NOT_EQUALS("<>"),
        ;
        public String value;
    }
    @Getter
    @AllArgsConstructor
    public enum SpecialOperations{
        //Special comparsion
        IS_NULL("is null"),
        NOT_NULL("is not null"),
        //Logical comparsion
        LIKE("LIKE"),
        NOT_LIKE("NOT LIKE"),

        ;
        public String value;
    }
    @Getter
    @AllArgsConstructor
    public enum LogicalOperators{
        AND("AND"),
        OR("OR"),
        ;
        public String value;
    }
    @Getter
    @AllArgsConstructor
    public enum TypeLike{
        INIT("INIT"),
        END("END"),
        BOTH("BOTH"),
        ;
        public String value;
    }
    @Getter
    @Setter
    public class Query{
        public String query;
        public String logicalOperator;
        public String comparisonOperator;
    }
    @Getter
    @Setter
    public class Params{
        public String key;
        public Object value;
        public String keyUnique;
    }
}