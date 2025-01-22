package tw.org.organ.mapper;

import tw.org.organ.pojo.entity.OrganDonationConsent;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Joey
 * @since 2024-11-13
 */
public interface OrganDonationConsentMapper extends BaseMapper<OrganDonationConsent> {

	List<OrganDonationConsent> selectOrganDonationConsentsByDate(@Param("startDate") String startDate,@Param("endDate") String endDate);

}
