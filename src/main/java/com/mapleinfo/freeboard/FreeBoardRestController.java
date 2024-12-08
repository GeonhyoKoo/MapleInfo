package com.mapleinfo.freeboard;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	
	// 글 삭제
	@DeleteMapping("/delete/{boardId}")
	public Map<String , Object> deleteBoard(
			HttpSession session,
			@PathVariable(name = "boardId") int boardId
			){
		
		Map<String , Object> result = new HashMap<>();
		Integer userId = (Integer)session.getAttribute("id");
		String loginId = (String)session.getAttribute("loginId");
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		int resultCount = freeBoardBO.deleteFreeBoard(userId , loginId , boardId);
		if (resultCount == -1) {
			result.put("code", 320);
			result.put("error_message", "등록된 게시글이 존재하지 않습니다.");
			return result;
		}
		
		result.put("code", 210);
		result.put("result", "게시글을 삭제했습니다.");
		return result;
	}
	
	
	// 글 업데이트
	@PutMapping("/update-board")
	public Map<String , Object> updateBoard(
			HttpSession session,
			@RequestParam("boardId") int boardId,
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
		
		int resultCount = freeBoardBO.updateFreeBoard(boardId, userId, loginId, subject, content, file);
		if (resultCount == -1) {
			result.put("code", 306);
			result.put("error_message", "조회된 게시글이 없습니다.");
			return result;
		} else if (resultCount == 1) {
			result.put("code", 301);
			result.put("error_message", "게시글과 로그인 정보가 일치하지 않습니다.");
			return result;
		} else if (resultCount == 0) {
			result.put("code", 201);
			result.put("result", "게시글을 수정했습니다.");
		}
		
		return result;
	}
	
	
	// 다중 파일 테스트
	@PostMapping("/test")
	public Map<String, Object> test(
			HttpSession session,
			@RequestParam("subject") String subject,
			@RequestParam(name = "files[]", required = false) MultipartFile[] files
			){
		
		Map<String , Object> result = new HashMap<>();
		
		 for(MultipartFile file : files) {
	            // 파일 처리 로직
	            System.out.println("파일 이름: " + file.getOriginalFilename());
	            System.out.println("파일 이름: " + file);
	            // 파일 저장하거나 기타 필요한 작업을 수행
		 }
		 
		return result;
	}
	
	
}
