package serg.shamiryan.accounts.service;

import serg.shamiryan.accounts.dto.CustomerDetailsDto;

public interface CustomerService {

    CustomerDetailsDto fetchCustomerDto(String mobileNumber);
}
