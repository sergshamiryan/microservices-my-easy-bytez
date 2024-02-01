package serg.shamiryan.gatewayserver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @RequestMapping("/contactSupport")
    public ResponseEntity<String> contactSupport() {
        return ResponseEntity.ok("An error occurred.Contact Support Team");
    }
}
