package com.mapleinfo.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapleinfo.comment.bo.CommentBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentRestController {

	private final CommentBO commentBO;
	
	/**
	 * 댓글 작성
	 * @param session
	 * @param boardId
	 * @param content
	 * @param type
	 * @return
	 */
	@PostMapping("/create-comment")
	public Map<String , Object> createComment(
			HttpSession session,
			@RequestParam("boardId") int boardId,
			@RequestParam("content") String content,
			@RequestParam(value="type" , required=false) String type
			){
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		String loginId = (String)session.getAttribute("loginId");
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		int resultCount = commentBO.addComment(type, boardId, userId, content);
		
		if (resultCount == 1) {
			result.put("code", 201);
			result.put("result", "댓글 작성에 성공했습니다.");
			return result;
		} else {
			result.put("code", 310);
			result.put("error_message", "댓글 작성에 실패했습니다.");
		}
		return result;
		
	}
	
	
	
	// 댓글 삭제
	@DeleteMapping("/delete/{commentId}")
	public Map<String, Object> deleteComment(
			HttpSession session,
			@PathVariable(name = "commentId") int commentId
			){
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
 		if(userId == null){
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
 		userId = (int)userId;
		
		int resultState = commentBO.deleteComment(userId, commentId);
		if(resultState == -1) {
			result.put("code", 320);
			result.put("error_message", "댓글 삭제 실패했습니다.");
			return result;
		}
		
		result.put("code", 210);
		result.put("result", "댓글 삭제에 성공했습니다.");
		
		
		return result;
		
	}
	
	
}
