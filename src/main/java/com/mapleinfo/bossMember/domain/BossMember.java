package com.mapleinfo.bossMember.domain;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BossMember {

	
	private int id;
	private int bossId;
	private int userId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
