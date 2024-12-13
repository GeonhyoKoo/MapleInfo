package com.mapleinfo.classboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapleinfo.classboard.bo.ClassBoardBO;
import com.mapleinfo.classboard.domain.ClassBoard;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/classboard")
@RequiredArgsConstructor
public class ClassBoardRestController {
	
		private final ClassBoardBO classBoardBO;
	
		// 게시글 작성
//		@PostMapping("/create-board")
//		public Map<String , Object> createBoard(
//				HttpSession session,
//				@RequestParam("userId") int id,
//				@RequestParam("characterClass") String characterClass,
//				@RequestParam("subject") String subject,
//				@RequestParam("content") String content,
//				@RequestParam(value= "file" , required = false) MultipartFile file
//				){
//			
//			Map<String , Object> result = new HashMap<>();
//			
//			Integer userId = (Integer)session.getAttribute("id");
//			String loginId = (String)session.getAttribute("loginId");
//			if(userId == null) {
//				result.put("code", 300);
//				result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
//				return result;
//			}
//			
//			if (id != userId) {
//				result.put("code" , 301);
//				result.put("error_message", "계정 정보가 일치하지 않습니다.");
//				return result;
//			}
//			
//			int resultCount = classBoardBO.addClassBoard(id, characterClass, loginId,  subject, content, file);
//			if (resultCount == 1) {
//				result.put("code" , 201);
//				result.put("result", "게시글 작성에 성공했습니다.");
//			} else {
//				result.put("code", 310);
//				result.put("error_message", "게시글 작성에 실패했습니다.");
//			}
//			
//			return result;
//		}
		
		
		// 글 작성 summernote
		@PostMapping("/create-board")
		public Map<String , Object> createBoard(
				HttpSession session,
				@RequestParam(value = "imgArr[]" , required = false) String[] imgArr,
				@ModelAttribute ClassBoard classBoard
				){
			
			Map<String , Object> result = new HashMap<>();
			Integer userId = (Integer)session.getAttribute("id");
			String loginId = (String)session.getAttribute("loginId");
			if(userId == null) {
				result.put("code", 300);
				result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
				return result;
			}
			
			userId = (int)userId;
			classBoard.setUserId(userId);
			
			int resultCount = classBoardBO.addClassBoard(classBoard, loginId, imgArr);
			
			if (resultCount > 0) {
				result.put("code", 200);
				result.put("result", "글 작성이 완료되었습니다.");
			}
			
			return result;
		}
		
		
		
		
		
		
		
		
		
		// 글 삭제
//		@DeleteMapping("/delete/{boardId}")
//		public Map<String , Object> deleteBoard(
//				HttpSession session,
//				@PathVariable(name = "boardId") int boardId
//				){
//			
//			Map<String , Object> result = new HashMap<>();
//			Integer userId = (Integer)session.getAttribute("id");
//			String loginId = (String)session.getAttribute("loginId");
//			if(userId == null) {
//				result.put("code", 300);
//				result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
//				return result;
//			}
//			
//			int resultCount = classBoardBO.deleteClassBoard(userId , loginId , boardId);
//			if (resultCount == -1) {
//				result.put("code", 320);
//				result.put("error_message", "등록된 게시글이 존재하지 않습니다.");
//				return result;
//			}
//			
//			result.put("code", 210);
//			result.put("result", "게시글을 삭제했습니다.");
//			return result;
//		}
		
		
		// 글 업데이트
//		@PutMapping("/update-board")
//		public Map<String , Object> updateBoard(
//				HttpSession session,
//				@RequestParam("boardId") int boardId,
//				@RequestParam("type") String type,
//				@RequestParam("subject") String subject,
//				@RequestParam("content") String content,
//				@RequestParam(value= "file" , required = false) MultipartFile file
//				){
//			
//			Map<String , Object> result = new HashMap<>();
//			
//			Integer userId = (Integer)session.getAttribute("id");
//			String loginId = (String)session.getAttribute("loginId");
//			if(userId == null) {
//				result.put("code", 300);
//				result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
//				return result;
//			}
//			
//			int resultCount = classBoardBO.updateClassBoard(boardId, type, userId, loginId, subject, content, file);
//			if (resultCount == -1) {
//				result.put("code", 306);
//				result.put("error_message", "조회된 게시글이 없습니다.");
//				return result;
//			} else if (resultCount == 1) {
//				result.put("code", 301);
//				result.put("error_message", "게시글과 로그인 정보가 일치하지 않습니다.");
//				return result;
//			} else if (resultCount == 0) {
//				result.put("code", 201);
//				result.put("result", "게시글을 수정했습니다.");
//			}
//			
//			return result;
//		}
		
		
		
		
		

}
