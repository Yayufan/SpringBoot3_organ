package tw.org.organ.service.impl;

import tw.org.organ.pojo.DTO.InsertArticleAttachmentDTO;
import tw.org.organ.pojo.entity.Article;
import tw.org.organ.pojo.entity.ArticleAttachment;
import tw.org.organ.convert.ArticleAttachmentConvert;
import tw.org.organ.mapper.ArticleAttachmentMapper;
import tw.org.organ.service.ArticleAttachmentService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文章的附件 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
@Service
@RequiredArgsConstructor
public class ArticleAttachmentServiceImpl extends ServiceImpl<ArticleAttachmentMapper, ArticleAttachment>
		implements ArticleAttachmentService {

	private final ArticleAttachmentMapper articleAttachmentMapper;
	private final ArticleAttachmentConvert articleAttachmentConvert;

	@Override
	public List<ArticleAttachment> getAllArticleAttachmentByArticleId(Long articleId) {
		List<ArticleAttachment> articleAttachmentList = articleAttachmentMapper.selectList(null);
		return articleAttachmentList;
	}

	@Override
	public IPage<ArticleAttachment> getAllArticleAttachmentByArticleId(Long articleId, Page<ArticleAttachment> page) {
		LambdaQueryWrapper<ArticleAttachment> articleAttachmentQueryWrapper = new LambdaQueryWrapper<>();
		articleAttachmentQueryWrapper.eq(ArticleAttachment::getArticleId, articleId);
		Page<ArticleAttachment> articleAttachmentPage = articleAttachmentMapper.selectPage(page,
				articleAttachmentQueryWrapper);
		return articleAttachmentPage;
	}

	@Override
	public void insertArticleAttachment(InsertArticleAttachmentDTO insertArticleAttachmentDTO, MultipartFile[] files) {
		// 轉換檔案
		ArticleAttachment articleAttachment = articleAttachmentConvert.insertDTOToEntity(insertArticleAttachmentDTO);

		// 確認並上傳檔案

		// 配置Path

		// 儲存進資料庫

	}

	@Override
	public void deleteArticleAttachment(Long articleAttachmentId) {

		ArticleAttachment articleAttachment = articleAttachmentMapper.selectById(articleAttachmentId);

		String filePath = articleAttachment.getPath();

		// 刪除檔案

		// 刪除資料
		articleAttachmentMapper.deleteById(articleAttachment);

	}

}
