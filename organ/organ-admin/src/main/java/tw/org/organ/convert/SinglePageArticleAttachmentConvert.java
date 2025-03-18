package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertSinglePageArticleAttachmentDTO;
import tw.org.organ.pojo.entity.SinglePageArticleAttachment;

@Mapper(componentModel = "spring")
public interface SinglePageArticleAttachmentConvert {
	SinglePageArticleAttachment insertDTOToEntity(InsertSinglePageArticleAttachmentDTO insertSinglePageArticleAttachmentDTO);

}
