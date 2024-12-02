package com.mapleinfo.boss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mapleinfo.boss.domain.Boss;

@Mapper
public interface BossMapper {

	public List<Boss> selectBossBoardByBossName(String bossName);
	
	
}
