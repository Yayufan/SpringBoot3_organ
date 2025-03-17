package tw.org.organ.pojo.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class InsertTimeLineAttachmentDTO {
	
	@Schema(description = "協會年表ID")
	private Long timeLineId;

	@Schema(description = "minio 檔案儲存路徑")
	private String path;


}
