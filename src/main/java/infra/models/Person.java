package infra.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import infra.models.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "people")
public class Person extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1l;

    @Column(name = "name", nullable = true)
    private String name;
    @Column(name = "surname", nullable = true)
    private String surName;
    @Column(name = "birthdate", nullable = true)
    private Instant birthDate;
    @Column(name = "enabled", nullable = true)
    private Boolean enabled;
    @Column(name = "cpf", nullable = true)
    private String cpf;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    @JsonManagedReference
    private List<Phone> phones;

}
