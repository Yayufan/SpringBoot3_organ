package tw.com.sc_uro.convert;

import org.mapstruct.Mapper;

import tw.com.sc_uro.pojo.DTO.InsertArticleDTO;
import tw.com.sc_uro.pojo.DTO.UpdateArticleDTO;
import tw.com.sc_uro.pojo.entity.Article;

@Mapper(componentModel = "spring")
public interface ArticleConvert {

	Article insertDTOToEntity(InsertArticleDTO insertArticleDTO);

	Article updateDTOToEntity(UpdateArticleDTO updateArticleDTO);
	
	Article copyEntity(Article article);
	
}
