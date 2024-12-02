package com.mapleinfo.mapleinfo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mapleinfo.character.bo.CharacterBO;
import com.mapleinfo.character.entity.CharacterEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MapleInfoController {

	private final CharacterBO characterBO;
	
	@GetMapping("/mapleinfo")
	public String mapleInfo(
			Model model,
			HttpSession session
			) {

		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			return "mapleinfo/mapleinfo";
		}
		
		CharacterEntity representCharacter = characterBO.getRepresentCharacter(userId);
		if(representCharacter != null) {
			model.addAttribute("character" , representCharacter);
			model.addAttribute("exist", 1);
		} else {
			model.addAttribute("exist", 0);
		}
		
		return "mapleinfo/mapleinfo";
	}
	
	
	
	
}
