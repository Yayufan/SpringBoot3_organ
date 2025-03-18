package tw.org.organ.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.SinglePageArticleConvert;
import tw.org.organ.exception.ExistPageException;
import tw.org.organ.mapper.SinglePageArticleMapper;
import tw.org.organ.pojo.DTO.InsertSinglePageArticleDTO;
import tw.org.organ.pojo.DTO.UpdateSinglePageArticleDTO;
import tw.org.organ.pojo.entity.SinglePageArticle;
import tw.org.organ.service.CmsService;
import tw.org.organ.service.SinglePageArticleService;
import tw.org.organ.utils.ArticleViewsCounterUtil;

/**
 * <p>
 * 單頁文章，用來設計簡單的單頁內容 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Service
@RequiredArgsConstructor
public class SinglePageArticleServiceImpl extends ServiceImpl<SinglePageArticleMapper, SinglePageArticle> implements SinglePageArticleService {

	@Value("${minio.bucketName}")
	private String minioBucketName;
	
	private final SinglePageArticleConvert singlePageArticleConvert;
	private final ArticleViewsCounterUtil articleViewsCounterUtil;
	private final CmsService cmsService;
	
	@Override
	public SinglePageArticle getSinglePageArticle(String path) {
		LambdaQueryWrapper<SinglePageArticle> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SinglePageArticle::getPath,path);
		SinglePageArticle singlePageArticle = baseMapper.selectOne(queryWrapper);
		return singlePageArticle;
	}

	@Override
	public SinglePageArticle getShowSinglePageArticle(String path) {
		LambdaQueryWrapper<SinglePageArticle> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SinglePageArticle::getPath,path);
		SinglePageArticle singlePageArticle = baseMapper.selectOne(queryWrapper);
		
		articleViewsCounterUtil.incrementViewCount(path, singlePageArticle.getSinglePageArticleId());
		return singlePageArticle;
	}

	@Override
	public Long insertSinglePageArticle(InsertSinglePageArticleDTO insertSinglePageArticleDTO) {
		LambdaQueryWrapper<SinglePageArticle> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(SinglePageArticle::getPath,insertSinglePageArticleDTO.getPath());
		boolean exists = baseMapper.exists(queryWrapper);
		
		if(exists) {
			throw new ExistPageException("已經存在此頁面，請勿重複創建");
		}
		SinglePageArticle singlePageArticle = singlePageArticleConvert.insertDTOToEntity(insertSinglePageArticleDTO);
		
		//直接創建一個空頁面
		SinglePageArticle emptySinglePageArticle = new SinglePageArticle();
		
		//新增
		baseMapper.insert(singlePageArticle);
		
		return singlePageArticle.getSinglePageArticleId();
	}

	@Override
	public void updateSinglePageArticle(UpdateSinglePageArticleDTO updateSinglePageArticleDTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSinglePageArticle(Long singlePageArticleId) {
		// TODO Auto-generated method stub
		
	}

}
