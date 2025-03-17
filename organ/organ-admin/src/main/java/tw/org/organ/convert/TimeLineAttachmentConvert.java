package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertTimeLineAttachmentDTO;
import tw.org.organ.pojo.entity.TimeLineAttachment;

@Mapper(componentModel = "spring")
public interface TimeLineAttachmentConvert {
	TimeLineAttachment insertDTOToEntity(InsertTimeLineAttachmentDTO insertTimeLineAttachmentDTO);
}
