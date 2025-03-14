package tw.org.organ.service.impl;

import tw.org.organ.pojo.entity.SinglePageArticle;
import tw.org.organ.mapper.SinglePageArticleMapper;
import tw.org.organ.service.SinglePageArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 單頁文章，用來設計簡單的單頁內容 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Service
public class SinglePageArticleServiceImpl extends ServiceImpl<SinglePageArticleMapper, SinglePageArticle> implements SinglePageArticleService {

}
