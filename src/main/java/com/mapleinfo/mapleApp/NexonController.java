package com.mapleinfo.mapleApp;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/nexon")
@Controller
public class NexonController {

	
	
	// 캐릭터 정보 검색
	@RequestMapping("/search")
	public String characterSearch(
			Model model,
			@RequestParam("nickname") String nickname
			) {
		
		
		
		return "mapleApi/maple";
	}
	
	
	
	
	//
//	
//	 private final NexonApiService nexonApiService;
//
//	    public MapleController(NexonApiService nexonApiService) {
//	        this.nexonApiService = nexonApiService;
//	    }
//
//	    // 닉네임을 입력받아 캐릭터 정보와 장비 정보를 조회
//	    @GetMapping("/search")
//	    public String searchCharacter(@RequestParam String nickname, Model model) {
//	        // API 호출을 통해 캐릭터 정보와 장비 정보 가져오기
//	        CharacterInfo characterInfo = nexonApiService.getCharacterInfoByNickname(nickname);
//
//	        // 모델에 데이터 전달
//	        model.addAttribute("characterInfo", characterInfo);
//
//	        // 결과를 보여줄 뷰로 리다이렉트
//	        return "characterSearch";
//	    }
	
	
	
}
