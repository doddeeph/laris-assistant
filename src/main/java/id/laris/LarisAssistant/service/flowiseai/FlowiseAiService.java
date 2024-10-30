package id.laris.LarisAssistant.service.flowiseai;

import id.laris.LarisAssistant.service.flowiseai.dto.FlowiseAiRequest;
import id.laris.LarisAssistant.service.flowiseai.dto.FlowiseAiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Log4j2
@Service
public class FlowiseAiService {

    private final WebClient webClient;

    @Value("${flowiseai.api.prediction-uri}")
    private String predictionURI;

    public FlowiseAiService(WebClient.Builder builder) {
        this.webClient = builder.baseUrl("http://117.53.144.144:3000").build();
    }

    public Mono<String> prediction(String question) {
        return webClient
                .post()
                .uri(predictionURI)
                .bodyValue(buildFlowiseAiRequest(question))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(s -> Mono.error(new RuntimeException("Client error " + s))))
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        response.bodyToMono(String.class)
                                .flatMap(s -> Mono.error(new RuntimeException("Server error " + s))))
                .bodyToMono(FlowiseAiResponse.class)
                .map(FlowiseAiResponse::getText)
                .doOnError(WebClientResponseException.class, e ->
                        log.error("Error occurred: {}", e.getResponseBodyAsString()));
    }

    private FlowiseAiRequest buildFlowiseAiRequest(String question) {
        return FlowiseAiRequest.builder().question(question).build();
    }
}
