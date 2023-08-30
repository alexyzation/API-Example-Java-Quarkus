package data.repository.base;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


public class BaseRepository<T> implements PanacheRepository<T> {
}
