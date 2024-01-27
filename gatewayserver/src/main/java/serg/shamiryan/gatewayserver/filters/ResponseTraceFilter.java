package serg.shamiryan.gatewayserver.filters;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Mono;

import static serg.shamiryan.gatewayserver.filters.FilterUtility.CORRELATION_ID;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class ResponseTraceFilter {

    private final FilterUtility filterUtility;

    @Bean
    public GlobalFilter postGlobalFilter() {
        return (exchange, chain) ->
                /*By line bellow and then() method the response will be executed,
                 * after the request sent to microservice and the response is received*/
                chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    HttpHeaders requestHeaders = exchange.getRequest().getHeaders();
                    String correlationId = filterUtility.getCorrelationId(requestHeaders);
                    if (!exchange.getResponse().getHeaders().containsKey(CORRELATION_ID)) {
                        log.debug(
                                "Updated correlation id to the outbound headers: {}",
                                correlationId);
                        exchange.getResponse().getHeaders().add(CORRELATION_ID, correlationId);
                    }
                }));
    }
}
