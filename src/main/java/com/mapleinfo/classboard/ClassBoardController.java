package com.mapleinfo.classboard;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapleinfo.classboard.bo.ClassBoardBO;
import com.mapleinfo.classboard.domain.ClassBoard;
import com.mapleinfo.classboard.domain.ClassBoardDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/classboard")
@RequiredArgsConstructor
public class ClassBoardController {

	
		private final ClassBoardBO classBoardBO;
	
		// 직업 게시판 메인 화면
		@GetMapping("/board-list-view")
		public String boardList(
				Model model
				) {
			
			List<ClassBoardDTO> classBoardList = classBoardBO.generateClassBoardList();
			model.addAttribute("classBoardList", classBoardList);
			
			return "classBoard/classBoardMain";
		}
	
		
		// 기존 직업 게시판 글쓰기
//		@GetMapping("/create-view")
//		public String create(
//				HttpSession session,
//				Model model
//				) {
//			
//			Integer userId = (Integer)session.getAttribute("id");
//			if(userId == null) {
//				return "user/signIn";
//			}
//			
//			model.addAttribute("userId", userId);
//			
//			return "classBoard/createClassBoard";
//		}

		
		
		// summerNote로 글쓰기
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
			
			return "classBoard/createClassBoardBySummernote";
		}
		
		
		
		// 자유 게시판 상세 내용
		@GetMapping("/detail-view")
		public String freeBoardDetail(
				HttpSession session,
				Model model,
				@RequestParam("classBoardId") int classBoardId
				) {
			
			Integer userId = (Integer)session.getAttribute("id");
			if(userId == null) {
				return "user/signIn";
			}
			
			userId = (int)userId;
			
			ClassBoardDTO classBoard = classBoardBO.generateClassBoard(classBoardId, userId);
			
			model.addAttribute("classBoard", classBoard);
			
			return "classBoard/classBoardDetail";
			
		}
		
		
		
		// 글 상세 -> 수정하기
		@GetMapping("/update/{boardId}")
		public String updateClassBoard(
				@PathVariable(name = "boardId") int boardId,
				Model model
				) {
			
			ClassBoard classBoard = classBoardBO.getClassBoardByClassBoardId(boardId);
			model.addAttribute("classBoard", classBoard);
			
			return "classBoard/updateClassBoard";
		}
		
		
		
	
}
