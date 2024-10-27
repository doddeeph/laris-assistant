package id.laris.LarisAssistant.web.rest.twilio;

import id.laris.LarisAssistant.service.twilio.TwilioService;
import id.laris.LarisAssistant.service.twilio.dto.WebhookRequestForm;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
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

    @PostMapping(path = "/webhook", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public Mono<String> webhook(
            ServerHttpRequest request,
            @RequestHeader Map<String, String> requestHeader,
            WebhookRequestForm requestForm) {
        return twilioService.webhook(request, requestHeader, requestForm);
    }
}
