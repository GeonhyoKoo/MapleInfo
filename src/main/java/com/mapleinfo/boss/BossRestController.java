package com.mapleinfo.boss;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapleinfo.boss.bo.BossBO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/boss")
@RequiredArgsConstructor
public class BossRestController {

	private final BossBO bossBO;
	
	
	// 게시물 작성
	@PostMapping("/create")
	public Map<String , Object> createBossBoard(
			HttpSession session,
			@RequestParam("bossName") String bossName,
			@RequestParam("world") String world,
			@RequestParam("state") String state,
			@RequestParam("recommendStat") String recommendStat,
			@RequestParam("memberLimit") int memberLimit,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam("id") int id
			){
		
		
		Map<String , Object> result = new HashMap<>();
		
		
		Integer userId = (Integer)session.getAttribute("id");
		String loginId = (String)session.getAttribute("loginId");
		
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		if (userId != id) {
			result.put("code", 301);
			result.put("error_message", "로그인 정보가 일치하지 않습니다.");
			return result;
		}
		userId = (int)userId;
		// insert
		int resultCount = bossBO.addBossBoard(bossName, userId, world, state, recommendStat, memberLimit, subject, content);
		if (resultCount == -1) {
			result.put("code", 310);
			result.put("error_message", "게시물 작성에 실패했습니다.");
			return result;
		}
		
		result.put("code", 201);
		result.put("result", "게시물 작성에 성공했습니다.");
		
		return result;
	}
	
	
	
	// 게시물 수정하기
	@PutMapping("/update")
	public Map<String , Object> updateBossBoard(
			HttpSession session,
			@RequestParam("state") String state,
			@RequestParam("recommendStat") String recommendStat,
			@RequestParam("subject") String subject,
			@RequestParam("content") String content,
			@RequestParam("bossId") int bossId
			){
		
		Map<String , Object> result = new HashMap<>();
		
		
		Integer userId = (Integer)session.getAttribute("id");
		
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		userId = (int)userId;
		int resultCount = bossBO.updateBossBoard(bossId, userId, state, recommendStat, subject, content);
		if(resultCount == -1) {
			result.put("code", 301);
			result.put("error_message", "작성자의 글이 아닙니다.");
			return result;
		}
		
		if(resultCount > 0) {
			result.put("code", 206);
			result.put("result", "수정되었습니다.");
			return result;
		} else {
			result.put("code", 315);
			result.put("error_message", "글 수정에 실패했습니다.");
			return result;
		}
	}
	
	
	
	// 게시물 삭제하기
	@DeleteMapping("/delete")
	public Map<String , Object> deleteBossBoard(
			HttpSession session,
			@RequestParam("bossId") int bossId
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		int resultCount = bossBO.deleteBossBoard(bossId, userId);
		if (resultCount == -1) {
			result.put("code", 301);
			result.put("error_message", "사용자와 작성자가 일치하지 않습니다.");
			return result;
		} else if (resultCount == 0) {
			result.put("code", 320);
			result.put("error_message", "게시물 삭제에 실패했습니다.");
			return result;
		}
		
		
		result.put("code", 210);
		result.put("result", "게시물을 삭제했습니다.");
		
		return result;
		
	}
	
	
	
	// 게시물 지원하기
	@PostMapping("/apply")
	public Map<String , Object> applyBossBoard(
			HttpSession session,
			@RequestParam("userId") int userId,
			@RequestParam("bossId") int bossId
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer id = (Integer)session.getAttribute("id");
		
		if(id == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		} if (id != userId) {
			result.put("code", 301);
			result.put("error_message", "계정 정보가 일치하지 않습니다. 다시 로그인 해주세요.");
		}
	
		int resultCount = bossBO.applyBossBoard(bossId, userId);
		
		if(resultCount == -1) {
			result.put("code", 310);
			result.put("error_message", "파티 모집 인원이 가득 찼습니다.");
			return result;
		}
		
		
		
		result.put("code", 200);
		result.put("result", "지원에 성공했습니다.");
		return result;
	}
	
}
