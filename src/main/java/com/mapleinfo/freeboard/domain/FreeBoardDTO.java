package com.mapleinfo.freeboard.domain;

import com.mapleinfo.user.entity.UserEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeBoardDTO {
	
	private UserEntity user;
	
	private FreeBoard freeboard;
	
	
}
