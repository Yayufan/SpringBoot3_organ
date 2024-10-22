package tw.com.sc_uro.service.impl;

import tw.com.sc_uro.pojo.DTO.InsertClinicHoursDTO;
import tw.com.sc_uro.pojo.DTO.UpdateClinicHoursDTO;
import tw.com.sc_uro.pojo.entity.ClinicHours;
import tw.com.sc_uro.mapper.ClinicHoursMapper;
import tw.com.sc_uro.service.ClinicHoursService;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 門診時間圖片表 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-07-15
 */
@Service
public class ClinicHoursServiceImpl extends ServiceImpl<ClinicHoursMapper, ClinicHours> implements ClinicHoursService {

	@Override
	public List<ClinicHours> getAllClinicHours() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPage<ClinicHours> getAllClinicHours(Page<ClinicHours> page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClinicHours getClinicHours(Long clinicHoursId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertClinicHours(InsertClinicHoursDTO insertClinicHoursDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateClinicHours(UpdateClinicHoursDTO updateClinicHoursDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteClinicHours(Long clinicHoursId) {
		// TODO Auto-generated method stub
		
	}

}
