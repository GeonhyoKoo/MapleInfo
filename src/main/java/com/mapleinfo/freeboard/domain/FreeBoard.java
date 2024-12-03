package com.mapleinfo.freeboard.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FreeBoard {

	private int id;
	private int userId;
	private String subject;
	private String content;
	private String imagePath;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
