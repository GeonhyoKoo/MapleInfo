package com.mapleinfo.boss.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mapleinfo.boss.domain.Boss;
import com.mapleinfo.boss.domain.BossDTO;
import com.mapleinfo.boss.mapper.BossMapper;
import com.mapleinfo.bossMember.bo.BossMemberBO;
import com.mapleinfo.bossMember.domain.BossMember;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BossBO {

	private final BossMapper bossMapper;
	private final BossMemberBO bossMemberBO;
	
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
		
		Boss boss = new Boss();
		boss.setBossName(bossName);
		boss.setUserId(userId);
		boss.setWorld(world);
		boss.setState(state);
		boss.setRecommendStat(recommendStat);
		boss.setMemberLimit(memberLimit);
		boss.setPresentMember(1);
		boss.setSubject(subject);
		boss.setContent(content);
		// boss 게시판에 쌓기
		
		
		int resultCount = bossMapper.insertBossBoard(boss);
		
		// boss 멤버에 쌓기
		int bossId = boss.getId();
		
		int count = bossMemberBO.addBossMember(bossId, userId);
		if (count == 0) {
			return -1;
		}
		
		return 1;
	}
	
	
	
	// bossBoard 한 개 가져오기 detail 담기 위해
	// dto로 변환 수정하기 버튼 활성화를 위해서
	public BossDTO getBossBoardByBossId(int boardId , int userId) {
		
		Boss boss = bossMapper.selectBossBoardByBossId(boardId);
		
		BossDTO bossDTO = new BossDTO();
		bossDTO.setBoss(boss);
		
		List<BossMember> bossMember = bossMemberBO.getBossMember(boardId);
		bossDTO.setBossMember(bossMember);
		
		
		return bossDTO;
	}
	
	
	// 게시물 수정하기
	// bossId, userId, state, recommendStat, subject, content
	public int updateBossBoard(int bossId, int userId, String state, 
			String recommendStat, String subject, String content) {
		
		// 게시물 조회먼저
		Boss boss = bossMapper.selectBossBoardByBossId(bossId);
		int boardId = boss.getUserId();
		// 게시물 작성자와 로그인 자 일치하지 않을 때
		if(boardId != userId) {
			return -1;
		}
		
		int updateBoss = bossMapper.updateBossBoard(bossId, state, recommendStat, subject, content);
		
		return updateBoss;
	}
	
	
	// 게시물 삭제하기
	public int deleteBossBoard(int bossId, int userId) {
		
		// 게시물 조회먼저
		Boss boss = bossMapper.selectBossBoardByBossId(bossId);
		int boardId = boss.getUserId();
		// 게시물 작성자와 로그인 자 일치하지 않을 때
		if(boardId != userId) {
			return -1;
		}
		
		int resultCount = bossMapper.deleteBossBoardByBossId(bossId);
		if(resultCount <= 0) {
			return 0;
		}
		
		// bossMember도 삭제
		bossMemberBO.deleteBossMemberByBossId(bossId);
		
		
		return resultCount;
	}
	
	
	// 멤버 지원하기
	public int applyBossBoard(int bossId, int userId) {
		
		// 게시물 조회먼저
		Boss boss = bossMapper.selectBossBoardByBossId(bossId);
		
		int limitCount = boss.getMemberLimit();
		int presentMember = boss.getPresentMember();
		if(limitCount == presentMember) {
			return -1;
		}
		// 지원 인원에 + 1;
		presentMember += 1;
		
		// member insert
		// boss update
		bossMapper.updateBossBoardByMember(bossId, presentMember);
		bossMemberBO.addBossMember(bossId, userId);
		
		// 지원인원이 추가되어 최대 인원과 같아지면 state - 종료로 변경
		if(presentMember == limitCount) {
			String state = "종료";
			bossMapper.updateBossBoardByState("state");
		}
		
		return 1;
	}
	
	
}
