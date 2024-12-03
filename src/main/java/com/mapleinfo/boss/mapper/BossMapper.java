package com.mapleinfo.boss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mapleinfo.boss.domain.Boss;

@Mapper
public interface BossMapper {

	// 보스 이름으로 게시글 가져오기
	public List<Boss> selectBossBoardByBossName(String bossName);
	
	
}
