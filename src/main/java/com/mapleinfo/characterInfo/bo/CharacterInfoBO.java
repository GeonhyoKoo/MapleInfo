package com.mapleinfo.characterInfo.bo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mapleinfo.character.bo.CharacterBO;
import com.mapleinfo.character.entity.CharacterEntity;
import com.mapleinfo.characterInfo.domain.CharacterInfoDTO;
import com.mapleinfo.ranking.bo.RankingBO;
import com.mapleinfo.ranking.domain.Ranking;
import com.mapleinfo.ranking.domain.RankingDTO;
import com.mapleinfo.user.bo.UserBO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CharacterInfoBO {

	
	private final RankingBO rankingBO;
	private final UserBO userBO;
	private final CharacterBO characterBO;
	
	
	
	// 랭킹 리스트로 가져오기
	// 랭킹 리스트 -> DTO로 변환
	// 캐릭터 , 유저 정보가 필요하기 때문에
	public List<CharacterInfoDTO> getRankingList(){
		
		List<Ranking> rankingList = rankingBO.getRankingList();
		
		List<CharacterInfoDTO> rankingDTOList = new ArrayList<CharacterInfoDTO>();
		
		
		if (rankingList.size() > 0) {
			
			for (Ranking ranking : rankingList) {
				
				CharacterInfoDTO rankingDTO = new CharacterInfoDTO();
				
				int characterId = ranking.getCharacterId();
				// 랭킹 
				rankingDTO.setRank(ranking);
				// 캐릭터
				
				CharacterEntity character = characterBO.getCharacterByCharacterId(characterId);
				int userId = character.getUserId();
				
				rankingDTO.setCharacter(characterBO.getCharacterByCharacterId(characterId));
				// 유저
				rankingDTO.setUser(userBO.getUserEntityById(userId));
				
				// 리스트에 담기
				rankingDTOList.add(rankingDTO);
			}
		}
		
		return rankingDTOList;
	}
		
	
}
