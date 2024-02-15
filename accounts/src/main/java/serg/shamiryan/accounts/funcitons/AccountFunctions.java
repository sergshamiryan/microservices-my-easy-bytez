package serg.shamiryan.accounts.funcitons;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;
import serg.shamiryan.accounts.entity.Accounts;
import serg.shamiryan.accounts.repository.AccountsRepository;

import java.util.function.Consumer;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class AccountFunctions {

    @Bean
    @Transactional
    Consumer<Long> updateCommunication(AccountsRepository accountsRepository) {
        return accNumber -> {
            if (accNumber != null) {
                Accounts account = accountsRepository.findByAccountNumber(accNumber)
                        .orElseThrow(() -> new NotFoundException("Not Found BY Number"));
                account.setCommunicationSw(true);
                log.info("Updating communication number in db for {}", accNumber);
                Accounts accountDb = accountsRepository.save(account);
                System.out.println(accountDb.getCommunicationSw());
            }
        };
    }
}
