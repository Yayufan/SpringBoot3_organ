package tw.org.organ.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.InsertFileDTO;
import tw.org.organ.pojo.entity.Article;
import tw.org.organ.service.FileService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 上傳檔案表，用於應用在只有上傳檔案的頁面 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
@Tag(name = "上傳至檔案中心API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/file")
public class FileController {

	private final FileService fileService;

	@GetMapping("{group}")
	@Operation(summary = "查詢某個組別所有文章")
	public R<List<Article>> getAllFileByGroup(@PathVariable("group") String group) {
		return R.ok();
	}
	
	@GetMapping("{group}/pagination")
	@Operation(summary = "查詢某個組別所有檔案(分頁)")
	public R<IPage<Article>> getAllFileByGroup(@PathVariable("group") String group, @RequestParam Integer page,
			@RequestParam Integer size) {
		Page<Article> pageInfo = new Page<>(page, size);
		IPage<Article> articleList = fileService.getAllFileByGroup(group, pageInfo);
		return R.ok(articleList);
	}
	

	@PostMapping
	@Operation(summary = "新增檔案至某個類別")
	public R<Void> addFile(@RequestParam("file") MultipartFile[] file, @RequestParam("data") String jsonData)
			throws JsonMappingException, JsonProcessingException {
		// 將 JSON 字符串轉為對象
		ObjectMapper objectMapper = new ObjectMapper();
		InsertFileDTO insertFileDTO = objectMapper.readValue(jsonData, InsertFileDTO.class);

		//將檔案和資料對象傳給後端
		fileService.addFile(file, insertFileDTO);
		
		return R.ok();
	}

	@PostMapping("{id}")
	@Operation(summary = "根據ID來刪除檔案")
	public R<Void> deleteFile(@PathVariable("id") Long fileId) {
		System.out.println("檔案刪除");
		return R.ok();
	}

}
