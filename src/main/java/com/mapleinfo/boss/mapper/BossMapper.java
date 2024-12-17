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
	public int insertBossBoard(Boss boss);
	
	// 게시물 한개 가져오기
	public Boss selectBossBoardByBossId(int boardId);
	
	// 게시물 업데이트
	public int updateBossBoard(
			@Param("bossId") int bossId,
			@Param("state") String state,
			@Param("recommendStat") String recommendStat,
			@Param("subject") String subject,
			@Param("content") String content
			);
	
	
	// 게시물 삭제
	public int deleteBossBoardByBossId(int bossId);
	
	
	// 보스 멤버 변화로 인한 게시물 업데이트
	public void updateBossBoardByMember(
			@Param("bossId") int bossId,
			@Param("presentMember") int presentMember
			);	
	
	// 인원 수가 차면 종료로 변경되는 업데이트
	public void updateBossBoardByState(
			@Param("bossId")int bossId, 
			@Param("state") String state
			);
	
	
}
