package tw.org.organ.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.InsertTimeLineAttachmentDTO;
import tw.org.organ.pojo.entity.TimeLineAttachment;
import tw.org.organ.service.TimeLineAttachmentService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 協會年表時間線附件 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2025-03-17
 */
@Tag(name = "時間線事件附件API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/time-line-attachment")
public class TimeLineAttachmentController {

	private final TimeLineAttachmentService timeLineAttachmentService;

	@GetMapping("{timeLineId}")
	@Operation(summary = "根據時間線事件ID，查詢時間線事件所有附件")
	public R<List<TimeLineAttachment>> getAllTimeLineAttachment(@PathVariable("timeLineId") Long timeLineId) {
		List<TimeLineAttachment> timeLineAttachmentList = timeLineAttachmentService
				.getAllTimeLineAttachmentByTimeLineId(timeLineId);
		return R.ok(timeLineAttachmentList);
	}

	@PostMapping
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER),
			@Parameter(name = "data", description = "JSON 格式的附件資料", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = InsertTimeLineAttachmentDTO.class)) })
	@SaCheckLogin
	@Operation(summary = "為某個時間線事件新增附件")
	public R<Void> addTimeLineAttachment(@RequestParam("file") MultipartFile[] files,
			@RequestParam("data") String jsonData) throws JsonMappingException, JsonProcessingException {
		// 將 JSON 字符串轉為對象
		ObjectMapper objectMapper = new ObjectMapper();
		InsertTimeLineAttachmentDTO insertTimeLineAttachmentDTO = objectMapper.readValue(jsonData,
				InsertTimeLineAttachmentDTO.class);

		// 將檔案和資料對象傳給後端
		timeLineAttachmentService.insertTimeLineAttachment(insertTimeLineAttachmentDTO, files);

		return R.ok();
	}

	@DeleteMapping("{id}")
	@SaCheckLogin
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@Operation(summary = "根據 timeLineAttachment ID來刪除時間線事件附件")
	public R<Void> deleteFile(@PathVariable("id") Long timeLineAttachmentId) {
		timeLineAttachmentService.deleteTimeLineAttachment(timeLineAttachmentId);
		return R.ok();
	}

	
}
