package tw.org.organ.pojo.VO;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TimeLineFrontVO {

	@Schema(description = "民國年")
	private Integer year;
	
	@Schema(description = "該年事件的列表")
	private List<TimeLineVO> timeLineVO;
	
	
}
