package com.mapleinfo.ranking.domain;

import com.mapleinfo.character.entity.CharacterEntity;
import com.mapleinfo.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RankingDTO {

	
	public UserEntity user;
	public Ranking ranking;
	public CharacterEntity character;
	
}
