package serg.shamiryan.accounts.service.client;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import serg.shamiryan.accounts.dto.CardsDto;
import serg.shamiryan.accounts.dto.LoansDto;

@Component
public class CardsFallback implements CardsFeignClient{
    
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String mobileNumber) {
        return null;
    }
}
