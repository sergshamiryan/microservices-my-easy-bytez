package serg.shamiryan.accounts.mapper;

import serg.shamiryan.accounts.dto.CardsDto;
import serg.shamiryan.accounts.dto.CustomerDetailsDto;
import serg.shamiryan.accounts.dto.CustomerDto;
import serg.shamiryan.accounts.dto.LoansDto;

public class CustomerDetailDtoMapper {

    public static CustomerDetailsDto mapTocustomerDetailsDto(CustomerDto customerDto,
                                                             CardsDto cardsDto,
                                                             LoansDto loansDto) {
        return CustomerDetailsDto.builder()
                .name(customerDto.getName())
                .email(customerDto.getEmail())
                .mobileNumber(customerDto.getMobileNumber())
                .accountsDto(customerDto.getAccountsDto())
                .cardsDto(cardsDto)
                .loansDto(loansDto)
                .build();
    }
}
