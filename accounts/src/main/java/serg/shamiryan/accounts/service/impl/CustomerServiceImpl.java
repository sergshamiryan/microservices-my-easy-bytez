package serg.shamiryan.accounts.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import serg.shamiryan.accounts.dto.CustomerDetailsDto;
import serg.shamiryan.accounts.dto.CustomerDto;
import serg.shamiryan.accounts.mapper.CustomerDetailDtoMapper;
import serg.shamiryan.accounts.service.AccountService;
import serg.shamiryan.accounts.service.CustomerService;
import serg.shamiryan.accounts.service.client.CardsFeignClient;
import serg.shamiryan.accounts.service.client.LoansFeignClient;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final AccountService accountService;
    private final LoansFeignClient loansFeignClient;
    private final CardsFeignClient cardsFeignClient;

    @Override
    public CustomerDetailsDto fetchCustomerDto(String mobileNumber) {
        CustomerDto customerDto = accountService.fetchAccount(mobileNumber);
        return CustomerDetailDtoMapper.mapTocustomerDetailsDto(
                customerDto,
                cardsFeignClient.fetchCardDetails(mobileNumber).getBody(),
                loansFeignClient.fetchLoanDetails(mobileNumber).getBody());
    }
}
