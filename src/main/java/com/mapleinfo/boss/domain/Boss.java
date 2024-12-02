package com.mapleinfo.boss.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Boss {
	
	private int id;
	private String bossName;
	private int userId;
	private String world;
	private String state;
	private String recommendStat;
	private int memberLimit;
	private String subject;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	
}
