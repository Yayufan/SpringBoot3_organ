package tw.org.organ.service.impl;

import tw.org.organ.pojo.DTO.InsertBoardMemberDTO;
import tw.org.organ.pojo.DTO.UpdateBoardMemberDTO;
import tw.org.organ.pojo.entity.BoardMember;
import tw.org.organ.convert.BoardMemberConvert;
import tw.org.organ.mapper.BoardMemberMapper;
import tw.org.organ.service.BoardMemberService;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * <p>
 * 理監事名單，包含理事長、常務理事、理事、常務監事、監事等.. 服务实现类
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Service
@RequiredArgsConstructor
public class BoardMemberServiceImpl extends ServiceImpl<BoardMemberMapper, BoardMember> implements BoardMemberService {

	private final BoardMemberConvert boardMemberConvert;

	@Override
	public List<BoardMember> getAllBoardMember() {
		List<BoardMember> boardMemberList = baseMapper.selectList(null);
		return boardMemberList;
	}
	
	@Override
	public IPage<BoardMember> getAllBoardMember(Page<BoardMember> pageInfo) {
		// 依照屆數倒序排列，最新的在越前面
		LambdaQueryWrapper<BoardMember> boardMemberlambdaQuery = new LambdaQueryWrapper<>();
		boardMemberlambdaQuery.orderByDesc(BoardMember::getTermNumber);	
		Page<BoardMember> boardMemberPage = baseMapper.selectPage(pageInfo, boardMemberlambdaQuery);
		return boardMemberPage;
	}

	@Override
	public BoardMember getBoardMember(Long boardMemberId) {
		BoardMember boardMember = baseMapper.selectById(boardMemberId);
		return boardMember;
	}

	@Override
	public Long insertBoardMember(InsertBoardMemberDTO insertBoardMemberDTO) {
		BoardMember boardMember = boardMemberConvert.insertDTOToEntity(insertBoardMemberDTO);
		baseMapper.insert(boardMember);
		return boardMember.getBoardMemberId();
	}

	@Override
	public void updateBoardMember(UpdateBoardMemberDTO updateBoardMemberDTO) {
		BoardMember boardMember = boardMemberConvert.updateDTOToEntity(updateBoardMemberDTO);
		baseMapper.updateById(boardMember);
	}

	@Override
	public void deleteBoardMember(Long boardMemberId) {
		baseMapper.deleteById(boardMemberId);
	}



}
