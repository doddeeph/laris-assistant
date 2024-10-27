package id.laris.LarisAssistant.service.openai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRequest {

    private String model;
    private List<Message> messages;

    @JsonProperty("max_completion_tokens")
    private int maxCompletionTokens;

    private double temperature;
}
