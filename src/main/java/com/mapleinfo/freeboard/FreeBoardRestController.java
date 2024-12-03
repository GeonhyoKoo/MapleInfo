package com.mapleinfo.freeboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mapleinfo.freeboard.bo.FreeBoardBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/freeboard")
@RequiredArgsConstructor
public class FreeBoardRestController {

	private final FreeBoardBO freeBoardBO;
	
	
	// 게시글 작성
	@PostMapping("/create-board")
	public Map<String , Object> createBoard(
			HttpSession session,
			@RequestParam("userId") int id,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam(value= "file" , required = false) MultipartFile file
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		String loginId = (String)session.getAttribute("loginId");
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		if (id != userId) {
			result.put("code" , 301);
			result.put("error_message", "계정 정보가 일치하지 않습니다.");
			return result;
		}
		
		int resultCount = freeBoardBO.addFreeBoard(id, loginId,  subject, content, file);
		if (resultCount == 1) {
			result.put("code" , 201);
			result.put("result", "게시글 작성에 성공했습니다.");
		} else {
			result.put("code", 310);
			result.put("error_message", "게시글 작성에 실패했습니다.");
		}
		
		
		return result;
	}
	
	
}
