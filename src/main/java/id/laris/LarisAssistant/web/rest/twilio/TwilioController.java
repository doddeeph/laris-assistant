package id.laris.LarisAssistant.web.rest.twilio;

import id.laris.LarisAssistant.service.twilio.TwilioService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.servlet.http.HttpServletRequest;
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
            HttpServletRequest request,
            @RequestHeader Map<String, String> requestHeader,
            @RequestParam Map<String, String> requestParam) {
        return twilioService.webhook(
                request.getRequestURL().toString(),
                requestHeader,
                requestParam);
    }
}
