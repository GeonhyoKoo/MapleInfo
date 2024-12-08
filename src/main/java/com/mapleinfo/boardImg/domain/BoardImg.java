package com.mapleinfo.boardImg.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardImg {

	private int id;
	private String type;
	private int boardId;
	private String imagePath;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
}
