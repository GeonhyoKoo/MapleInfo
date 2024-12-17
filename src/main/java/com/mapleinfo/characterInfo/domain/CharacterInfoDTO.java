package com.mapleinfo.characterInfo.domain;

import com.mapleinfo.character.entity.CharacterEntity;
import com.mapleinfo.ranking.domain.Ranking;
import com.mapleinfo.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterInfoDTO {

	public UserEntity user;
	public CharacterEntity character;
	public Ranking rank;
	
	
}
