package com.mapleinfo.boss;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapleinfo.boss.bo.BossBO;
import com.mapleinfo.boss.domain.Boss;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/boss")
@RequiredArgsConstructor
public class BossController {

	private final BossBO bossBO;
	
	@GetMapping("/main")
	public String bossMain() {
		return "boss/bossMain";
	}
	
	
	
	
	@GetMapping("/board")
	public String bossBoard(
			Model model,
			@RequestParam(value="bossName" , required = false) String bossName
			) {
		
		List<Boss> bossBoard = bossBO.getBossBoardByBossName(bossName);
		if(bossBoard.isEmpty()) {
			bossBoard = null;
		}
		
		model.addAttribute("bossBoard", bossBoard);
		model.addAttribute("bossName", bossName);
		
		return "boss/bossBoard";
	}
	
	
	
	
	
	
	
}
