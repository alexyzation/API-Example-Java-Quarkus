package data.utils;

import io.quarkus.panache.common.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryBuilder {

    private final Map<String, Object> params = new HashMap<>();
    public QueryBuilder addParam(String paramName, Object paramValue) {
        params.put(paramName, paramValue);
        return this;
    }

    public String getQuery() {
        StringBuilder query = new StringBuilder();

        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (query.length() > 0) {
                query.append(" AND ");
            }
            query.append(entry.getKey()).append(" = :").append(entry.getKey());
        }

        return query.toString();
    }

    public Parameters getParams() {
        Parameters parameters = new Parameters();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            parameters.and(entry.getKey(), entry.getValue());
        }
        return parameters;
    }
}