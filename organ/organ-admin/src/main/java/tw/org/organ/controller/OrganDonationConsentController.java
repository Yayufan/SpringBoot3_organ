package tw.org.organ.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.OrganDonationConsentConvert;
import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.DTO.UpdateOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;
import tw.org.organ.pojo.excelPojo.OrganDonationConsentExcel;
import tw.org.organ.service.OrganDonationConsentService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
@Tag(name = "器捐同意書API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/organ-donation-consent")
public class OrganDonationConsentController {

	private final OrganDonationConsentService organDonationConsentService;
	private final OrganDonationConsentConvert organDonationConsentConvert;
	

	@GetMapping("{id}")
	@Operation(summary = "查詢單一器捐同意書")
	public R<OrganDonationConsent> getOrganDonationConsent(@PathVariable("id") Long organDonationConsentId) {
		OrganDonationConsent organDonationConsent = organDonationConsentService
				.getOrganDonationConsent(organDonationConsentId);
		return R.ok(organDonationConsent);
	}

	@GetMapping
	@Operation(summary = "查詢所有器捐同意書")
	public R<List<OrganDonationConsent>> getAllOrganDonationConsent() {

		List<OrganDonationConsent> organDonationConsentList = organDonationConsentService.getAllOrganDonationConsent();
		return R.ok(organDonationConsentList);
	}

	@GetMapping("pagination")
	@SaCheckLogin
	@Operation(summary = "查詢所有器捐同意書(分頁)")
	public R<IPage<OrganDonationConsent>> getAllOrganDonationConsent(@RequestParam Integer page,
			@RequestParam Integer size) {
		Page<OrganDonationConsent> pageInfo = new Page<>(page, size);
		IPage<OrganDonationConsent> organDonationConsentList = organDonationConsentService
				.getAllOrganDonationConsent(pageInfo);

		return R.ok(organDonationConsentList);
	}

	@GetMapping("pagination-by-status")
	@SaCheckLogin
	@Operation(summary = "根據器捐同意書狀態,查詢符合的所有器捐同意書(分頁)")
	public R<IPage<OrganDonationConsent>> getAllOrganDonationConsentByQuery(@RequestParam Integer page,
			@RequestParam Integer size, @RequestParam(required = false) String status,
			@RequestParam(required = false) String queryText) {
		Page<OrganDonationConsent> pageInfo = new Page<>(page, size);

		IPage<OrganDonationConsent> organDonationConsentList;

		organDonationConsentList = organDonationConsentService.getAllOrganDonationConsentByStatus(pageInfo, status,
				queryText);

		return R.ok(organDonationConsentList);
	}

	@GetMapping("count")
	@Operation(summary = "查詢器捐同意書總數")
	public R<Long> getOrganDonationConsentCount() {
		Long organDonationConsentCount = organDonationConsentService.getOrganDonationConsentCount();
		return R.ok(organDonationConsentCount);
	}

	@GetMapping("count-by-status")
	@Operation(summary = "根據審核狀態,查詢相符的器捐同意書總數")
	public R<Long> getOrganDonationConsentCountByStatus(String status) {
		Long organDonationConsentCount = organDonationConsentService.getOrganDonationConsentCount(status);
		return R.ok(organDonationConsentCount);
	}

	/**
	 * 新增器捐同意書
	 * 
	 * @param insertOrganDonationConsentDTO
	 * @return
	 */
	@PostMapping
	@Operation(summary = "線上填寫器捐同意書")
	public R<InsertOrganDonationConsentDTO> saveOrganDonationConsent(
			@Validated @RequestBody InsertOrganDonationConsentDTO insertOrganDonationConsentDTO) {

		organDonationConsentService.insertOrganDonationConsent(insertOrganDonationConsentDTO);
		return R.ok();
	}

	@Operation(summary = "更新同意書")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateOrganDonationConsent(
			@Validated @RequestBody UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO) {
		organDonationConsentService.updateOrganDonationConsent(updateOrganDonationConsentDTO);
		return R.ok();

	}

	@Operation(summary = "批量更新同意書")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping("batch")
	public R<Void> batchUpdateOrganDonationConsent(
			@Validated @RequestBody @NotEmpty List<UpdateOrganDonationConsentDTO> updateOrganDonationConsentDTOList) {
		organDonationConsentService.updateOrganDonationConsent(updateOrganDonationConsentDTOList);
		return R.ok();

	}

	@Operation(summary = "刪除同意書")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping("{id}")
	public R<Void> deleteOrganDonationConsent(@PathVariable("id") Long organDonationConsentId) {
		organDonationConsentService.deleteOrganDonationConsent(organDonationConsentId);
		return R.ok();
	}

	@Operation(summary = "批量刪除同意書")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping()
	public R<Void> batchDeleteOrganDonationConsent(@Valid @NotNull @RequestBody List<Long> organDonationConsentIdList) {
		organDonationConsentService.deleteOrganDonationConsent(organDonationConsentIdList);
		return R.ok();
	}
	

	@Operation(summary = "下載同意書excel列表")
	@SaCheckLogin
    @GetMapping("/download-excel")
    public void downloadExcel(HttpServletResponse response) throws IOException {
		  // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        
        List<OrganDonationConsent> allOrganDonationConsent = organDonationConsentService.getAllOrganDonationConsent();
        List<OrganDonationConsentExcel> excelData = allOrganDonationConsent.stream().map(organDonationConsent -> {
        	return organDonationConsentConvert.entityToExcel(organDonationConsent);
        }).collect(Collectors.toList());
        
        System.out.println(excelData);
        
        EasyExcel.write(response.getOutputStream(), OrganDonationConsentExcel.class).sheet("模板").doWrite(excelData);
    }
	
	
	

}
