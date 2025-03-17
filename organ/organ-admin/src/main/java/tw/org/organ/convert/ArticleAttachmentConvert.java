package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertArticleAttachmentDTO;
import tw.org.organ.pojo.entity.ArticleAttachment;

@Mapper(componentModel = "spring")
public interface ArticleAttachmentConvert {
	ArticleAttachment insertDTOToEntity(InsertArticleAttachmentDTO insertArticleAttachmentDTO);

}
