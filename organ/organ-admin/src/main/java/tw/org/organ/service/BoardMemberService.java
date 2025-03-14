package tw.org.organ.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import tw.org.organ.pojo.DTO.InsertBoardMemberDTO;
import tw.org.organ.pojo.DTO.UpdateBoardMemberDTO;
import tw.org.organ.pojo.entity.BoardMember;

/**
 * <p>
 * 理監事名單，包含理事長、常務理事、理事、常務監事、監事等.. 服务类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
public interface BoardMemberService extends IService<BoardMember> {

	/**
	 * 獲取全部屆數理監事成員
	 * 
	 * @return
	 */
	List<BoardMember> getAllBoardMember();
	
	/**
	 * 獲取全部屆數理監事成員(分頁)
	 * 
	 * @param pageInfo
	 * @return
	 */
	IPage<BoardMember> getAllBoardMember(Page<BoardMember> pageInfo);

	/**
	 * 獲取單一屆數理監事成員
	 * 
	 * @param boardMemberId
	 * @return
	 */
	BoardMember getBoardMember(Long boardMemberId);

	/**
	 * 新增理監事成員
	 * 
	 * @param insertBoardMemberDTO
	 */
	Long insertBoardMember(InsertBoardMemberDTO insertBoardMemberDTO);

	/**
	 * 更新理監事團隊
	 * 
	 * @param updateBoardMemberDTO
	 */
	void updateBoardMember(UpdateBoardMemberDTO updateBoardMemberDTO);

	/**
	 * 根據BoardMemberId刪除單一屆理監事團隊
	 * 
	 * @param boardMemberId
	 */
	void deleteBoardMember(Long boardMemberId);

}
