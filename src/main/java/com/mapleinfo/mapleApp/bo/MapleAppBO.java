package com.mapleinfo.mapleApp.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapleinfo.mapleApp.domain.MapleCharacterDTO;

@Service
public class MapleAppBO {

	private final RestTemplate restTemplate;
	
	@Value("${nexon.api.key}")
	private String apiKey;
	
	// 캐릭터 ocid 조회 url
	private static final String CHARACTER_URL = "https://open.api.nexon.com/maplestory/v1/id";  
	
	// 캐릭터의 기본 정보 조회 url
	
	
	// 해당 캐릭터의 장비 조회 url
	private static final String ITEM_URL = "https://open.api.nexon.com/maplestory/v1/character/item-equipment";
	
	
	
	 public MapleAppBO(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }
	
	
	public MapleCharacterDTO getMapleCharacterInfo(String nickname) {
		
		// 닉네임으로 캐릭터 정보 조회하기 위한 url 구성
		String characterSerarchUrl = UriComponentsBuilder.fromHttpUrl(CHARACTER_URL)
				.queryParam("character_name", nickname).toUriString();
				
		// 구성한 url로 api 로 조회
		String characterResponse = restTemplate.getForObject(characterSerarchUrl, String.class);
		
		// 장비 정보 조회 url 구성
		// 먼저 조회한 api에서 ocid 가져오기
		String ocid = getOcid(characterResponse);
		
		// ocid 정보가 없으면 -> 일치하는 데이터가 없다는 것이므로 return 처리
		if (ocid == null) {
			return null;
		}
		
		
		// 장비 정보 url 구성
		// request로 들어갈 정보 캐릭터 ocid , 날짜
		// 날짜를 구성 -> yyyy-MM-dd 로 바꿔야함
		String date = getDate();
		
		String itemUrl = UriComponentsBuilder.fromHttpUrl(ITEM_URL).queryParam("ocid", ocid)
				.queryParam("date", date)
				.toUriString();
		
		// 장비 정보 url로 api 조회
		 String itemResponse = restTemplate.getForObject(itemUrl, String.class);
		
		
		 MapleCharacterDTO mcd = new MapleCharacterDTO();
		 
		return mcd;
	}
	
	

	private String getOcid(String characterResponse) {
		
//		try {
//			ObjectMapper objectMapper = new ObjectMapper();
//			JsonNode rootNode = objectMapper.readTree(characterResponse);
//			
//			// null일 경우
//			JsonNode ocidNode = rootNode.path("ocid");
//			if (ocidNode.isMissingNode() || ocidNode.asText().isEmpty()) {
//				throw new IllegalArgumentException("OCID not found");
//			}
//			return ocidNode.asText();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		} 
		
		try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(characterResponse);
            
            // "ocid" 필드 값 추출
            return rootNode.path("ocid").asText();
        } catch (Exception e) {
            e.printStackTrace();
            // 예외 발생 시 null 반환
            return null;
        }
		
	}
	
	
	
	private String getDate() {
		LocalDate date = LocalDate.now().minusDays(1);
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
}
