package com.mapleinfo.character;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mapleinfo.character.bo.CharacterBO;
import com.mapleinfo.character.entity.CharacterEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/character")
public class CharacterRestController {

	private final CharacterBO characterBO;
	
	// 캐릭터 중복확인
	@GetMapping("/is-duplicate")
	public Map<String , Object> isDuplicateCharacter(
			@RequestParam("name") String name,
			HttpSession session
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		CharacterEntity character =  characterBO.getCharacterByName(name);
		if (character == null) {
			result.put("code", 200);
			result.put("result", "중복된 닉네임이 없습니다.");
			return result;
		} else {
			result.put("code", 202);
			result.put("error_message", "이미 등록된 닉네임입니다.");
		}
		
		return result;
	}

	
	/**
	 * 캐릭터 추가
	 * @param name
	 * @param characterClass
	 * @param world
	 * @param level
	 * @param stat
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/create-character")
	public Map<String , Object> createCharacter(
			@RequestParam("name") String name,
			@RequestParam("characterClass") String characterClass,
			@RequestParam("world") String world,
			@RequestParam("level") int level,
			@RequestParam("stat") String stat,
			@RequestParam("file") MultipartFile file,
			HttpSession session
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		String loginId = (String)session.getAttribute("loginId");
		
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		CharacterEntity character = characterBO.addCharacter(name, characterClass, world, level, stat, file, userId, loginId);
		
		if (character == null) {
			result.put("code", 310);
			result.put("error_message", "캐릭터 추가에 실패했습니다.");
			return result;
		}
		
		result.put("code", 201);
		result.put("result", "캐릭터 추가에 성공했습니다.");
		
		return result;
	}
	
	
	/**
	 * 캐릭터 추가 전 DB 조회 -> 닉네임은 유일해야함.
	 * @param id
	 * @param session
	 * @return
	 */
	@GetMapping("/before-change-character")
	public Map<String , Object> beforeUpdateCharacter(
			@RequestParam("characterId") Integer id,
			HttpSession session
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		if (id == null) {
			result.put("code", 302);
			result.put("error_message", "캐릭터 정보가 없습니다.");
			return result;
		}
		
		int characterId = (int)id;
		
		CharacterEntity character = characterBO.getCharacterByCharacterId(characterId);
		
		if (character == null) {
			result.put("code", 305);
			result.put("error_message", "조회된 캐릭터가 없습니다.");
			return result;
		}
		
		result.put("code", 200);
		result.put("result", "해당 캐릭터를 조회 성공했습니다.");
		return result;
	}
	
	
	
	/** 
	 * 캐릭터 업데이트
	 * @param characterId
	 * @param name
	 * @param level
	 * @param stat
	 * @param file
	 * @param session
	 * @return
	 */
	@PostMapping("/update-character")
	public Map<String , Object> updateCharacter(
			@RequestParam("characterId") int characterId,
			@RequestParam("name") String name,
			@RequestParam("level") int level,
			@RequestParam("stat") String stat,
			@RequestParam(value= "file" , required = false) MultipartFile file,
			HttpSession session
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		String loginId = (String)session.getAttribute("loginId");
		
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		
		CharacterEntity character = characterBO.updateCharacter(characterId, name, level, stat, file , userId, loginId);
		
		if (character == null) {
			result.put("code", 305);
			result.put("error_message", "조회된 캐릭터가 없습니다.");
			return result;
		}
		
		result.put("code", 206);
		result.put("result", "캐릭터 정보 수정에 성공했습니다");
		
		return result;
	}
	
	
	
	// 캐릭터 삭제
	// TODO 대표 캐릭터가 삭제되는 경우 예외처리 
	@DeleteMapping("/delete-character")
	public Map<String , Object> deleteCharacter(
			@RequestParam("characterId") int characterId,
			HttpSession session
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		String loginId = (String)session.getAttribute("loginId");
		
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		
		int resultState = characterBO.deleteCharacter(characterId, userId , loginId);
		
		if (resultState == -1 ) {
			result.put("code", 305);
			result.put("error_message", "캐릭터 조회에 실패했습니다.");
			return result;
		} else if (resultState == -2 ) {
			result.put("code", 306);
			result.put("error_message", "캐릭터 이미지 정보를 불러오는데에 실패했습니다.");
			return result;
		} else {
			result.put("code", 210);
			result.put("result", "삭제 성공했습니다.");
			return result;
		}
		
	}
	
	
	// 대표 캐릭터 변경하기
	@GetMapping("/designate-represent-character")
	public Map<String , Object> designateRepresent(
			HttpSession session,
			@RequestParam("characterId") Integer requestCharacterId
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 이용 가능한 서비스입니다. 로그인 해주세요.");
			return result;
		}
		
		if(requestCharacterId == null) {
			result.put("code", 302);
			result.put("error_message", "캐릭터의 정보가 비었습니다. 다시 시도해주세요.");
			return result;
		}
		
		int characterId = (int)requestCharacterId;
		
		int resultState = characterBO.updateRepresentCharacter(userId , characterId);
		
		if (resultState == -1) {
			result.put("code", 305);
			result.put("error_message", "등록된 캐릭터가 존재하지 않습니다.");
			return result;
		} else if (resultState == 0) {
			result.put("code", 306);
			result.put("error_message", "대표로 등록된 캐릭터가 존재하지 않습니다.");
			return result;
		} else if (resultState == 1) {
			result.put("code", 206);
			result.put("result", "대표 캐릭터를 변경했습니다.");
			return result;
		}
		
		return result;
	}
	
	
	
}
