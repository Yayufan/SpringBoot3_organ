package tw.org.organ.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.google.common.base.Joiner;

import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.DTO.UpdateOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;
import tw.org.organ.pojo.excelPojo.OrganDonationConsentExcel;

@Mapper(componentModel = "spring")
public interface OrganDonationConsentConvert {

	@Mapping(target = "donateOrgans", source = "donateOrgans", qualifiedByName = "listToString")
	OrganDonationConsent insertDTOToEntity(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO);

	@Mapping(target = "donateOrgans", source = "donateOrgans", qualifiedByName = "listToString")
	OrganDonationConsent updateDTOToEntity(UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO);

	
	@Mapping(target = "consentCard", source = "consentCard", qualifiedByName = "convertConsentCard")
	@Mapping(target = "status", source = "status", qualifiedByName = "convertStatus")
	OrganDonationConsentExcel entityToExcel(OrganDonationConsent organDonationConsent);

	@Named("listToString")
	default String listToString(List<String> donateOrgans) {
		return Joiner.on(",").skipNulls().join(donateOrgans);
	}

	@Named("convertStatus")
	default String convertStatus(String status) {
		switch (status) {
		case "1":
			return "審核通過";
		case "0":
			return "未審核";
		case "-1":
			return "廢除簽署";
		default:
			return "未知";
		}
	}
	
	@Named("convertConsentCard")
	default String convertConsentCard(String consentCard) {
		switch (consentCard) {
		case "1":
			return "需要";
		case "-1":
			return "不需要";
		default:
			return "";
		}
	}

}
