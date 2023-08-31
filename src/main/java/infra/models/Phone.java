package infra.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import infra.models.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "phone")
public class Phone extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1l;

    @ManyToOne
    @JsonBackReference
    public Person person;

    @Column(name = "phonenumber", nullable = true)
    private String phoneNumber;

}
