package tw.org.organ.service.impl;

import java.util.List;
import java.util.Map;
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
import tw.org.organ.pojo.VO.TimeLineFrontVO;
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
	public List<TimeLineFrontVO> getAllTimeLineFrontVO() {
		List<TimeLineVO> timeLineVOList = this.getAllTimeLine();
		
		// 2. 根據年份進行分組
	    Map<Integer, List<TimeLineVO>> groupedByYear = timeLineVOList.stream()
	            .collect(Collectors.groupingBy(TimeLineVO::getYear));

	    
	    /**
	     * groupedByYear.entrySet() 是 Java Map 的一個方法，
	     * 它的作用是返回一個 Set 集合，其中每個元素都是 Map 中的一個鍵值對（Map.Entry）。
	     * 這在處理 Map 時非常常用，特別是在你想要遍歷 Map 的所有鍵值對時。
	     * 
	     * entrySet() 方法作用：
	     * entrySet() 返回的是 Map 中所有鍵值對的 Set 集合。
	     * 每個 Set 的元素是 Map.Entry 類型，它表示一個鍵值對。
	     * Map.Entry 具有兩個方法：getKey() 和 getValue()，分別用來取得鍵和值。
	     * 
	     */
	    // 3. 將分組資料轉換為 TimeLineFrontVO 並返回
	    List<TimeLineFrontVO> timeLineFrontVOList = groupedByYear.entrySet().stream()
	    		/**
	    		 * sorted() 是 Stream 類型的一個方法，用來對 Stream 中的元素進行排序。
	    		 * 它接收一個 Comparator（比較器）作為參數，並按照該比較器的規則進行排序
	    		 * 在這裡，sorted() 用於排序 groupedByYear.entrySet() 這個 Stream，entrySet() 返回的是 Map.Entry 類型的元素，
	    		 * 其中每個 Map.Entry 包含了一個年份（鍵）和對應的事件列表（值）。
	    		 * 
	    		 * (entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey())，它是一個 Lambda 表達式
	    		 * entry1 和 entry2：這兩個參數代表的是 Map.Entry 類型的元素，它們是groupedByYear.entrySet() 流中的兩個元素。每個 entry 代表一個年份和對應的事件列表。
	    		 * 
	    		 * entry1.getKey() 和 entry2.getKey()：這是 Map.Entry 中的 鍵，也就是年份（Integer 類型）。
	    		 * compareTo() 方法：這是 Integer 類型自帶的一個方法，用來比較兩個 Integer 值的大小。它會返回：
	    		 * 
	    		 * 正常情況下，compareTo 是按升序排列（即 entry1.getKey() 比 entry2.getKey() 小時排在前面）。
	    		 * 但是，為了讓年份按照降序排列，我們交換了兩者的位置，讓較大的年份（即較近的年份）排在前面。
	    		 * 
	    		 * 
	    		 * 如果 entry1 的年份（entry1.getKey()）大於 entry2 的年份（entry2.getKey()），
	    		 * 則 compareTo 返回正數，意味著 entry1 會排在 entry2 的後面。
	    		 */
	    		.sorted((entry1, entry2) -> entry2.getKey().compareTo(entry1.getKey()))
	    		.map(entry -> {
	                TimeLineFrontVO frontVO = new TimeLineFrontVO();
	                frontVO.setYear(entry.getKey());
	                frontVO.setTimeLineVO(entry.getValue());
	                return frontVO;
	            })
	            .collect(Collectors.toList());

	    return timeLineFrontVOList;

	}
	
	@Override
	public List<TimeLineVO> getAllTimeLine() {
		
		LambdaQueryWrapper<TimeLine> timeLinelambdaQuery = new LambdaQueryWrapper<>();
		timeLinelambdaQuery.orderByDesc(TimeLine::getYear).orderByAsc(TimeLine::getEventDate);

		
		List<TimeLine> timeLineList = baseMapper.selectList(timeLinelambdaQuery);
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
