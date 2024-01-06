package serg.shamiryan.accounts.service;

import serg.shamiryan.accounts.dto.CustomerDto;
import serg.shamiryan.accounts.entity.Customer;

public interface AccountService {

    Customer createAccount(CustomerDto customerDto);

    CustomerDto fetchAccount(String mobileNumber);

    boolean updateAccount(CustomerDto customerDto);

    boolean deleteAccount(String mobileNumber);
}
