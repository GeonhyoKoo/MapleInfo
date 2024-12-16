package com.mapleinfo.ranking.bo;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.mapleinfo.ranking.domain.Ranking;
import com.mapleinfo.ranking.mapper.RankingMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RankingBO {

	private final RankingMapper rankingMapper;
	
	
	// 일주일 마다 랭킹을 등록
	public void addRanking() {
		rankingMapper.insertRanking();
	}
	
	
	// 랭킹 가져오기 (오늘 날짜로 <= endStandard) 일때까지
	public Ranking getRanking(int characterId , LocalDate today) {
		return rankingMapper.selectRankingByCharacterIdAndDate(characterId, today);
	}
	
	
	
}
