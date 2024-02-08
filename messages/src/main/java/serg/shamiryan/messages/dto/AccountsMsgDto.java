package serg.shamiryan.messages.dto;

public record AccountsMsgDto(Long accountNumber,
                             String name,
                             String email,
                             String mobileNumber) {
}
