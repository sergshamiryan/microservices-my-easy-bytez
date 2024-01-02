package serg.shamiryan.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "Customer",
        description = "Schema to Hold Customer And Account information"
)
public class CustomerDto {

    @Schema(
            description = "Name of the customer",
            example = "Armen Vardanyan"
    )
    @NotEmpty(message = "Name cannot be null and empty")
    @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
    private String name;

    @Schema(
            description = "Customer email",
            example = "armenVardanyan@gmail.com"
    )
    @NotEmpty(message = "Email cannot be null and empty")
    @Email(message = "Email address should be a valid email")
    private String email;

    @Pattern(regexp = "(^$|[0-9]{10})")
    private String mobileNumber;

    private AccountsDto accountsDto;
}
