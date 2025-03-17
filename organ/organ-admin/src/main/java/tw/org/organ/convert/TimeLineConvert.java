package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertTimeLineDTO;
import tw.org.organ.pojo.DTO.UpdateTimeLineDTO;
import tw.org.organ.pojo.VO.TimeLineVO;
import tw.org.organ.pojo.entity.TimeLine;

@Mapper(componentModel = "spring")
public interface TimeLineConvert {
	
	TimeLine insertDTOToEntity(InsertTimeLineDTO insertTimeLineDTO);

	TimeLine updateDTOToEntity(UpdateTimeLineDTO updateTimeLineDTO);
	
	TimeLineVO entityToVO(TimeLine timeLine);
	
	
}
