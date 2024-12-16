package com.mapleinfo.ranking.mapper;

import java.time.LocalDate;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mapleinfo.ranking.domain.Ranking;

@Mapper
public interface RankingMapper {

	
	// 랭킹에 일주일마다 담기
	public void insertRanking();
	
	
	// 랭킹 가져오기(characterId, Date로)
	public Ranking selectRankingByCharacterIdAndDate(
			@Param("characterId") int characterId, 
			@Param("today") LocalDate today
			);
	
}
