package serg.shamiryan.accounts.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import serg.shamiryan.accounts.dto.CustomerDetailsDto;
import serg.shamiryan.accounts.dto.ErrorResponseDto;
import serg.shamiryan.accounts.service.CustomerService;

@Tag(
        name = "For getting Customer related  information"
)
@RestController
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class CustomerController {

    public final CustomerService customerService;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "HTTP Status OK"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Customer Not Found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))
            )})
    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDto(
            @RequestParam
            @Pattern(regexp = "(^$|[0-9]{10})")
            String mobileNumber) {
        return ResponseEntity.ok(customerService.fetchCustomerDto(mobileNumber));
    }
}
