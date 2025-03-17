package tw.org.organ.service;

import tw.org.organ.pojo.DTO.InsertTimeLineAttachmentDTO;
import tw.org.organ.pojo.entity.TimeLineAttachment;
import tw.org.organ.pojo.entity.TimeLineAttachment;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 協會年表時間線附件 服务类
 * </p>
 *
 * @author Joey
 * @since 2025-03-17
 */
public interface TimeLineAttachmentService extends IService<TimeLineAttachment> {
	/**
	 * 根據時間線事件ID,獲取該時間線事件的所有附件
	 * 
	 * @param timeLineId
	 * @return
	 */
	List<TimeLineAttachment> getAllTimeLineAttachmentByTimeLineId(Long timeLineId);

	/**
	 * 根據時間線事件ID,獲取該時間線事件的所有附件(分頁)
	 * 
	 * @param timeLineId
	 * @param page
	 * @return
	 */
	IPage<TimeLineAttachment> getAllTimeLineAttachmentByTimeLineId(Long timeLineId, Page<TimeLineAttachment> page);

	/**
	 * 新增時間線事件附件
	 * 
	 * @param insertTimeLineDTO
	 */
	void insertTimeLineAttachment(InsertTimeLineAttachmentDTO insertTimeLineAttachmentDTO, MultipartFile[] files);

	
	/**
	 * 根據timeLineAttachmentId刪除時間線事件附件
	 * 
	 * @param timeLineAttachmentId
	 */
	void deleteTimeLineAttachment(Long timeLineAttachmentId);
}
