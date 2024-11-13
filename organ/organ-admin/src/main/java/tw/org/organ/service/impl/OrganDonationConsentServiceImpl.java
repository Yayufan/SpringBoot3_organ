package tw.org.organ.service.impl;

import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;
import tw.org.organ.convert.OrganDonationConsentConvert;
import tw.org.organ.mapper.OrganDonationConsentMapper;
import tw.org.organ.service.OrganDonationConsentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
@Service
@RequiredArgsConstructor
public class OrganDonationConsentServiceImpl extends ServiceImpl<OrganDonationConsentMapper, OrganDonationConsent> implements OrganDonationConsentService {

	private final OrganDonationConsentConvert organDonationConsentConvert;
	private String DEFAULT_STATUS = "0";
	
	@Override
	public void insertOrganDonationConsent(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO) {
		
		OrganDonationConsent organDonationConsent = organDonationConsentConvert.insertDTOToEntity(insertOrganDonationConsentDTO);
		organDonationConsent.setSignatureDate(LocalDate.now());
		organDonationConsent.setStatus(DEFAULT_STATUS);
		
		baseMapper.insert(organDonationConsent);
		
	}

}
