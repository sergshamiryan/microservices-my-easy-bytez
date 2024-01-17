package serg.shamiryan.accounts.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import serg.shamiryan.accounts.dto.CustomerDetailsDto;
import serg.shamiryan.accounts.dto.ErrorResponseDto;
import serg.shamiryan.accounts.service.CustomerService;

@Tag(
        name = "For getting Customer related  information"
)
@Log4j2
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
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
            @RequestHeader("shamiryanbank-correlation-id") String correlationId,
            @RequestParam @Pattern(regexp = "(^$|[0-9]{10})") String mobileNumber) {
        log.debug("shamiryanbank-correlation-id foung {}: ",correlationId);
        return ResponseEntity.ok(customerService.fetchCustomerDto(mobileNumber,correlationId));
    }
}
