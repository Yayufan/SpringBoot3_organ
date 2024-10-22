package tw.com.sc_uro.convert;

import org.mapstruct.Mapper;

import tw.com.sc_uro.pojo.DTO.InsertUserDTO;
import tw.com.sc_uro.pojo.DTO.UpdateUserDTO;
import tw.com.sc_uro.pojo.entity.User;

@Mapper(componentModel = "spring")
public interface UserConvert {

	User insertDTOToEntity(InsertUserDTO insertUserDTO);
	
	User updateDTOToEntity(UpdateUserDTO updateUserDTO);
	
}
