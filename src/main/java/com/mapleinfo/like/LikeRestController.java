package com.mapleinfo.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapleinfo.like.bo.LikeBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LikeRestController {

	private final LikeBO likeBO;
	
	@PostMapping("/like")
	public Map<String ,Object> likeToggle(
			HttpSession session,
			@RequestParam("type") String type,
			@RequestParam("boardId") int boardId
			){
		
		Map<String, Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		int resultState = likeBO.likeToggle(type , boardId, userId);
		result.put("code", 206);
		if (resultState == 1) {
			result.put("result", "좋아요 성공");
		}
		if (resultState == 2) {
			result.put("result", "좋아요 삭제");
		}
		
		return result;
	}
	
	
	
}
