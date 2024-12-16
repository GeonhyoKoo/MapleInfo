package com.mapleinfo.bossMember.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.mapleinfo.bossMember.domain.BossMember;

@Mapper
public interface BossMemberMapper {

	
	public int insertBossMember(@Param("bossId") int bossId, @Param("userId") int userId);
	
	
	// 보스 멤버 리스트로 가져오기
	public List<BossMember> selectBossMemberListByBoardId(int boardId);
	
	
	// 보스 게시물 삭제로 인한 멤버삭제
	public void deleteBossMemberByBossId(int bossId);
	
}
