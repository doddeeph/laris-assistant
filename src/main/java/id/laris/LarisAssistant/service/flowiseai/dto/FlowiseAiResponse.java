package id.laris.LarisAssistant.service.flowiseai.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowiseAiResponse {

    private String text;
    private List<UsedTool> usedTools;
    private String question;
    private String chatId;
    private String chatMessageId;
    private Boolean isStreamValid;
    private String sessionId;
    private String memoryType;

}
