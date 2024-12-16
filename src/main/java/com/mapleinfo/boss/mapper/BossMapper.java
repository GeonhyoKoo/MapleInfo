package com.mapleinfo.boss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mapleinfo.boss.domain.Boss;

@Mapper
public interface BossMapper {

	// 보스 이름으로 게시글 가져오기
	public List<Boss> selectBossBoardByBossName(String bossName);
	
	// 보스 게시물 작성
	public int insertBossBoard(
			@Param("bossName") String bossName,
			@Param("userId") int userId,
			@Param("world") String world,
			@Param("state") String state,
			@Param("recommendStat") String recommendStat,
			@Param("presentMember") int  presentMember,
			@Param("memberLimit") int memberLimit,
			@Param("subject") String subject,
			@Param("content") String content
			);
	
	
}
