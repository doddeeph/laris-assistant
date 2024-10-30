package id.laris.LarisAssistant.service.flowiseai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsedTool {

    private String tool;
    private ToolInput toolInput;
//    private String toolOutput;
}
