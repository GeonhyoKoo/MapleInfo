package com.mapleinfo.character.domain;

import com.mapleinfo.character.entity.CharacterEntity;
import com.mapleinfo.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDTO {

	private UserEntity user;
	private CharacterEntity character;
	
	
	
}
