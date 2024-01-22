package serg.shamiryan.accounts.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import serg.shamiryan.accounts.dto.CardsDto;

/* It knows where to call because of the name specified
 * in Eureka server by application name
 * , If it sees many instances Feign is going to use spring
 * cloud load balancer and after performing load balancing
 * strategy, it will call one of the service instances
 * */
@FeignClient(name = "cards", fallback = CardsFallback.class)
public interface CardsFeignClient {

    @GetMapping(value = "/api/fetch", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CardsDto> fetchCardDetails(@RequestParam String mobileNumber);
}
