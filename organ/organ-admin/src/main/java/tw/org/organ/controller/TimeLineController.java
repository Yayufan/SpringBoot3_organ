package tw.org.organ.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.InsertTimeLineDTO;
import tw.org.organ.pojo.DTO.UpdateTimeLineDTO;
import tw.org.organ.pojo.VO.TimeLineFrontVO;
import tw.org.organ.pojo.VO.TimeLineVO;
import tw.org.organ.pojo.entity.TimeLine;
import tw.org.organ.service.TimeLineService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 年表-時間線 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */

@Tag(name = "協會年表時間線API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/time-line")
public class TimeLineController {

	private final TimeLineService timeLineService;

	@GetMapping("tree")
	@Operation(summary = "查詢所有協會年表時間線事件-依年份分組(樹狀結構)")
	public R<List<TimeLineFrontVO>> getAllTimeLineTree() {
		  List<TimeLineFrontVO> allTimeLineFrontVO = timeLineService.getAllTimeLineFrontVO();
		return R.ok(allTimeLineFrontVO);
	}
	
	@GetMapping("{id}")
	@Operation(summary = "查詢單一屆協會年表時間線事件")
	public R<TimeLineVO> getTimeLine(@PathVariable("id") Long timeLineId) {
		TimeLineVO timeLineVO = timeLineService.getTimeLine(timeLineId);
		return R.ok(timeLineVO);
	}

	@GetMapping
	@Operation(summary = "查詢所有協會年表時間線事件")
	public R<List<TimeLineVO>> getAllTimeLine() {
		 List<TimeLineVO> timeLineVOList = timeLineService.getAllTimeLine();
		return R.ok(timeLineVOList);
	}

	@GetMapping("pagination")
	@Operation(summary = "查詢所有協會年表時間線事件(分頁)")
	public R<IPage<TimeLineVO>> getAllArticle(@RequestParam Integer page, @RequestParam Integer size) {
		Page<TimeLine> pageInfo = new Page<>(page, size);
		IPage<TimeLineVO> timeLineVOPage = timeLineService.getAllTimeLine(pageInfo);

		return R.ok(timeLineVOPage);
	}

	@Operation(summary = "新增協會年表時間線事件")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER),
			@Parameter(name = "data", description = "JSON 格式的檔案資料", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = InsertTimeLineDTO.class)) })
	@SaCheckLogin
	@PostMapping
	public R<Long> saveTimeLine(@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam("data") String jsonData) throws JsonMappingException, JsonProcessingException {

		// 將 JSON 字符串轉為對象
		ObjectMapper objectMapper = new ObjectMapper();
		// 這邊objectMapper 註冊 JavaTimeModule，是為了轉換Java 8 的LocalDate 、 LocalDateTime
		objectMapper.registerModule(new JavaTimeModule());
		InsertTimeLineDTO insertTimeLineDTO = objectMapper.readValue(jsonData, InsertTimeLineDTO.class);
		Long timeLineId = timeLineService.insertTimeLine(files, insertTimeLineDTO);
		return R.ok(timeLineId);

	}

	@Operation(summary = "更新協會年表時間線事件")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER),
			@Parameter(name = "data", description = "JSON 格式的檔案資料", required = true, in = ParameterIn.QUERY, schema = @Schema(implementation = UpdateTimeLineDTO.class)) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateTimeLine(@RequestParam(value = "file", required = false) MultipartFile[] files,
			@RequestParam("data") String jsonData) throws JsonMappingException, JsonProcessingException {
		// 將 JSON 字符串轉為對象
		ObjectMapper objectMapper = new ObjectMapper();
		// 這邊objectMapper 註冊 JavaTimeModule，是為了轉換Java 8 的LocalDate 、 LocalDateTime
		objectMapper.registerModule(new JavaTimeModule());
		UpdateTimeLineDTO updateTimeLineDTO = objectMapper.readValue(jsonData, UpdateTimeLineDTO.class);
		
		
		timeLineService.updateTimeLine(files, updateTimeLineDTO);
		return R.ok();

	}

	@Operation(summary = "刪除單一協會年表時間線事件")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping("{id}")
	public R<Void> deleteTimeLine(@PathVariable("id") Long timeLineId) {
		timeLineService.deleteTimeLine(timeLineId);
		return R.ok();

	}

}
