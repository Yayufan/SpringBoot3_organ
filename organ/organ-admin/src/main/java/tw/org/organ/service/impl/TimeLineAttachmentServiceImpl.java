package tw.org.organ.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.ArticleAttachmentConvert;
import tw.org.organ.convert.TimeLineAttachmentConvert;
import tw.org.organ.mapper.ArticleAttachmentMapper;
import tw.org.organ.mapper.TimeLineAttachmentMapper;
import tw.org.organ.pojo.DTO.InsertTimeLineAttachmentDTO;
import tw.org.organ.pojo.entity.TimeLineAttachment;
import tw.org.organ.service.TimeLineAttachmentService;
import tw.org.organ.utils.MinioUtil;

/**
 * <p>
 * 協會年表時間線附件 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-03-17
 */
@Service
@RequiredArgsConstructor
public class TimeLineAttachmentServiceImpl extends ServiceImpl<TimeLineAttachmentMapper, TimeLineAttachment> implements TimeLineAttachmentService {


	private final TimeLineAttachmentMapper timeLineAttachmentMapper;
	private final TimeLineAttachmentConvert timeLineAttachmentConvert;
	private final MinioUtil minioUtil;
	private final String PATH = "timeLine";
	
	@Value("${minio.bucketName}")
	private String minioBucketName;

	@Override
	public List<TimeLineAttachment> getAllTimeLineAttachmentByTimeLineId(Long timeLineId) {
		LambdaQueryWrapper<TimeLineAttachment> timeLineAttachmentQueryWrapper = new LambdaQueryWrapper<>();
		timeLineAttachmentQueryWrapper.eq(TimeLineAttachment::getTimeLineId,timeLineId);
		List<TimeLineAttachment> timeLineAttachmentList = timeLineAttachmentMapper.selectList(timeLineAttachmentQueryWrapper);
		return timeLineAttachmentList;
	}

	@Override
	public IPage<TimeLineAttachment> getAllTimeLineAttachmentByTimeLineId(Long timeLineId, Page<TimeLineAttachment> page) {
		LambdaQueryWrapper<TimeLineAttachment> timeLineAttachmentQueryWrapper = new LambdaQueryWrapper<>();
		timeLineAttachmentQueryWrapper.eq(TimeLineAttachment::getTimeLineId, timeLineId);
		Page<TimeLineAttachment> timeLineAttachmentPage = timeLineAttachmentMapper.selectPage(page,
				timeLineAttachmentQueryWrapper);
		return timeLineAttachmentPage;
	}

	@Override
	public void insertTimeLineAttachment(InsertTimeLineAttachmentDTO insertTimeLineAttachmentDTO, MultipartFile[] files) {
		// 轉換檔案
		TimeLineAttachment timeLineAttachment = timeLineAttachmentConvert.insertDTOToEntity(insertTimeLineAttachmentDTO);

		// 檔案存在，處理檔案
		if (files != null && files.length > 0) {

			List<String> upload = minioUtil.upload(minioBucketName, PATH + "/", files);
			// 基本上只有有一個檔案跟著formData上傳,所以這邊直接寫死,把唯一的url增添進對象中
			String url = upload.get(0);
			// 將bucketName 組裝進url
			url = "/" + minioBucketName + "/" + url;
			// minio完整路徑放路對象中
			timeLineAttachment.setPath(url);

			// 放入資料庫
			baseMapper.insert(timeLineAttachment);

		}

		System.out.println("上傳完成");

	}

	@Override
	public void deleteTimeLineAttachment(Long timeLineAttachmentId) {

		TimeLineAttachment timeLineAttachment = timeLineAttachmentMapper.selectById(timeLineAttachmentId);

		String filePath = timeLineAttachment.getPath();
		String result = filePath.substring(filePath.indexOf("/", 1));

		// 透過Minio進行刪除
		minioUtil.removeObject(minioBucketName, result);
		// 資料庫資料刪除
		baseMapper.deleteById(timeLineAttachmentId);

	}

	
	
}
