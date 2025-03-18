package tw.org.organ.service;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import jakarta.validation.Valid;
import tw.org.organ.pojo.DTO.InsertTimeLineDTO;
import tw.org.organ.pojo.DTO.UpdateTimeLineDTO;
import tw.org.organ.pojo.VO.TimeLineFrontVO;
import tw.org.organ.pojo.VO.TimeLineVO;
import tw.org.organ.pojo.entity.TimeLine;

/**
 * <p>
 * 年表-時間線 服务类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */

@Validated
public interface TimeLineService extends IService<TimeLine> {

	/**
	 * 獲取前端顯示的協會年表
	 * 
	 * @return
	 */
	List<TimeLineFrontVO> getAllTimeLineFrontVO();
	
	/**
	 * 獲取全部協會年表事件
	 * 
	 * @return
	 */
	List<TimeLineVO> getAllTimeLine();
	
	/**
	 * 獲取全部協會年表事件(分頁)
	 * 
	 * @param pageInfo
	 * @return
	 */
	IPage<TimeLineVO> getAllTimeLine(Page<TimeLine> pageInfo);

	/**
	 * 獲取單一協會年表事件
	 * 
	 * @param timeLineId
	 * @return
	 */
	TimeLineVO getTimeLine(Long timeLineId);

	/**
	 * 新增協會年表事件
	 * 
	 * @param insertTimeLineDTO
	 */
	Long insertTimeLine(MultipartFile[] files,@Valid InsertTimeLineDTO insertTimeLineDTO);

	/**
	 * 更新協會年表時間線事件
	 * 
	 * @param updateTimeLineDTO
	 */
	void updateTimeLine(MultipartFile[] files,@Valid UpdateTimeLineDTO updateTimeLineDTO);

	/**
	 * 根據TimeLineId刪除單一協會年表時間線事件
	 * 
	 * @param timeLineId
	 */
	void deleteTimeLine(Long timeLineId);
	
}
