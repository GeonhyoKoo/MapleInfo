package com.mapleinfo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mapleinfo.user.bo.UserBO;
import com.mapleinfo.user.entity.UserEntity;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {

	
	private final UserBO userBO;
	
	// 중복확인
	@GetMapping("/is-duplicate-id")
	public Map<String, Object> isDuplicate(
			@RequestParam("loginId") String loginId
			){
		
		int duplicateId = userBO.isDuplicateId(loginId);
		
		Map<String , Object> result = new HashMap<>();
		if (duplicateId == 0) {
			result.put("code", 200);
			result.put("result", "사용 가능");
		} else if (duplicateId == 1) {
			result.put("code", 203);
			result.put("result", "숫자 포함");
		} else if (duplicateId == 2) {
			result.put("code", 205);
			result.put("result", "특수문자 포함");
		} else if (duplicateId == 3) {
			result.put("code", 204);
			result.put("result", "공백 포함");
		} else if (duplicateId == -1) {
			result.put("code", 202);
			result.put("result", "중복된 아이디");
		}
		return result;
	}
	
	
	/**
	 * 회원가입
	 * @param name
	 * @param birth
	 * @param email
	 * @param phoneNumber
	 * @param loginId
	 * @param password
	 * @return
	 */
	@PostMapping("/sign-up")
	public Map<String , Object> signUp(
			@RequestParam("name") String name,
			@RequestParam("birth") String birth,
			@RequestParam("email") String email,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password
			){
		
		
		Map<String, Object> result = new HashMap<>();
		
		UserEntity user =  userBO.addUser(name, birth, email, phoneNumber, loginId, password);
		
		if(user != null) {
			result.put("code", 201);
			result.put("result", "회원가입 성공");
			return result;
		} else {
			result.put("code", 310);
			result.put("error_message", "회원가입에 실패했습니다.");
			return result;
		}
	}
	
	
	/**
	 * 로그인
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/sign-in")
	public Map<String , Object> signIn(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session
			){
		
		Map<String , Object> result = new HashMap<>();
		
		UserEntity user = userBO.getUserByLoginIdAndPassword(loginId, password);
		
		if(user == null) {
			result.put("code", 306);
			result.put("error_message", "조회된 사용자가 없습니다.");
			return result;
		}
		
		result.put("code", 200);
		result.put("result", "로그인 성공");
		
		session.setAttribute("loginId", user.getLoginId());
		session.setAttribute("id", user.getId());
		
		return result;
	}
	
	
	
	/**
	 * 비밀번호 변경 (로그인 시) 계정 조회
	 * @param loginId
	 * @param password
	 * @param session
	 * @return
	 */
	@PostMapping("/find-user")
	public Map<String , Object> findUser(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			HttpSession session
			){
		Map<String , Object> result = new HashMap<>();
		UserEntity user = userBO.getUserByLoginIdAndPassword(loginId, password);
		
		Integer id = (Integer)session.getAttribute("id");
		if (id == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 사용 가능합니다.");
			return result;
		}
		if(user == null) {
			result.put("code", 306);
			result.put("error_message", "조회된 사용자가 없습니다.");
			return result;
		}
		result.put("code", 200);
		result.put("result", "조회 성공");
		return result;
	}
	
	
	/**
	 * 비밀번호 변경 (로그인 시)
	 * @param loginId
	 * @param password
	 * @param prePassword
	 * @param session
	 * @return
	 */
	@PutMapping("/change-pw-bypw")
	public Map<String ,Object> changePwByPw(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password,
			@RequestParam("prePassword") String prePassword,
			HttpSession session
			){
		
		Map<String , Object> result = new HashMap<>();
		
		Integer id = (Integer)session.getAttribute("id");
		if (id == null) {
			result.put("code", 300);
			result.put("error_message", "로그인 후 사용 가능합니다.");
			return result;
		}
		
		UserEntity user = userBO.getUserByLoginIdAndPassword(loginId, prePassword);
		if(user == null) {
			result.put("code", 306);
			result.put("error_message", "조회된 사용자가 없습니다.");
			return result;
		}
		UserEntity updateUser = userBO.updateUserByloginIdAndPasswordAndUserEntity(loginId, password, user);
		if (updateUser == null) {
			result.put("code", 315);
			result.put("error_message", "비밀번호 변경에 실패했습니다.");
			return result;
		}
		
		result.put("code", 206);
		result.put("result", "비밀번호 변경에 성공했습니다. 새로운 비밀번호로 로그인 해주세요.");
		return result;
	}
	
	
	// 비밀번호 변경을 위한 사용자 조회 (비로그인 시)
	@PostMapping("/find-password")
	public Map<String , Object> findPassword(
			@RequestParam("name") String name,
			@RequestParam("loginId") String loginId,
			@RequestParam("birth") String birth
			){
		
		Map<String , Object> result = new HashMap<>();
		
		UserEntity user = userBO.getUserByNameAndLoginIdAndBirth(name, loginId, birth);
		if(user == null) {
			result.put("code", 305);
			result.put("error_message", "조회하신 사용자의 정보가 없습니다.");
			return result;
		}
		
		result.put("code", 200);
		result.put("result", "사용자 정보 조회 성공");
		
		return result;
	}
	
	
	// 비밀번호 변경 (비로그인 시) 변경
	@PutMapping("/find-pw-change")
	public Map<String, Object> findPasswordAndChange(
			@RequestParam("loginId") String loginId,
			@RequestParam("password") String password
			){
		
		Map<String , Object> result = new HashMap<>();
		UserEntity user  = userBO.updateUserByLoginIdAndPassword(loginId, password);
		if (user == null) {
			result.put("code", 315);
			result.put("error_message", "비밀번호 변경에 실패했습니다.");
			return result;
		}
		
		result.put("code", 206);
		result.put("result", "비밀번호 변경에 성공했습니다. 새로운 비밀번호로 로그인 해주세요.");
		return result;
		
	}
	
	
	
	/**
	 * 아이디 찾기
	 * @param name
	 * @param birth
	 * @param phoneNumber
	 * @param email
	 * @return
	 */
	@PostMapping("/find-id")
	public Map<String, Object> findId(
			@RequestParam("name") String name,
			@RequestParam("birth") String birth,
			@RequestParam("phoneNumber") String phoneNumber,
			@RequestParam("email") String email
			){
		
		Map<String , Object> result = new HashMap<>();
		
		UserEntity user = userBO.findIdByNameAndBirthAndPhoneNumberAndEmail(name, birth, phoneNumber, email);
		if(user == null) {
			result.put("code", 305);
			result.put("error_message", "조회하신 사용자의 정보가 없습니다.");
			return result;
		}
		result.put("code", 200);
		String userResponse = user.getName() + "님의\n 아이디는 " + user.getLoginId() + "입니다.";
		result.put("result", userResponse);
		return result;
	}
	
	
	
	
	
	
}
