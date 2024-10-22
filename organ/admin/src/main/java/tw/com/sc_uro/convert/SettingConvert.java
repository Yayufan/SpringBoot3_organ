package tw.com.sc_uro.convert;

import org.mapstruct.Mapper;

import tw.com.sc_uro.pojo.DTO.UpdateSettingDTO;
import tw.com.sc_uro.pojo.entity.Setting;

@Mapper(componentModel = "spring")
public interface SettingConvert {

	Setting updateDTOToEntity(UpdateSettingDTO updateSettingDTO);
	
}
