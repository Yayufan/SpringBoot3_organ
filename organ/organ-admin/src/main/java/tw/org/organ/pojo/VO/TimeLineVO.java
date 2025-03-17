package tw.org.organ.pojo.VO;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import tw.org.organ.pojo.entity.TimeLineAttachment;

@Data
public class TimeLineVO {
	
    @Schema(description = "主鍵ID")
    private Long timeLineId;

    @Schema(description = "ROC民國年，蒐集時要指定105年、106年這樣")
    private Integer year;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "事件日期")
    private LocalDate eventDate;

    @Schema(description = "描述")
    private String description;
    
    @Schema(description = "附件圖片")
    private List<TimeLineAttachment> timeLineAttachment;
}
