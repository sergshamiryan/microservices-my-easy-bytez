package serg.shamiryan.accounts.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import serg.shamiryan.accounts.audit.AuditAwareImpl;
import serg.shamiryan.accounts.entity.Accounts;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import(AuditAwareImpl.class)
class AccountsRepositoryTest {

    @Autowired
    private AccountsRepository accountsRepository;

    @Test
    public void givenCustomerId_whenFindByCustomerId_thenReturnCustomerObject() {
        //given - precondition or setup
        Accounts accounts = new Accounts();
        accounts.setCustomerId(1L);
        accounts.setAccountType("Type");
        accounts.setAccountNumber(1234567891L);
        accounts.setBranchAddress("Address");
        accounts.setCreatedAt(LocalDateTime.now());
        accounts.setCreatedBy("Admin");
        accounts.setUpdatedAt(null);
        accounts.setUpdatedBy(null);
        accountsRepository.save(accounts);
        //when - action or behaviour we are going to test
        Accounts accountsDb = accountsRepository.findByCustomerId(1L)
                .orElseThrow();
        //then - verify the output
        assertThat(accountsDb).isNotNull();
    }
}