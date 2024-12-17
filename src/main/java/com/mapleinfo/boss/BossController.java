package com.mapleinfo.boss;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapleinfo.boss.bo.BossBO;
import com.mapleinfo.boss.common.ConverterBossNameToImg;
import com.mapleinfo.boss.domain.Boss;
import com.mapleinfo.boss.domain.BossDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/boss")
@RequiredArgsConstructor
public class BossController {

	private final BossBO bossBO;
	
	// 보스 메인 , 보스 선택 페이지
	@GetMapping("/main")
	public String bossMain() {
		return "boss/bossMain";
	}
	
	// 보스 선택 후 게시판
	@GetMapping("/board")
	public String bossBoard(
			Model model,
			@RequestParam(value="bossName" , required = false) String bossName
			) {
		
		List<Boss> bossBoard = bossBO.getBossBoardByBossName(bossName);
		if(bossBoard.isEmpty()) {
			bossBoard = null;
		}
		
		ConverterBossNameToImg cbi = new ConverterBossNameToImg();
		String bossImg = cbi.CovertBossName(bossName);
		
		model.addAttribute("bossBoard", bossBoard);
		model.addAttribute("bossName", bossName);
		model.addAttribute("bossImg", bossImg);
		
		return "boss/bossBoard";
	}
	
	
	// 보스 게시판 글쓰기
	@GetMapping("/create-view")
	public String bossCreateView(
			Model model,
			@RequestParam(value="bossName" , required = false) String bossName,
			HttpSession session
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		if (userId == null) {
			return "user/signIn";
		}
		
		model.addAttribute("userId" , userId);
		model.addAttribute("bossName", bossName);
		
		return "boss/createBoss";
	}
	
	
	// 보스 게시판 상세 내용보기
	@GetMapping("/detail-view")
	public String bossDetailView(
			Model model,
			@RequestParam("boardId") int boardId,
			HttpSession session
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		if (userId == null) {
			return "user/signIn";
		}
		
		
		BossDTO bossDTO = bossBO.getBossBoardByBossId(boardId , userId);
		
		model.addAttribute("board" , bossDTO);
		model.addAttribute("userId" , userId);
		return "boss/bossDetail";
	}
	
	
	// 보스 게시판 수정하기
	@GetMapping("/update-view")
	public String bossUpdateView(
			@RequestParam("bossId") int bossId,
			HttpSession session,
			Model model
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		if (userId == null) {
			return "user/signIn";
		}
		
		BossDTO bossDTO = bossBO.getBossBoardByBossId(bossId , userId);
		model.addAttribute("board" , bossDTO);
		model.addAttribute("userId" , userId);
		return "boss/bossUpdate";
	}
	
	
	
	
}
