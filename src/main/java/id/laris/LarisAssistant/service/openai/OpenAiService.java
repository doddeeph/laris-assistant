package id.laris.LarisAssistant.service.openai;

import id.laris.LarisAssistant.service.openai.dto.ChatRequest;
import id.laris.LarisAssistant.service.openai.dto.ChatResponse;
import id.laris.LarisAssistant.service.openai.dto.Message;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Log4j2
@Service
public class OpenAiService {

    private final WebClient webClient;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.base-url}")
    private String baseUrl;

    @Value("${openai.api.uri}")
    private String uri;

    @Value("${openai.api.model}")
    private String model;

    @Value("${openai.api.max-token}")
    private int maxToken;

    @Value("${openai.api.temperature}")
    private double temperature;

    @Value("${openai.api.role-system-message}")
    private String roleSystemMessage;

    public OpenAiService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl(baseUrl).build();
    }

    public Mono<String> getAssistantResponse(String ask) {
        return webClient
                .post()
                .uri(uri)
                .header("Authorization", "Bearer " + apiKey)
                .bodyValue(buildChatRequest(ask))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(s -> Mono.error(new RuntimeException("Client error " + s))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(s -> Mono.error(new RuntimeException("Server error " + s))))
                .bodyToMono(ChatResponse.class)
                .map(chatResponse -> chatResponse.getChoices().get(0).getMessage().getContent())
                .doOnError(WebClientResponseException.class, e ->
                        log.error("Error occurred: {}", e.getResponseBodyAsString()));
    }

    private ChatRequest buildChatRequest(String ask) {
        return ChatRequest
                .builder()
                .model(model)
                .messages(Arrays.asList(
                        Message.builder().role(Role.SYSTEM.name().toLowerCase()).content(roleSystemMessage).build(),
                        Message.builder().role(Role.USER.name().toLowerCase()).content(ask).build()))
                .maxCompletionTokens(maxToken)
                .temperature(temperature)
                .build();
    }
}
