package tw.org.organ.convert;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.google.common.base.Joiner;

import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.DTO.UpdateOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;

@Mapper(componentModel = "spring")
public interface OrganDonationConsentConvert {

	@Mapping(target = "donateOrgans", source = "donateOrgans", qualifiedByName = "listToString")
	OrganDonationConsent insertDTOToEntity(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO);

	@Mapping(target = "donateOrgans", source = "donateOrgans", qualifiedByName = "listToString")
	OrganDonationConsent updateDTOToEntity(UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO);
	
	@Named("listToString")
	default String listToString(List<String> donateOrgans) {
		return Joiner.on(",").skipNulls().join(donateOrgans);
	}

}
