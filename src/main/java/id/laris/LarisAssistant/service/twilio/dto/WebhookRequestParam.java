package id.laris.LarisAssistant.service.twilio.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.Map;
import java.util.Optional;

@Log4j2
@Data
@NoArgsConstructor
public class WebhookRequestParam {

    private Map<String, String> requestParam;
    private String apiVersion;
    private String smsSid;
    private String smsStatus;
    private String smsMessageSid;
    private String profileName;
    private Integer numSegments;
    private String from;
    private String waId;
    private String messageSid;
    private String accountSid;
    private String buttonText;
    private Integer referralNumMedia;
    private String to;
    private String body;
    private String messageType;
    private String buttonPayload;
    private Integer numMedia;

    public WebhookRequestParam(Map<String, String> requestParam) {
        requestParam.forEach((k, v) -> log.debug("req param {}: {}", k, v));
        this.requestParam = requestParam;
        this.apiVersion = requestParam.getOrDefault("ApiVersion", "");
        this.smsSid = requestParam.getOrDefault("SmsSid", "");
        this.smsStatus = requestParam.getOrDefault("SmsStatus", "");
        this.smsMessageSid = requestParam.getOrDefault("SmsMessageSid", "");
        this.profileName = requestParam.getOrDefault("ProfileName", "");
        this.numSegments = Optional.ofNullable(requestParam.get("NumSegments")).map(Integer::parseInt).orElse(null);
        this.from = requestParam.getOrDefault("From", "");
        this.waId = requestParam.getOrDefault("WaId", "");
        this.messageSid = requestParam.getOrDefault("MessageSid", "");
        this.accountSid = requestParam.getOrDefault("AccountSid", "");
        this.buttonText = requestParam.getOrDefault("ButtonText", "");
        this.referralNumMedia = Optional.ofNullable(requestParam.get("ReferralNumMedia")).map(Integer::parseInt).orElse(null);
        this.to = requestParam.getOrDefault("To", "");
        this.body = requestParam.getOrDefault("Body", "");
        this.messageType = requestParam.getOrDefault("MessageType", "");
        this.buttonPayload = requestParam.getOrDefault("ButtonPayload", "");
        this.numMedia = Optional.ofNullable(requestParam.get("NumMedia")).map(Integer::parseInt).orElse(null);
    }
}
