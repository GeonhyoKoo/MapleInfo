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
	
	
	
	
	
}
