package tw.org.organ.system.convert;

import org.mapstruct.Mapper;

import tw.org.organ.system.pojo.VO.SysUserVO;
import tw.org.organ.system.pojo.entity.SysUser;

@Mapper(componentModel = "spring")
public interface SysUserConvert {

	//最後返回為SysUserVo對象, 方法名為entityToVO, 參數為SysUser對象
	SysUserVO entityToVO(SysUser sysUser);
	
}
