package onedigital.domain.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersonRequest {

    private String name;
    private String surName;
    private Instant birthDate;
    private String cpf;
}
