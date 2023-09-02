package onedigital.domain.dto;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PersonResponse {
    private String name;
    private String surName;
    private Instant birthDate;
    private String cpf;
}
