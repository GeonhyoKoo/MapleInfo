package com.mapleinfo.character;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mapleinfo.character.bo.CharacterBO;
import com.mapleinfo.character.entity.CharacterEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/character")
@Controller
public class CharacterController {


	private final CharacterBO characterBO;
	
	/**
	 * 정보 수정 페이지
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/change-view")
	public String change(
			HttpSession session,
			Model model
			) {

		Integer userId = (Integer)session.getAttribute("id"); 
		if(userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		
		List<CharacterEntity> characterList =  characterBO.getCharacterList(userId);
		model.addAttribute("characterList" , characterList);
		
		
		return "character/change";
	}
	
	
	/**
	 * 캐릭터 추가 페이지
	 * @param session
	 * @return
	 */
	@GetMapping("/create-character-view")
	public String createCharacter(
			HttpSession session
			) {
		
		Integer userId = (Integer)session.getAttribute("id"); 
		if(userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		return "character/createCharacter";
	}
	
	/**
	 * 캐릭터 정보 수정
	 * @param session
	 * @param model
	 * @param characterId
	 * @return
	 */
	@GetMapping("/update-character-view/{characterId}")
	public String updateCharacter(
			HttpSession session,
			Model model,
			@PathVariable(name = "characterId") Integer characterId
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		String loginId = (String)session.getAttribute("loginId");
		
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		CharacterEntity character = characterBO.getCharacterByCharacterId(characterId);
		model.addAttribute("character", character);
		return "character/updateCharacter";
	}
	
	
	// 내 캐릭터 조회
	// 대표 캐릭터 변경
	@GetMapping("/my-character-list-view")
	public String myCharacterList(
			HttpSession session,
			Model model
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		if (userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		List<CharacterEntity> characterList = characterBO.getCharacterList(userId);
		if (characterList.isEmpty()) {
			model.addAttribute("exist", 0);
			return "character/myCharacterList";
		}
		
		CharacterEntity representCharacter = characterBO.getRepresentCharacter(userId);
		if (representCharacter == null) {
			model.addAttribute("exist", -1);
			model.addAttribute("characterList" , characterList);
			return "character/myCharacterList";
		}
		
		model.addAttribute("exist", 1);
		model.addAttribute("characterList" , characterList);
		model.addAttribute("representCharacter" , representCharacter);
		return "character/myCharacterList";
		
	}
	
	
	
}
