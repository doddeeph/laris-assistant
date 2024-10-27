package id.laris.LarisAssistant.service.twilio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WebhookRequestForm {

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

    @Override
    public String toString() {
        return "WebhookRequestForm{" +
                "apiVersion='" + apiVersion + '\'' +
                ", smsSid='" + smsSid + '\'' +
                ", smsStatus='" + smsStatus + '\'' +
                ", smsMessageSid='" + smsMessageSid + '\'' +
                ", profileName='" + profileName + '\'' +
                ", numSegments=" + numSegments +
                ", from='" + from + '\'' +
                ", waId='" + waId + '\'' +
                ", messageSid='" + messageSid + '\'' +
                ", accountSid='" + accountSid + '\'' +
                ", buttonText='" + buttonText + '\'' +
                ", referralNumMedia=" + referralNumMedia +
                ", to='" + to + '\'' +
                ", body='" + body + '\'' +
                ", messageType='" + messageType + '\'' +
                ", buttonPayload='" + buttonPayload + '\'' +
                ", numMedia=" + numMedia +
                '}';
    }
}
