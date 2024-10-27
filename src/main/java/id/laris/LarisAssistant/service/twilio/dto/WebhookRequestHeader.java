package id.laris.LarisAssistant.service.twilio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;

@Log4j2
@Data
@NoArgsConstructor
public class WebhookRequestHeader {

    private String twilioSignature;

    public WebhookRequestHeader(Map<String, String> requestHeader) {
        requestHeader.forEach((k, v) -> log.debug("req header {}: {}", k, v));
        this.twilioSignature = requestHeader.getOrDefault("X-Twilio-Signature", "");
    }
}
