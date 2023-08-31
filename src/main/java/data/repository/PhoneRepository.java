package data.repository;

import data.repository.base.BaseRepository;
import infra.models.Person;
import infra.models.Phone;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
@ApplicationScoped
public class PhoneRepository extends BaseRepository<Phone> {
    public List<Phone> getAll(){
        return findAll().list();
    }
}
