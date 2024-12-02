package com.mapleinfo.user.bo;

import org.springframework.stereotype.Service;

import com.mapleinfo.user.common.IdValidation;
import com.mapleinfo.user.common.PasswordEncoder;
import com.mapleinfo.user.entity.UserEntity;
import com.mapleinfo.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserBO {

	private final UserRepository userRepository;
	
	/**
	 * 중복확인 
	 * 결과 값 -> int 로 분기
	 * @param loginId
	 * @return
	 */
	public int isDuplicateId(String loginId) {
		
		// validation
		// 정상 0 숫자만 1 특수문자 2 공백 3
		IdValidation vali = new IdValidation();
		int checkCount = vali.isRightId(loginId);
		if(checkCount != 0) {
			return checkCount;
		}
		
		UserEntity user = userRepository.findByLoginId(loginId);
		if (user != null) {
			return -1;
		}
		return 0;
	}
	
	
	/**
	 * 회원가입
	 * 비밀번호 해싱 
	 * @param name
	 * @param birth
	 * @param email
	 * @param phoneNumber
	 * @param loginId
	 * @param password
	 * @return
	 */
	public UserEntity addUser(
			String name, String birth, String email, String phoneNumber , String loginId, String password
			) {
		
		// 비밀번호 암호화
		PasswordEncoder pwEncoder = new PasswordEncoder();
		String hashPassword = pwEncoder.encrypt(loginId, password);
		
		return userRepository.save(UserEntity.builder().name(name)
				.birth(birth).email(email).phoneNumber(phoneNumber).loginId(loginId)
				.password(hashPassword).build());
	}
	
	
	
	/**
	 * 비밀번호 변경을 위해 유저 조회(로그인시)
	 * @param loginId
	 * @param password
	 * @return
	 */
	public UserEntity getUserByLoginIdAndPassword(String loginId, String password) {
		
		PasswordEncoder pwEncoder = new PasswordEncoder();
		String hashPassword = pwEncoder.encrypt(loginId, password);
		
		UserEntity user = userRepository.findByLoginIdAndPassword(loginId, hashPassword);
		return user;
	}
	
	
	/**
	 * 비밀번호 변경 (로그인시)
	 * @param loginId
	 * @param password
	 * @param user
	 * @return
	 */
	public UserEntity updateUserByloginIdAndPasswordAndUserEntity(String loginId, String password , UserEntity user) {
		PasswordEncoder pwEncoder = new PasswordEncoder();
		String hashPassword = pwEncoder.encrypt(loginId, password);
		user = user.toBuilder().password(hashPassword).build();
		userRepository.save(user);
		return user;
	}
	
	
	
	
	/**
	 * 비밀번호 찾기
	 * 이름 , 로그인 아이디 , 생로 사용자조회 -> 이후 변경으로 넘어갈 예정
	 * @param name
	 * @param loginId
	 * @param birth
	 * @return
	 */
	public UserEntity getUserByNameAndLoginIdAndBirth(String name, String loginId, String birth) {
		return userRepository.findByNameAndLoginIdAndBirth(name, loginId, birth);
	}
	
	/**
	 * 비밀번호 변경 (비로그인 시)
	 * 위에서 조회한 데이터가 있을 시에 수행, 변경할 비밀번호로 업데이트
	 * @param loginId
	 * @param password
	 * @return
	 */
	public UserEntity updateUserByLoginIdAndPassword(String loginId , String password) {
		
		UserEntity user = userRepository.findByLoginId(loginId);
		PasswordEncoder pwEncoder = new PasswordEncoder();
		String hashPassword = pwEncoder.encrypt(loginId, password);
		user = user.toBuilder().password(hashPassword).build();
		userRepository.save(user);
		return user;
	}
	
	
	/**
	 * 아이디 찾기 (비로그인시)
	 * 이름 , 생일, 전화번호, 이메일로 사용자 조회
	 * @param name
	 * @param birth
	 * @param phoneNumber
	 * @param email
	 * @return
	 */
	public UserEntity findIdByNameAndBirthAndPhoneNumberAndEmail(String name, String birth, String phoneNumber , String email) {
		return userRepository.findByNameAndBirthAndPhoneNumberAndEmail(name, birth, phoneNumber, email);
	}
	
	
	
	
	
	
}
