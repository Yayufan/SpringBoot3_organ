package tw.org.organ.convert;

import org.mapstruct.Mapper;

import tw.org.organ.pojo.DTO.InsertSinglePageArticleDTO;
import tw.org.organ.pojo.DTO.UpdateSinglePageArticleDTO;
import tw.org.organ.pojo.entity.SinglePageArticle;

@Mapper(componentModel = "spring")
public interface SinglePageArticleConvert {

	SinglePageArticle insertDTOToEntity(InsertSinglePageArticleDTO insertSinglePageArticleDTO );

	SinglePageArticle updateDTOToEntity(UpdateSinglePageArticleDTO updateSinglePageArticleDTO );
	
}
