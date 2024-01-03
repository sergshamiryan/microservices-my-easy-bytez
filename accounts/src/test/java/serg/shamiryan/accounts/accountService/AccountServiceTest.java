package serg.shamiryan.accounts.accountService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import serg.shamiryan.accounts.accountService.impl.AccountServiceImpl;
import serg.shamiryan.accounts.dto.CustomerDto;
import serg.shamiryan.accounts.entity.Accounts;
import serg.shamiryan.accounts.entity.Customer;
import serg.shamiryan.accounts.mapper.CustomerMapper;
import serg.shamiryan.accounts.repository.AccountsRepository;
import serg.shamiryan.accounts.repository.CustomerRepository;

import java.util.Optional;
import java.util.Random;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountsRepository accountsRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    public void givenCustomerObject_whenSaveCustomer_thenReturnSavedCustomer() {
        //given - precondition or setup
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber("1234567891");
        customerDto.setName("Test Name");
        customerDto.setEmail("test@gmail.com");
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        given(customerRepository.save(any(Customer.class))).willReturn(customer);
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        account.setAccountNumber(1000000000L + new Random().nextInt(900000000));
        account.setAccountType("Some type");
        account.setBranchAddress("Address");
        given(accountsRepository.save(any(Accounts.class))).willReturn(account);
        //when - action or behaviour we are going to test
        Customer customerDb = accountService.createAccount(customerDto);
        //then - verify the output
        assertThat(customerDb).isNotNull();
    }

    @Test
    public void givenMobileNumber_whenFetchAccount_thenReturnAccount() {
        //given - precondition or setup
        String mobileNumber = "1234567891";
        CustomerDto customerDto = new CustomerDto();
        customerDto.setMobileNumber("1234567891");
        customerDto.setName("Test Name");
        customerDto.setEmail("test@gmail.com");
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        account.setAccountNumber(1000000000L + new Random().nextInt(900000000));
        account.setAccountType("Some type");
        account.setBranchAddress("Address");
        BDDMockito.given(customerRepository.findByMobileNumber(mobileNumber))
                .willReturn(Optional.of(customer));
        BDDMockito.given(accountsRepository.findByCustomerId(customer.getCustomerId()))
                .willReturn(Optional.of(account));
        //when - action or behaviour we are going to test
        CustomerDto fetchedCustomerDto = accountService.fetchAccount(mobileNumber);
        //then - verify the output
        assertThat(fetchedCustomerDto.getEmail()).isNotNull();
    }
}