package serg.shamiryan.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class AccountsDto {

    @Schema(
            description = "Account number in Shamiryan Bank",
            example = "1234567891"
    )
    @NotEmpty(message = "AccountNumber cannot be empty or null")
    @Pattern(regexp = "(^$|[0-9]{10})",message = "AccountNumber must have 10 digits")
    private Long accountNumber;

    @NotEmpty(message = "AccountType cannot be empty or null")
    private String accountType;

    @NotEmpty(message = "BranchAddress cannot be empty or null")
    private String branchAddress;
}
