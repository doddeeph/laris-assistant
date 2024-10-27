package id.laris.LarisAssistant.web.rest.twilio;

import id.laris.LarisAssistant.service.twilio.TwilioService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Log4j2
@RestController
@RequestMapping("/api/twilio")
public class TwilioController {

    private final TwilioService twilioService;

    public TwilioController(TwilioService twilioService) {
        this.twilioService = twilioService;
    }

    @GetMapping("/webhook")
    public Mono<String> webhook(
            ServerHttpRequest request,
            @RequestHeader Map<String, String> requestHeader,
            @RequestParam Map<String, String> requestParam) {
        return Mono.defer(() -> {
            String requestUrl = request.getURI().toString();
            return twilioService.webhook(requestUrl, requestHeader, requestParam);
        });
    }
}
