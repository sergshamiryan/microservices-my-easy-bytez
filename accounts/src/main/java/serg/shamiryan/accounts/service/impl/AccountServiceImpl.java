package serg.shamiryan.accounts.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import serg.shamiryan.accounts.constants.AccountsConstants;
import serg.shamiryan.accounts.dto.AccountsDto;
import serg.shamiryan.accounts.dto.AccountsMsgDto;
import serg.shamiryan.accounts.dto.CustomerDto;
import serg.shamiryan.accounts.entity.Accounts;
import serg.shamiryan.accounts.entity.Customer;
import serg.shamiryan.accounts.exception.CustomerAlreadyExistsException;
import serg.shamiryan.accounts.exception.ResourceNotFoundException;
import serg.shamiryan.accounts.mapper.AccountMapper;
import serg.shamiryan.accounts.mapper.CustomerMapper;
import serg.shamiryan.accounts.repository.AccountsRepository;
import serg.shamiryan.accounts.repository.CustomerRepository;
import serg.shamiryan.accounts.service.AccountService;

import java.util.Random;

@Service
@Log4j2
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final CustomerRepository customerRepository;
    private final AccountsRepository accountsRepository;
    private final StreamBridge streamBridge;

    @Override
    @Transactional
    public Customer createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        if (customerRepository.existsByMobileNumber(customerDto.getMobileNumber())) {
            throw new CustomerAlreadyExistsException("Exists");
        }
        Customer savedCustomer = customerRepository.save(customer);
        Accounts savedAccount = accountsRepository.save(this.createNewAccount(savedCustomer));
        this.sendCommunication(savedAccount, savedCustomer);
        return customer;
    }

    private void sendCommunication(Accounts accounts, Customer customer) {
        var accountMessageDto = new AccountsMsgDto(accounts.getAccountNumber(), customer.getName(),
                customer.getEmail(), customer.getMobileNumber());
        log.info("Sending Communication request for the details: {}", accountMessageDto);
        /*By "sendCommunication-out-0" binding name it knows to which exchange forward the message*/
        boolean result = streamBridge.send("sendCommunication-out-0", accountMessageDto);
        log.info("Is the communication request successfully processed? : {}", result);
    }

    private Accounts createNewAccount(Customer customer) {
        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }

    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Customer",
                        "mobileNumber",
                        mobileNumber));
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Accounts",
                        "customerId",
                        customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    @Transactional
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account",
                            "AccountNumber",
                            accountsDto.getAccountNumber().toString())
            );
            AccountMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer",
                            "CustomerID",
                            customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }
}
