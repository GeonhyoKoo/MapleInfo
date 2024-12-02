package com.mapleinfo.character.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mapleinfo.character.entity.CharacterEntity;

public interface CharacterRepository extends JpaRepository<CharacterEntity, Integer>{

	
	// 캐릭터 닉네임으로 정보 조회
	public CharacterEntity findByName(String name);
	
	// 대표 캐릭터 조회
	public CharacterEntity findByUserIdAndRepresentCharacter(int userId, boolean represent);
	
	// 유저의 캐릭터 목록 가져오기, 등록 여부 가리기 위해서도 사용 , 아래를 포함
	public List<CharacterEntity> findByUserId(int userId);
	// 유저가 등록한 캐릭터 있는지 정보 조회
	//public CharacterEntity findByUserId(int userId);
	
	// 캐릭터 id 로 조회
	public CharacterEntity findById(int characterId);
	
}
