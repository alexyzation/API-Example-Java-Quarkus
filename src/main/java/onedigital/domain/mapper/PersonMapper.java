package onedigital.domain.mapper;

import onedigital.domain.dto.PersonRequest;
import onedigital.domain.dto.PersonResponse;
import onedigital.models.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

//Se usar Javax tem q colocar @Mapper(componentModel = "cdi")
@Mapper(componentModel = "jakarta", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface PersonMapper {
    @Mapping(target = "name", source = "request.name")
    @Mapping(target = "surName", source = "request.surName")
    @Mapping(target = "birthDate", source = "request.birthDate")
    @Mapping(target = "cpf", source = "request.cpf")
    @Mapping(target = "enabled", ignore = true)  // Ignorando a propriedade "enabled"
    @Mapping(target = "phones", ignore = true)   // Ignorando a propriedade "phones"
    Person toEntity(PersonRequest request);

    @Mapping(target = "name", source = "entity.name")
    @Mapping(target = "surName", source = "entity.surName")
    @Mapping(target = "birthDate", source = "entity.birthDate")
    @Mapping(target = "cpf", source = "entity.cpf")
    PersonResponse toResponse(Person entity);

    List<PersonResponse> toResponse(List<Person> employees);
}