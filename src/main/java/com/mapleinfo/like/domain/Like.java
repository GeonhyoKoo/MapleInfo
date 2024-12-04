package com.mapleinfo.like.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Like {

	
	private String type;
	private int boardId;
	private int userId;
	private LocalDateTime createdAt;
}
