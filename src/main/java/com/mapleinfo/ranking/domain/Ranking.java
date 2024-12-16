package com.mapleinfo.ranking.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ranking {

	private int id;
	private int characterId;
	private int rank;
	private LocalDate startStandard;
	private LocalDate endStandard;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
}
