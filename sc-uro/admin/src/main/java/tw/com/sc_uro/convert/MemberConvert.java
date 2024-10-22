package tw.com.sc_uro.convert;

import org.mapstruct.Mapper;

import tw.com.sc_uro.pojo.DTO.InsertMemberDTO;
import tw.com.sc_uro.pojo.DTO.MemberLoginInfo;
import tw.com.sc_uro.pojo.DTO.ProviderRegisterDTO;
import tw.com.sc_uro.pojo.DTO.UpdateMemberDTO;
import tw.com.sc_uro.pojo.VO.MemberVO;
import tw.com.sc_uro.pojo.entity.Member;

@Mapper(componentModel = "spring")
public interface MemberConvert {

	Member loginInfoToEntity(MemberLoginInfo memberLoginInfo);
	
	Member insertDTOToEntity(InsertMemberDTO insertMemberDTO);

	Member updateDTOToEntity(UpdateMemberDTO updateMemberDTO);
	
	Member providerRegisterDTO(ProviderRegisterDTO providerRegisterDTO);
	
	MemberVO entityToVO(Member member);
	
}
