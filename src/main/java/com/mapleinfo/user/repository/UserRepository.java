package com.mapleinfo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mapleinfo.user.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity , Integer>{

	// 아이디 중복확인
	public UserEntity findByLoginId(String loginId);
	
	// 로그인
	public UserEntity findByLoginIdAndPassword(String loginId, String password);
	
	// 아이디 찾기
	public UserEntity findByNameAndBirthAndPhoneNumberAndEmail(String name, String birth, String phoneNumber , String email);
	
	// 비밀번호 찾기 -> 유저 정보 조회
	public UserEntity findByNameAndLoginIdAndBirth(String name, String loginId, String Birth);
	
}
