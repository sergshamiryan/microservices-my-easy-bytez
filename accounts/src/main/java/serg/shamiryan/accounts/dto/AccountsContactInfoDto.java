package serg.shamiryan.accounts.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(
        String profile,
        String message,
        Map<String, String> contactDetails,
        List<String> onCallSupport,
        Family family) {

    record Family(Member member) {
        record Member(FullName fullName) {
            record FullName(String name, String surname) {
            }
        }
    }
}