package com.mapleinfo.classboard.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassBoard {

	private int id;
	private int userId;
	private String characterClass;
	private String subject;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
}
