package tw.org.organ.service.impl;

import tw.org.organ.pojo.DTO.InsertOrganDonationConsentDTO;
import tw.org.organ.pojo.DTO.UpdateOrganDonationConsentDTO;
import tw.org.organ.pojo.entity.Member;
import tw.org.organ.pojo.entity.OrganDonationConsent;
import tw.org.organ.convert.OrganDonationConsentConvert;
import tw.org.organ.mapper.OrganDonationConsentMapper;
import tw.org.organ.service.OrganDonationConsentService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
@Service
@RequiredArgsConstructor
public class OrganDonationConsentServiceImpl extends ServiceImpl<OrganDonationConsentMapper, OrganDonationConsent>
		implements OrganDonationConsentService {

	private final OrganDonationConsentConvert organDonationConsentConvert;
	private String DEFAULT_STATUS = "0";

	@Override
	public OrganDonationConsent getOrganDonationConsent(Long organDonationConsentId) {
		OrganDonationConsent organDonationConsent = baseMapper.selectById(organDonationConsentId);
		return organDonationConsent;
	}

	@Override
	public List<OrganDonationConsent> getAllOrganDonationConsent() {
		List<OrganDonationConsent> organDonationConsentList = baseMapper.selectList(null);
		return organDonationConsentList;
	}

	@Override
	public IPage<OrganDonationConsent> getAllOrganDonationConsent(Page<OrganDonationConsent> page) {
		Page<OrganDonationConsent> organDonationConsentList = baseMapper.selectPage(page, null);
		return organDonationConsentList;
	}

	@Override
	public IPage<OrganDonationConsent> getAllOrganDonationConsentByStatus(Page<OrganDonationConsent> page,
			String status) {
		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();
		organDonationConsentQueryWrapper.eq(OrganDonationConsent::getStatus, status);

		Page<OrganDonationConsent> organDonationConsentList = baseMapper.selectPage(page,
				organDonationConsentQueryWrapper);
		return organDonationConsentList;
	}

	@Override
	public IPage<OrganDonationConsent> getAllOrganDonationConsentByStatus(Page<OrganDonationConsent> page,
			String status, String queryText) {
		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();
		organDonationConsentQueryWrapper.eq(OrganDonationConsent::getStatus, status)
				.like(OrganDonationConsent::getName, queryText).or().like(OrganDonationConsent::getIdCard, queryText)
				.or().like(OrganDonationConsent::getContactNumber, queryText).or()
				.like(OrganDonationConsent::getPhoneNumber, queryText);

		Page<OrganDonationConsent> organDonationConsentList = baseMapper.selectPage(page,
				organDonationConsentQueryWrapper);
		return organDonationConsentList;

	}

	@Override
	public Long getOrganDonationConsentCount() {
		Long selectCount = baseMapper.selectCount(null);
		return selectCount;
	}

	@Override
	public Long getOrganDonationConsentCount(String status) {
		LambdaQueryWrapper<OrganDonationConsent> organDonationConsentQueryWrapper = new LambdaQueryWrapper<>();
		organDonationConsentQueryWrapper.eq(OrganDonationConsent::getStatus, status);

		Long selectCount = baseMapper.selectCount(organDonationConsentQueryWrapper);
		return selectCount;
	}

	@Override
	public void insertOrganDonationConsent(InsertOrganDonationConsentDTO insertOrganDonationConsentDTO) {
		OrganDonationConsent organDonationConsent = organDonationConsentConvert
				.insertDTOToEntity(insertOrganDonationConsentDTO);
		organDonationConsent.setSignatureDate(LocalDate.now());
		organDonationConsent.setStatus(DEFAULT_STATUS);

		baseMapper.insert(organDonationConsent);

	}

	@Override
	public void updateOrganDonationConsent(UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO) {
		OrganDonationConsent organDonationConsent = organDonationConsentConvert
				.updateDTOToEntity(updateOrganDonationConsentDTO);
		baseMapper.updateById(organDonationConsent);
	}

	@Override
	public void updateOrganDonationConsent(List<UpdateOrganDonationConsentDTO> updateOrganDonationConsentDTOList) {
		for (UpdateOrganDonationConsentDTO updateOrganDonationConsentDTO : updateOrganDonationConsentDTOList) {
			this.updateOrganDonationConsent(updateOrganDonationConsentDTO);
		}

	}

}
