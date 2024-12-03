package com.mapleinfo.freeboard;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapleinfo.comment.bo.CommentBO;
import com.mapleinfo.comment.domain.Comment;
import com.mapleinfo.freeboard.bo.FreeBoardBO;
import com.mapleinfo.freeboard.domain.FreeBoard;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/freeboard")
@RequiredArgsConstructor
public class FreeBoardController {

	private final FreeBoardBO freeBoardBO;
	private final CommentBO commentBO;
	
	// 자유 게시판 메인 화면
	@GetMapping("/board-list-view")
	public String boardList(
			Model model
			) {
		
		List<FreeBoard> freeBoardList = freeBoardBO.getFreeBoardList();
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
		
		FreeBoard freeBoard = freeBoardBO.getFreeBoardByFreeBoardId(freeBoardId);
		
		//TODO : 댓글, 좋아요 가져와서 모델에 담기
		String type ="자유";
		List<Comment> commentList = commentBO.getCommentListByTypeAndBoardId(type ,freeBoardId);
		
		
		model.addAttribute("freeBoard", freeBoard);
		model.addAttribute("commentList", commentList);
		return "freeBoard/freeBoardDetail";
		
	}
	
	
}
