package id.laris.LarisAssistant.service.twilio;

import com.twilio.security.RequestValidator;
import id.laris.LarisAssistant.service.openai.OpenAiService;
import id.laris.LarisAssistant.service.twilio.dto.WebhookRequestHeader;
import id.laris.LarisAssistant.service.twilio.dto.WebhookRequestParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Map;

@Log4j2
@Service
public class TwilioService {

    private final OpenAiService openAiService;

    @Value("${twilio.auth-token}")
    private String authToken;

    public TwilioService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public Mono<String> webhook(String requestUrl, Map<String, String> requestHeader, Map<String, String> requestParam) {
        return Mono.defer(() -> {
            WebhookRequestHeader reqHeader = new WebhookRequestHeader(requestHeader);
            WebhookRequestParam reqParam = new WebhookRequestParam(requestParam);
            return Mono.just(isRequestValid(requestUrl, requestParam, reqHeader.getTwilioSignature()))
                    .flatMap(isValid -> isValid ?
                            openAiService.getAssistantResponse(reqParam.getBody()) :
                            Mono.just("[Twilio] Invalid request signature!"));
        });
    }

    private boolean isRequestValid(String url, Map<String, String> params, String twilioSignature) {
        return new RequestValidator(authToken).validate(url, params, twilioSignature);
    }
}
