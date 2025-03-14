package tw.org.organ.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import cn.dev33.satoken.annotation.SaCheckLogin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import tw.org.organ.pojo.DTO.InsertBoardMemberDTO;
import tw.org.organ.pojo.DTO.UpdateBoardMemberDTO;
import tw.org.organ.pojo.entity.Article;
import tw.org.organ.pojo.entity.BoardMember;
import tw.org.organ.service.BoardMemberService;
import tw.org.organ.service.BoardMemberService;
import tw.org.organ.utils.R;

/**
 * <p>
 * 理監事名單，包含理事長、常務理事、理事、常務監事、監事等.. 前端控制器
 * </p>
 *
 * @author Joey
 * @since 2025-03-14
 */
@Tag(name = "理監事成員API")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/board-member")
public class BoardMemberController {
	
	private final BoardMemberService boardMemberService;
	
	@GetMapping("{id}")
	@Operation(summary = "查詢單一屆理監事團隊")
	public R<BoardMember> getBoardMember(@PathVariable("id") Long boardMemberId) {
		BoardMember boardMember = boardMemberService.getBoardMember(boardMemberId);
		return R.ok(boardMember);
	}
	
	@GetMapping
	@Operation(summary = "查詢所有屆數理監事團隊")
	public R<List<BoardMember>> getAllBoardMember() {
		List<BoardMember> boardMemberList = boardMemberService.getAllBoardMember();
		return R.ok(boardMemberList);
	}
	
	@GetMapping("pagination")
	@Operation(summary = "查詢所有屆數理監事團隊(分頁)")
	public R<IPage<BoardMember>> getAllArticle(@RequestParam Integer page, @RequestParam Integer size) {
		Page<BoardMember> pageInfo = new Page<>(page, size);
		IPage<BoardMember> boardMemberPage = boardMemberService.getAllBoardMember(pageInfo);

		return R.ok(boardMemberPage);
	}
	
	@Operation(summary = "新增理監事團隊")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PostMapping
	public R<Long> saveBoardMember(
			@Validated @RequestBody InsertBoardMemberDTO insertBoardMemberDTO) {
		Long boardMemberId = boardMemberService.insertBoardMember(insertBoardMemberDTO);
		return R.ok(boardMemberId);

	}

	@Operation(summary = "更新理監事團隊")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@PutMapping
	public R<Void> updateBoardMember(@RequestBody @Validated UpdateBoardMemberDTO updateBoardMemberDTO) {
		boardMemberService.updateBoardMember(updateBoardMemberDTO);
		return R.ok();

	}

	@Operation(summary = "刪除單一屆理監事團隊")
	@Parameters({
			@Parameter(name = "Authorization", description = "請求頭token,token-value開頭必須為Bearer ", required = true, in = ParameterIn.HEADER) })
	@SaCheckLogin
	@DeleteMapping("{id}")
	public R<Void> deleteBoardMember(@PathVariable("id") Long boardMemberId) {
		boardMemberService.deleteBoardMember(boardMemberId);
		return R.ok();

	}

}
