package tw.com.sc_uro.convert;

import org.mapstruct.Mapper;

import tw.com.sc_uro.pojo.DTO.InsertClinicHoursDTO;
import tw.com.sc_uro.pojo.DTO.UpdateClinicHoursDTO;
import tw.com.sc_uro.pojo.entity.ClinicHours;

@Mapper(componentModel = "spring")
public interface ClinicHoursConvert {

	ClinicHours insertDTOToEntity(InsertClinicHoursDTO insertClinicHoursDTO);

	ClinicHours updateDTOToEntity(UpdateClinicHoursDTO updateClinicHoursDTO);

}
