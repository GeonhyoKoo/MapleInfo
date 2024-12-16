package com.mapleinfo.batch;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mapleinfo.ranking.bo.RankingBO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BatchScheduler {

	private final RankingBO rankingBO;
	
	
	
	// 매주 목요일 마다 실행
	@Scheduled(cron = "0 0 0 * * THU")
	public void insertRankJob() {
		rankingBO.addRanking();
		log.info("####랭킹 등록##### " + System.currentTimeMillis());
	}
	
	
	// 테스트
//	@Scheduled(cron = "0 0/1 * * * *")
//	public void testBatch() {
//		rankingBO.addRanking();
//		log.info("###실행 ##### " + System.currentTimeMillis());
//	}
	
	
}
