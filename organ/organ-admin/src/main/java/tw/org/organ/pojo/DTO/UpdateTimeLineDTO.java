package tw.org.organ.pojo.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
//忽略其他沒有的屬性，避免objectMapper映射失敗
@JsonIgnoreProperties(ignoreUnknown = true) 
public class UpdateTimeLineDTO {

	@Schema(description = "主鍵ID")
    private Long timeLineId;

    @Schema(description = "ROC民國年，蒐集時要指定105年、106年這樣")
    private Integer year;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "事件日期")
    private LocalDate eventDate;

    @Schema(description = "描述")
    private String description;

    
	
}
