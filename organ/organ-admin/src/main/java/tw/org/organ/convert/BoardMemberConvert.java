package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertBoardMemberDTO;
import tw.org.organ.pojo.DTO.UpdateBoardMemberDTO;
import tw.org.organ.pojo.entity.BoardMember;

@Mapper(componentModel = "spring")
public interface BoardMemberConvert {

	
	BoardMember insertDTOToEntity(InsertBoardMemberDTO insertBoardMemberDTO);

	BoardMember updateDTOToEntity(UpdateBoardMemberDTO updateBoardMemberDTO);
	
	
	
}
