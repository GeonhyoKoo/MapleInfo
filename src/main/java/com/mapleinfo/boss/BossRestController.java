package com.mapleinfo.boss;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
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
	
	@PostMapping("/create")
	public Map<String , Object> createBoss(
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
		
		
		return result;
	}
	
}
