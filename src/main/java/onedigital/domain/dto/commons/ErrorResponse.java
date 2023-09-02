package onedigital.domain.dto.commons;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class ErrorResponse {

    private int statusCode;
    private String message;
    private String details;

    public ErrorResponse(int statusCode, String message, String details) {
        this.statusCode = statusCode;
        this.message = message;
        this.details = details;
    }
}