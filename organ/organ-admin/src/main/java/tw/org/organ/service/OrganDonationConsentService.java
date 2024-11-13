package tw.org.organ.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.DTO.UpdateOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.OrganDonationConsent;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
public interface OrganDonationConsentService extends IService<OrganDonationConsent> {

	/**
	 * 獲取單篇同意書
	 * 
	 * @param organDonationConsentId
	 * @return
	 */
	OrganDonationConsent getOrganDonationConsent(Long organDonationConsentId);

	/**
	 * 獲取全部同意書
	 * 
	 * @return
	 */
	List<OrganDonationConsent> getAllOrganDonationConsent();

	/**
	 * 獲取全部同意書(分頁)
	 * 
	 * @param page
	 * @return
	 */
	IPage<OrganDonationConsent> getAllOrganDonationConsent(Page<OrganDonationConsent> page);

	/**
	 * 獲取某個狀態下的全部同意書(分頁)
	 * 
	 * @param page
	 * @param status
	 * @return
	 */
	IPage<OrganDonationConsent> getAllOrganDonationConsentByStatus(Page<OrganDonationConsent> page, String status);

	/**
	 * 獲取某個狀態下,以及符合查詢條件的全部同意書(分頁)
	 * 
	 * @param page
	 * @param status
	 * @return
	 */
	IPage<OrganDonationConsent> getAllOrganDonationConsentByStatus(Page<OrganDonationConsent> page, String status,
			String queryText);

	/**
	 * 獲取全部同意書的總數
	 * 
	 * @return
	 */
	Long getOrganDonationConsentCount();

	/**
	 * 獲取某個狀態下全部同意書的總數
	 * 
	 * @param status
	 * @return
	 */
	Long getOrganDonationConsentCount(String status);

	/**
	 * 新增器捐同意書
	 * 
	 * @param insertOrganDonationConsentDTO
	 * @return
	 */
	void insertOrganDonationConsent(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO);

	/**
	 * 更新同意書的資料
	 * 
	 * @param updateMemberDTO
	 */
	void updateOrganDonationConsent(UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO);

	/**
	 * 提供一個簡單的API只負責更改同意書的狀態
	 * 
	 * @param updateMemberDTOList
	 */
	void updateOrganDonationConsent(List<UpdateOrganDonationConsentDTO> updateOrganDonationConsentDTOList);

}
