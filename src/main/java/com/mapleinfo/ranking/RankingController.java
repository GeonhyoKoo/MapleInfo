package com.mapleinfo.ranking;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mapleinfo.characterInfo.bo.CharacterInfoBO;
import com.mapleinfo.characterInfo.domain.CharacterInfoDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/ranking")
@RequiredArgsConstructor
public class RankingController {

	
	private final CharacterInfoBO characterInfoBO;
	
	@GetMapping("/ranking-view")
	public String rankingView(
			Model model
			) {
		
		List<CharacterInfoDTO> rankingDTOList = characterInfoBO.getRankingList();
		
		model.addAttribute("ranking" , rankingDTOList);
		
		return "ranking/ranking";
	}
	
	
	
}
