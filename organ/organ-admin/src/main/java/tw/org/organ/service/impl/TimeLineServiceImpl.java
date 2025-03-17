package tw.org.organ.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import tw.org.organ.convert.TimeLineConvert;
import tw.org.organ.mapper.TimeLineAttachmentMapper;
import tw.org.organ.mapper.TimeLineMapper;
import tw.org.organ.pojo.DTO.InsertTimeLineDTO;
import tw.org.organ.pojo.DTO.UpdateTimeLineDTO;
import tw.org.organ.pojo.VO.TimeLineVO;
import tw.org.organ.pojo.entity.TimeLine;
import tw.org.organ.pojo.entity.TimeLineAttachment;
import tw.org.organ.service.TimeLineService;
import tw.org.organ.utils.MinioUtil;

/**
 * <p>
 * 年表-時間線 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Service
@RequiredArgsConstructor
public class TimeLineServiceImpl extends ServiceImpl<TimeLineMapper, TimeLine> implements TimeLineService {

	@Value("${minio.bucketName}")
	private String minioBucketName;

	private static final String MINIO_BUCKET_PATH = "timeLine/";

	private final TimeLineConvert timeLineConvert;
	private final TimeLineAttachmentMapper timeLineAttachmentMapper;
	private final MinioUtil minioUtil;

	@Override
	public List<TimeLineVO> getAllTimeLine() {
		List<TimeLine> timeLineList = baseMapper.selectList(null);
		List<TimeLineVO> timeLineVOList = timeLineList.stream().map(timeLine -> {
			TimeLineVO vo = timeLineConvert.entityToVO(timeLine);
			LambdaQueryWrapper<TimeLineAttachment> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(TimeLineAttachment::getTimeLineId, vo.getTimeLineId());

			List<TimeLineAttachment> timeLineAttachmentList = timeLineAttachmentMapper.selectList(queryWrapper);
			vo.setTimeLineAttachment(timeLineAttachmentList);

			return vo;
		}).collect(Collectors.toList());

		return timeLineVOList;

	}

	@Override
	public IPage<TimeLineVO> getAllTimeLine(Page<TimeLine> pageInfo) {
		LambdaQueryWrapper<TimeLine> timeLinelambdaQuery = new LambdaQueryWrapper<>();
		timeLinelambdaQuery.orderByDesc(TimeLine::getYear).orderByDesc(TimeLine::getEventDate);

		Page<TimeLine> timeLinePage = baseMapper.selectPage(pageInfo, timeLinelambdaQuery);

		// 將 TimeLine 轉換成 TimeLineVO
		List<TimeLineVO> voList = timeLinePage.getRecords().stream().map(timeLine -> {

			TimeLineVO vo = timeLineConvert.entityToVO(timeLine);

			// 找到附件
			LambdaQueryWrapper<TimeLineAttachment> queryWrapper = new LambdaQueryWrapper<>();
			queryWrapper.eq(TimeLineAttachment::getTimeLineId, vo.getTimeLineId());
			List<TimeLineAttachment> timeLineAttachmentList = timeLineAttachmentMapper.selectList(queryWrapper);

			vo.setTimeLineAttachment(timeLineAttachmentList);
			return vo;
		}).collect(Collectors.toList());

		// 構造返回的分頁結果
		Page<TimeLineVO> resultPage = new Page<>(timeLinePage.getCurrent(), timeLinePage.getSize(),
				timeLinePage.getTotal());
		resultPage.setRecords(voList);

		return resultPage;
	}

	@Override
	public TimeLineVO getTimeLine(Long timeLineId) {

		// 找到資料轉換成VO
		TimeLine timeLine = baseMapper.selectById(timeLineId);
		TimeLineVO vo = timeLineConvert.entityToVO(timeLine);

		// 找到附件
		LambdaQueryWrapper<TimeLineAttachment> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TimeLineAttachment::getTimeLineId, vo.getTimeLineId());
		List<TimeLineAttachment> timeLineAttachmentList = timeLineAttachmentMapper.selectList(queryWrapper);

		// 組裝VO
		vo.setTimeLineAttachment(timeLineAttachmentList);

		return vo;
	}

	@Override
	public Long insertTimeLine(MultipartFile[] files, @Valid InsertTimeLineDTO insertTimeLineDTO) {
		// 先對檔案進行轉換
		TimeLine timeLine = timeLineConvert.insertDTOToEntity(insertTimeLineDTO);

		// 新增資料
		baseMapper.insert(timeLine);

		// 如果有上傳檔案
		if (files != null && files.length > 0) {
			List<String> pathList = minioUtil.upload(minioBucketName, MINIO_BUCKET_PATH, files);

			for (String path : pathList) {
				TimeLineAttachment timeLineAttachment = new TimeLineAttachment();
				timeLineAttachment.setTimeLineId(timeLine.getTimeLineId());
				timeLineAttachment.setPath("/" + minioBucketName + "/" + path);
				timeLineAttachmentMapper.insert(timeLineAttachment);

			}

		}

		return timeLine.getTimeLineId();
	}

	@Override
	public void updateTimeLine(MultipartFile[] files, @Valid UpdateTimeLineDTO updateTimeLineDTO) {
		// 如果有上傳檔案
		if (files != null && files.length > 0) {

		}

		// 轉換並進行更新
		TimeLine timeLine = timeLineConvert.updateDTOToEntity(updateTimeLineDTO);
		baseMapper.updateById(timeLine);

	}

	@Override
	public void deleteTimeLine(Long timeLineId) {

		// 刪除圖片
		LambdaQueryWrapper<TimeLineAttachment> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(TimeLineAttachment::getTimeLineId, timeLineId);
		List<TimeLineAttachment> timeLineAttachmentList = timeLineAttachmentMapper.selectList(queryWrapper);

		for (TimeLineAttachment timeLineAttachment : timeLineAttachmentList) {
			String filePath = timeLineAttachment.getPath();
			String result = filePath.substring(filePath.indexOf("/", 1));

			// 透過Minio進行刪除
			minioUtil.removeObject(minioBucketName, result);
			// 從資料庫刪除附件路徑資料
			timeLineAttachmentMapper.deleteById(timeLineAttachment.getTimeLineAttachmentId());
		}

		// 刪除資料
		baseMapper.deleteById(timeLineId);

	}

}
