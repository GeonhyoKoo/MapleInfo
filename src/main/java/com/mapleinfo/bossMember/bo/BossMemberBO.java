package com.mapleinfo.bossMember.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mapleinfo.bossMember.domain.BossMember;
import com.mapleinfo.bossMember.mapper.BossMemberMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BossMemberBO {

	private final BossMemberMapper bossMemberMapper;
	
	
	// 게시판 생성 or 지원 때 등록
	public int addBossMember(int bossId, int userId) {
		
		int result = bossMemberMapper.insertBossMember(bossId, userId);
		
		return result;
	}
	
	
	// 보스 멤버의 리스트 가져오기
	public List<BossMember> getBossMember(int boardId){
		
		List<BossMember> bossMemeber = bossMemberMapper.selectBossMemberListByBoardId(boardId);
		return bossMemeber;
	}
	
	// 보스 게시물 삭제로 인한 멤버 삭제
	public void deleteBossMemberByBossId(int bossId) {
		bossMemberMapper.deleteBossMemberByBossId(bossId);
	}
	
	
}
