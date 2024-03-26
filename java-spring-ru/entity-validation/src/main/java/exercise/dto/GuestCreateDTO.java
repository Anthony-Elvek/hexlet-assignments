package exercise.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

// BEGIN
@Getter
@Setter
public class GuestCreateDTO {

    @NotBlank
    private String name;

    @Column(unique = true)
    @Email
    private String email;

    @Column(unique = true)
    @Pattern(regexp = "\\+[0-9]{11,13}")
    private String phoneNumber;

    @Column(unique = true)
    @Pattern(regexp = "[0-9]{4}")
    private String clubCard;

    @Future
    private LocalDate cardValidUntil;
}
// END
