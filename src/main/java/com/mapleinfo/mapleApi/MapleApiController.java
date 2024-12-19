package com.mapleinfo.mapleApi;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mapleinfo.mapleApi.bo.MapleApiBO;
import com.mapleinfo.mapleApi.domain.MapleCharacterDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/maple")
@Controller
public class MapleApiController {

	private final MapleApiBO mapleApiBO;
	
	// 캐릭터 정보 검색
	@PostMapping("/search")
	public String characterSearch(
			Model model,
			@RequestParam("nickname") String nickname
			) {
		
		MapleCharacterDTO characterDTO = mapleApiBO.getMapleCharacterInfo(nickname);
		
		if (characterDTO != null) {
			model.addAttribute("characterDTO" , characterDTO);
			return "redirect:/maple/detail-view";
		} else {
			model.addAttribute("error_message" , "캐릭터를 찾을 수 없습니다.");
		}
		
		return "mapleinfo/mapleinfo";
	}
	
	
	
	// detail view
	@GetMapping("/detail-view")
	public String characterDetail(Model model) {
		
	    MapleCharacterDTO characterDTO = (MapleCharacterDTO) model.getAttribute("characterDTO");

	    model.addAttribute("characterDTO", characterDTO);  
	    return "mapleApi/maple";  
	}

	
}
