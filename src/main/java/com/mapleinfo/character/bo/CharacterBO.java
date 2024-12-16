package com.mapleinfo.character.bo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mapleinfo.character.config.ConvertToInt;
import com.mapleinfo.character.domain.CharacterDTO;
import com.mapleinfo.character.entity.CharacterEntity;
import com.mapleinfo.character.repository.CharacterRepository;
import com.mapleinfo.common.FileManagerService;
import com.mapleinfo.ranking.bo.RankingBO;
import com.mapleinfo.user.bo.UserBO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CharacterBO {

	private final CharacterRepository characterRepository;
	private final UserBO userBO;
	private final FileManagerService fileManeger;
	private final RankingBO rankingBO;
	
	/**
	 * 캐릭터 닉네임 중복확인
	 * @param name
	 * @return
	 */
	public CharacterEntity getCharacterByName(String name) {
		return characterRepository.findByName(name);
	}
	
	
	/**
	 * 내 캐릭터 추가하기
	 * 대표 캐릭터 없으면 대표캐릭터로 , 아니면 그냥 추가만
	 * 기존 코드와의 문제점
	 * 조회를 위해 userId를 사용했는데 리스트목록을 가져오면서 겹치는 상황이 발생 
	 * @param name
	 * @param characterClass
	 * @param world
	 * @param level
	 * @param stat
	 * @param file
	 * @param userId
	 * @param loginId
	 * @return
	 */
//	public CharacterEntity addCharacter(
//			String name , String characterClass, String world, int level, String stat, MultipartFile file, int userId , String loginId
//			) {
//		
//		// 조회먼저 ( 대표캐릭터를 등록할지를 위해)
//		CharacterEntity character = characterRepository.findByUserId(userId);
//		
//		FileManagerService fileManeger = new FileManagerService();
//		String imagePath = fileManeger.uploadFile(loginId, file);
//		
//		if (character == null) {
//			 character = characterRepository.save(CharacterEntity.builder()
//					.userId(userId).name(name).characterClass(characterClass)
//					.world(world).level(level).stat(stat).characterImg(imagePath)
//					.representCharacter(true).build());
//			return character;
//		} else {
//			character = characterRepository.save(CharacterEntity.builder()
//					.userId(userId).name(name).characterClass(characterClass)
//					.world(world).level(level).stat(stat).characterImg(imagePath)
//					.representCharacter(false).build());
//				return character;
//		}
//		
//	}
	
	public CharacterEntity addCharacter(
			String name , String characterClass, String world, int level, String stat, MultipartFile file, int userId , String loginId
			) {
		
		// 조회먼저 ( 대표캐릭터를 등록할지를 위해)
		List<CharacterEntity> characterList = characterRepository.findByUserId(userId);
		
		// 이미지 저장
		String imagePath = fileManeger.uploadFile(loginId, file);
		
		// 스탯 숫자로 변환 후 추가로 넣기
		ConvertToInt cti = new ConvertToInt();
		int originStat = cti.convertStatToInt(stat);
		
		
		if (characterList.size() == 0) {
			CharacterEntity character = characterRepository.save(CharacterEntity.builder()
					.userId(userId).name(name).characterClass(characterClass)
					.world(world).level(level).stat(stat).originStat(originStat).characterImg(imagePath)
					.representCharacter(true).build());
			return character;
		} else {
			CharacterEntity character = characterRepository.save(CharacterEntity.builder()
					.userId(userId).name(name).characterClass(characterClass)
					.world(world).level(level).stat(stat).originStat(originStat).characterImg(imagePath)
					.representCharacter(false).build());
				return character;
		}
		
	}
	
	
	/**
	 * 대표 캐릭터 한개 가져오기
	 * 마이 인포 , 메인 화면에서 사용하기 위해
	 * @param userId
	 * @return
	 */
	
	// 추가 캐릭터 랭크 넣기
	public CharacterDTO getRepresentCharacter(int userId) {
		
		CharacterEntity result = characterRepository.findByUserIdAndRepresentCharacter(userId , true);
		
		if (result == null) {
			return null;
		}
		
		CharacterDTO characterDTO = new CharacterDTO();
		characterDTO.setUser(userBO.getUserEntityById(userId));
		characterDTO.setCharacter(result);
		
		int characterId = result.getId();
		LocalDate today = LocalDate.now();
		characterDTO.setRanking(rankingBO.getRanking(characterId , today));
		
		
		return characterDTO;
	}
	
	
	/**
	 * 캐릭터 리스트 가져오기
	 * 정보 수정에 사용하기 위해
	 * @param userId
	 * @return
	 */
	public List<CharacterEntity> getCharacterList(int userId){
		
		return characterRepository.findByUserId(userId);
		
	}
	
	
	/**
	 * 캐릭터 수정 전 해당 캐릭터 있는지 조회 
	 * @param characterId
	 * @return
	 */
	public CharacterEntity getCharacterByCharacterId(int characterId) {
		return characterRepository.findById(characterId);
	}
	
	
	/**
	 * 캐릭터 업데이트
	 * 기존 정보 조회 -> 파일 존재시 새로운 이미지 저장 후 디렉토리 삭제 -> 수정
	 * @param characterId
	 * @param name
	 * @param level
	 * @param stat
	 * @param file
	 * @param userId
	 * @param loginId
	 * @return
	 */
	public CharacterEntity updateCharacter(int characterId, String name, int level, String stat,
			MultipartFile file, int userId, String loginId) {
		
		CharacterEntity character = characterRepository.findById(characterId);
		if (character == null) {
			log.info("[캐릭터 업데이트 시 불러오기 실패] characterId : {}" , characterId );
			return null;
		}
		
		String fileImagePath = character.getCharacterImg();
		if (fileImagePath == null) {
			log.info("[캐릭터 업데이트 시 이미지 불러우기 실패] fileImagePath : {}" , fileImagePath);
			return null;
		}
		
		String newFileImagePath = null;
		
		// 파일이 있는지 체크
		if (file == null) {
			// 파일이 없을땐 나머지 내용들 업데이트
			character = character.toBuilder().level(level).stat(stat).updatedAt(LocalDateTime.now()).build();
			character = characterRepository.save(character);
		} else {
			// 새 파일 저장
			newFileImagePath = fileManeger.uploadFile(loginId, file);
			
			// 파일, 디렉토리 삭제
			if (newFileImagePath != null && fileImagePath != null) {
				fileManeger.deleteFile(fileImagePath);
			}
			
			character = character.toBuilder().level(level).stat(stat).characterImg(newFileImagePath)
					.updatedAt(LocalDateTime.now()).build();
			
			character = characterRepository.save(character);
		}
		
		return character;
	}
	
	
	
	// 캐릭터 삭제
	/**
	 * 캐릭터 삭제
	 * 조회 -> 이미지 파일 삭제 -> 정보 삭제
	 * @param characterId
	 * @param userId
	 * @param loginId
	 * @return
	 */
	public int deleteCharacter(int characterId , int userId, String loginId) {
		
		CharacterEntity character = characterRepository.findById(characterId);
		if(character == null) {
			log.info("[캐릭터 삭제 조회 실패] characetrId : {}" , characterId);
			return -1;
		}
		
		String imagePath = character.getCharacterImg();
		if (imagePath == null) {
			log.info("[캐릭터 이미지 조회 실패] imagePath : {}" , imagePath);
			return -2;
		}
		
		fileManeger.deleteFile(imagePath);
		characterRepository.delete(character);
		return 1;
	}
	
	
	
	/**
	 * 대표 캐릭터 변경하기
	 * @param userId
	 * @param characterId
	 * @return
	 */
	public int updateRepresentCharacter(int userId , int characterId) {
		
		CharacterEntity character = characterRepository.findByUserIdAndRepresentCharacter(userId, true);
		
		if (character.getId() == characterId) {
			return -1;
		}
		
		character = character.toBuilder().representCharacter(false).createdAt(LocalDateTime.now()).build();
		characterRepository.save(character);
		
		character = characterRepository.findById(characterId);
		if (character.isRepresentCharacter()) {
			return 0;
		}
		
		character = character.toBuilder().representCharacter(true).createdAt(LocalDateTime.now()).build();
		characterRepository.save(character);
		return 1;
		
	}
	
	
	
}
