package serg.shamiryan.accounts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResponseDto {

    @Schema(
            description = "Response Status Code"
    )
    private String responseCode;

    @Schema(
            description = "Response message"
    )
    private String message;
}
