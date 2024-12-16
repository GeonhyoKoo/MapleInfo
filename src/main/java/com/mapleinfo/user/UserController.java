package com.mapleinfo.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mapleinfo.character.bo.CharacterBO;
import com.mapleinfo.character.domain.CharacterDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user")
public class UserController {

	private final CharacterBO characterBO;
	
	
	/**
	 * 로그인
	 * @return
	 */
	@GetMapping("/sign-in-view")
	public String signIn() {
		return "user/signIn";
	}
	
	/**
	 * 회원가입
	 * @return
	 */
	@GetMapping("/sign-up-view")
	public String signUp() {
		return "user/signUp";
	}
	
	/**
	 * 정보 수정하기
	 * @return
	 */
	@GetMapping("/myinfo")
	public String myInfo(
			HttpSession session,
			Model model
			) {
		
		Integer userId = (Integer)session.getAttribute("id");
		if(userId == null) {
			return "redirect:/user/sign-in-view";
		}
		
		CharacterDTO character = characterBO.getRepresentCharacter(userId);
		if(character != null) {
			model.addAttribute("character" , character);
			model.addAttribute("exist", 1);
		} else {
			model.addAttribute("exist", 0);
		}
		
		return "user/myInfo";
	}
	
	/**
	 * 로그아웃
	 * @param session
	 * @return
	 */
	@GetMapping("/sign-out")
	public String signOut(
			HttpSession session
			) {
		session.removeAttribute("loginId");
		session.removeAttribute("id");
		return "redirect:/user/sign-in-view";
	}
	
	/**
	 * 아이디 찾기
	 * @param session
	 * @return
	 */
	@GetMapping("/find-id-view")
	public String findId(
			HttpSession session
			) {
		
		//로그인 되어있으면 -> 메인페이지
		Integer id = (Integer)session.getAttribute("id"); 
		if(id != null) {
			return "redirect:/mapleinfo";
		}
		return "user/findId";
	}
	
	
	/**
	 * 비밀번호 변경 (로그인 시)
	 * @param session
	 * @return
	 */
	@GetMapping("/change-pw-bypw-view")
	public String changePwBypw(
			HttpSession session
			) {
		
		//로그인 안되어있으면 -> 로그인 페이지
		Integer id = (Integer)session.getAttribute("id"); 
		if(id == null) {
			return "redirect:/user/sign-in-view";
		}
		
		return "user/changePwByPw";
	}
	
	// 비밀번호 찾기
	@GetMapping("/find-pw-view")
	public String findPw(
			HttpSession session
			) {
		
		//로그인 되어있으면 -> 메인페이지
		Integer id = (Integer)session.getAttribute("id"); 
		if(id != null) {
			return "redirect:/mapleinfo";
		}
		return "user/findPw";
		
	}
	
	// 비밀번호 찾기 -> 이름 , 아이디 , 생년월일 정보 확인 후 변경 페이지
	@GetMapping("/find-pw-detail-view/{loginId}")
	public String findPwDetail(
			Model model,
			@PathVariable(name = "loginId") String loginId
			) {
		model.addAttribute("loginId", loginId);
		return "user/findPwDetail";
	}
	
}
