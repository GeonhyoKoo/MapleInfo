package com.mapleinfo.boss.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mapleinfo.boss.domain.Boss;
import com.mapleinfo.boss.mapper.BossMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BossBO {

	private final BossMapper bossMapper;
	
	/**
	 * 보스 이름으로 게시글 가져오기
	 * @param bossName
	 * @return
	 */
	public List<Boss> getBossBoardByBossName(String bossName){
		return bossMapper.selectBossBoardByBossName(bossName);
	}
	
	
	// bossBoard insert
	public int addBossBoard(String bossName, int userId, String world, String state, 
			String recommendStat, int memberLimit, String subject, String content) {
		
		int presentMember = 1;
		// boss 게시판에 쌓기
		int resultCount = bossMapper.insertBossBoard(bossName, userId, world, state,
				recommendStat, presentMember, memberLimit, subject, content);
		
		// boss 멤버에 쌓
		
		return resultCount;
	}
	
	
}
