package tw.org.organ.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.service.ArticleAttachmentService;

/**
 * <p>
 * 文章的附件 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2024-12-27
 */
@Tag(name = "文章附件API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/article-attachment")
public class ArticleAttachmentController {

	private final ArticleAttachmentService articleAttachmentService;
	
}
