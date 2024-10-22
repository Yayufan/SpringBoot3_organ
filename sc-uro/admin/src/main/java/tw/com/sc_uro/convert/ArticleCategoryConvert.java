package tw.com.sc_uro.convert;

import org.mapstruct.Mapper;

import tw.com.sc_uro.pojo.DTO.InsertArticleCategoryDTO;
import tw.com.sc_uro.pojo.DTO.UpdateArticleCategoryDTO;
import tw.com.sc_uro.pojo.entity.ArticleCategory;

@Mapper(componentModel = "spring")
public interface ArticleCategoryConvert {

	ArticleCategory insertDTOToEntity(InsertArticleCategoryDTO insertArticleCategoryDTO);

	ArticleCategory updateDTOToEntity(UpdateArticleCategoryDTO updateArticleCategoryDTO);

}
