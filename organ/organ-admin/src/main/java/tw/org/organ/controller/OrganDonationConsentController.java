package tw.org.organ.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.Article;
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

	@PostMapping
	@Operation(summary = "線上填寫器捐同意書")
	public R<InsertOrganDonationConsentDTO> saveOrganDonationConsent(
			@Validated @RequestBody InsertOrganDonationConsentDTO insertOrganDonationConsentDTO) {
		System.out.println(insertOrganDonationConsentDTO);
		
		organDonationConsentService.insertOrganDonationConsent(insertOrganDonationConsentDTO);
		
		return R.ok();
	}
	
	

}
