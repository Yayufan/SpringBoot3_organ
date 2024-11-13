package tw.org.organ.service;

import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
public interface OrganDonationConsentService extends IService<OrganDonationConsent> {

	/**
	 * 新增器捐同意書
	 * 
	 * @param insertOrganDonationConsentDTO
	 * @return
	 */
	void insertOrganDonationConsent(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO);
	
	
}
