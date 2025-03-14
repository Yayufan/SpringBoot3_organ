package tw.org.organ.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InsertBoardMemberDTO {
	
	@Schema(description = "第幾屆（數字，方便查詢和排序）")
    private Integer termNumber;

    @Schema(description = "第幾屆（中文，用於展示）")
    private String termNumberCn;

    @Schema(description = "任職起始日期，直接寫民國年")
    private String startDate;

    @Schema(description = "任職結束日期，直接寫民國年")
    private String endDate;

    @Schema(description = "理事長，僅1位")
    private String chairperson;

    @Schema(description = "常務理事，多位，請用全形頓號分隔")
    private String executiveDirectors;

    @Schema(description = "理事，多位，請用全形頓號分隔")
    private String directors;

    @Schema(description = "常務監事，僅1位")
    private String executiveSupervisor;

    @Schema(description = "監事，多位，請用全形頓號分隔")
    private String supervisors;
}
