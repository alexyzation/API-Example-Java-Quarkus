package onedigital.data.repository;

import onedigital.data.repository.base.BaseRepository;
import onedigital.models.Phone;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;
@ApplicationScoped
public class PhoneRepository extends BaseRepository<Phone> {
    public List<Phone> getAll(){
        return findAll().list();
    }
}
