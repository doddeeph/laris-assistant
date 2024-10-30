package id.laris.LarisAssistant.service.twilio;

import id.laris.LarisAssistant.service.flowiseai.FlowiseAiService;
import id.laris.LarisAssistant.service.twilio.dto.WebhookRequestHeader;
import id.laris.LarisAssistant.service.twilio.dto.WebhookRequestForm;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Log4j2
@Service
public class TwilioService {

    private final FlowiseAiService flowiseAiService;
//    private final OpenAiService openAiService;

    @Value("${twilio.auth-token}")
    private String authToken;

    public TwilioService(FlowiseAiService flowiseAiService) {
//        this.openAiService = openAiService;
        this.flowiseAiService = flowiseAiService;
    }

    public Mono<String> webhook(ServerHttpRequest request, Map<String, String> requestHeader, WebhookRequestForm reqForm) {
        return Mono.defer(() -> {
            WebhookRequestHeader reqHeader = new WebhookRequestHeader(requestHeader);
            log.info("{}", reqHeader.toString());
            log.info("{}", reqForm.toString());
            return Mono.just(isRequestValid(request.getURI().toString(), null, reqHeader.getTwilioSignature()))
                    .flatMap(isValid -> isValid ?
                            flowiseAiService.prediction(reqForm.getBody()) :
//                            openAiService.getAssistantResponse(reqForm.getBody()) :
                            Mono.just("[Twilio] Invalid request signature!"));
        });
    }

    private boolean isRequestValid(String url, Map<String, String> params, String twilioSignature) {
        return true;
        //return new RequestValidator(authToken).validate(url, params, twilioSignature);
    }
}
