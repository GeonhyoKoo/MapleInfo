package com.mapleinfo.mapleApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapleinfo.mapleApp.bo.MapleAppBO;
import com.mapleinfo.mapleApp.domain.MapleCharacterDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/nexon")
@Controller
public class NexonController {

	private final MapleAppBO mapleAppBO;
	
	// 캐릭터 정보 검색
	@GetMapping("/search")
	public String characterSearch(
			Model model,
			@RequestParam("nickname") String nickname
			) {
		
		MapleCharacterDTO characterDTO = mapleAppBO.getMapleCharacterInfo(nickname);
		
		if (characterDTO != null) {
			model.addAttribute("characterDTO" , characterDTO);
		} else {
			model.addAttribute("error_message" , "캐릭터를 찾을 수 없습니다.");
		}
		
		
		return "mapleApi/maple";
	}
	
	
	

	
}
