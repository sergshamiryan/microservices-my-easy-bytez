package serg.shamiryan.messages.functions;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import serg.shamiryan.messages.dto.AccountsMsgDto;

import java.util.function.Function;

@Log4j2
@Configuration
public class MessageFunctions {

    @Bean
    public Function<AccountsMsgDto, AccountsMsgDto> email() {
        return accountsMsgDto -> {
          log.info("Sending an email message: " + accountsMsgDto.toString());
          return accountsMsgDto;
        };
    }

    @Bean
    public Function<AccountsMsgDto, Long> sms() {
        return accountsMsgDto -> {
            log.info("Sending a sms message");
            return accountsMsgDto.accountNumber();
        };
    }
}
