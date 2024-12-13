package com.mapleinfo.freeboard;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapleinfo.freeboard.bo.FreeBoardBO;
import com.mapleinfo.freeboard.domain.FreeBoard;
import com.mapleinfo.freeboard.domain.FreeBoardDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/freeboard")
@RequiredArgsConstructor
public class FreeBoardController {

	private final FreeBoardBO freeBoardBO;
	
	
	
	// 자유 게시판 메인 화면
	@GetMapping("/board-list-view")
	public String boardList(
			Model model
			) {
		
//		List<FreeBoard> freeBoardList = freeBoardBO.getFreeBoardList();
//		model.addAttribute("freeBoardList", freeBoardList);
		
		List<FreeBoardDTO> freeBoardList = freeBoardBO.generateFreeBoardList();
		
		model.addAttribute("freeBoardList", freeBoardList);
		
		return "freeboard/freeBoardMain";
	}
	
	
	
	// 자유 게시판 글쓰기
	@GetMapping("/create-view")
	public String create(
			HttpSession session,
			Model model
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			return "user/signIn";
		}
		
		model.addAttribute("userId", userId);
		
		return "freeboard/createFreeBoard";
	}
	
	
	
	// 자유 게시판 상세 내용
	@GetMapping("/detail-view")
	public String freeBoardDetail(
			HttpSession session,
			Model model,
			@RequestParam("freeBoardId") int freeBoardId
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			return "user/signIn";
		}
		
		userId = (int)userId;
		
//		FreeBoard freeBoard = freeBoardBO.getFreeBoardByFreeBoardId(freeBoardId);
//		
//		//TODO : 댓글, 좋아요 가져와서 모델에 담기
//		String type ="자유";
//		List<Comment> commentList = commentBO.getCommentListByTypeAndBoardId(type ,freeBoardId);
//		
//		int likeCount = likeBO.getLikeCountByTypeAndBoardId(type, freeBoardId);
//		
//		boolean like = likeBO.isLikeByTypeAndBoardIdAndUserId(type, freeBoardId, userId);
//		
//		model.addAttribute("freeBoard", freeBoard);
//		model.addAttribute("commentList", commentList);
//		model.addAttribute("likeCount", likeCount);
//		model.addAttribute("isLike", like);
		
		FreeBoardDTO freeBoard = freeBoardBO.generateFreeBoard(freeBoardId, userId);
		
		model.addAttribute("freeBoard", freeBoard);
		
		return "freeBoard/freeBoardDetail";
		
	}
	
	
	// 글 상세 -> 수정하기
	@GetMapping("/update/{boardId}")
	public String updateFreeBoard(
			@PathVariable(name = "boardId") int boardId,
			Model model,
			HttpSession session
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			return "user/signIn";
		}
		
		userId = (int)userId;
		
		FreeBoardDTO freeBoardDTO = freeBoardBO.generateFreeBoard(boardId, userId);
		model.addAttribute("freeBoard", freeBoardDTO);
		
		
		return "freeBoard/updateFreeBoard";
	}
	
	
	
	// test
	@GetMapping("/test-view")
	public String testView() {
		return "freeBoard/testFreeBoard";
	}
	
	
	
	
	
}
