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
import tw.org.organ.mapper.TimeLineMapper;
import tw.org.organ.pojo.DTO.InsertTimeLineDTO;
import tw.org.organ.pojo.DTO.UpdateTimeLineDTO;
import tw.org.organ.pojo.entity.TimeLine;
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
	private final MinioUtil minioUtil;

	@Override
	public List<TimeLine> getAllTimeLine() {
		List<TimeLine> timeLineList = baseMapper.selectList(null);
		return timeLineList;
	}

	@Override
	public IPage<TimeLine> getAllTimeLine(Page<TimeLine> pageInfo) {
		LambdaQueryWrapper<TimeLine> timeLinelambdaQuery = new LambdaQueryWrapper<>();
		timeLinelambdaQuery.orderByDesc(TimeLine::getYear).orderByDesc(TimeLine::getEventDate);

		Page<TimeLine> timeLinePage = baseMapper.selectPage(pageInfo, timeLinelambdaQuery);
		return timeLinePage;
	}

	@Override
	public TimeLine getTimeLine(Long timeLineId) {
		TimeLine timeLine = baseMapper.selectById(timeLineId);
		return timeLine;
	}

	@Override
	public Long insertTimeLine(MultipartFile[] files, @Valid InsertTimeLineDTO insertTimeLineDTO) {
		// 先對檔案進行轉換
		TimeLine timeLine = timeLineConvert.insertDTOToEntity(insertTimeLineDTO);

		// 如果有上傳檔案
		if (files != null && files.length > 0) {
			List<String> pathList = minioUtil.upload(minioBucketName, MINIO_BUCKET_PATH , files);

	        // 在每個 path 前加上 /organ/ 並轉換為分號分隔的字符串
	        String pathString = pathList.stream()
	                                    .map(path -> "/" + minioBucketName + "/" + path)
	                                    .collect(Collectors.joining(";"));
	        			
		}

		// 新增資料
		baseMapper.insert(timeLine);
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

		// 刪除資料
		baseMapper.deleteById(timeLineId);

	}

}
