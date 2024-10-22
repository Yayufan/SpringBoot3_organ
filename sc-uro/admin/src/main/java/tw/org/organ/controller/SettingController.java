package tw.org.organ.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.UpdateSettingDTO;
import tw.org.organ.pojo.entity.Setting;
import tw.org.organ.service.SettingService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 系統設定表 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@Tag(name = "系統設定API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/setting")
public class SettingController {

	private final SettingService settingService;
	
	@GetMapping
	@Parameters({
		@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@Operation(summary = "查詢所有系統設置")
	public R<List<Setting>> getAllSetting() {
		 List<Setting> settingList = settingService.getAllSetting();
		return R.ok(settingList);
	}
	
	@Operation(summary = "更新最新消息")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateSetting(@Validated @RequestBody List<UpdateSettingDTO> updateSettingDTOList) {
		settingService.updateSetting(updateSettingDTOList);
		return R.ok();

	}
	
}
