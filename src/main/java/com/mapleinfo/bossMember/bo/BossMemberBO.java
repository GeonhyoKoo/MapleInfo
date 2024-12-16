package com.mapleinfo.bossMember.bo;

import org.springframework.stereotype.Service;

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
	
	
	
	
}
