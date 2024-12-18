package com.mapleinfo.mapleApp.bo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mapleinfo.mapleApp.domain.MapleCharacterDTO;
import com.mapleinfo.mapleApp.domain.MapleCharacterDTO.ItemEquiment;

@Service
public class MapleAppBO {

	private final RestTemplate restTemplate;
	
	@Value("${nexon.api.key}")
	private String apiKey;
	
	// 캐릭터 ocid 조회 url
	private static final String CHARACTER_URL = "https://open.api.nexon.com/maplestory/v1/id";  
	
	// 캐릭터의 기본 정보 조회 url
	private static final String BASIC_INFO_URL = "https://open.api.nexon.com/maplestory/v1/character/basic";
	
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
		
		
		// request로 들어갈 정보 캐릭터 ocid , 날짜
		// 날짜를 구성 -> yyyy-MM-dd 로 바꿔야함
		String date = getDate();
		
		//기본 정보 url
		String basicInfoUrl = UriComponentsBuilder.fromHttpUrl(BASIC_INFO_URL)
	                .queryParam("ocid", ocid)
	                .queryParam("date", date)
	                .toUriString();
		
		// api 기본 정보 조회
		String basicInfoResponse = restTemplate.getForObject(basicInfoUrl, String.class);
		
		
		
		// 장비 정보 url 구성
		String itemUrl = UriComponentsBuilder.fromHttpUrl(ITEM_URL).queryParam("ocid", ocid)
				.queryParam("date", date)
				.toUriString();
		
		// 장비 정보 url로 api 조회
		 String itemResponse = restTemplate.getForObject(itemUrl, String.class);
		
		
		 MapleCharacterDTO mcd = new MapleCharacterDTO();
		 
		 // 장비 정보 파싱
		 List<ItemEquiment> itemEquiments = parseItemEquiment(itemResponse);
		 
		// 캐릭터 기본 정보와 장비 정보 DTO로 변환
        mcd = parseCharacterInfo(basicInfoResponse);
        mcd.setItemEquiment(itemEquiments);
		 
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
	
	// 장비 정보 파싱
    private List<ItemEquiment> parseItemEquiment(String itemResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(itemResponse);
            JsonNode itemListNode = rootNode.path("items");

            // 장비 정보 리스트로 변환
            return itemListNode.findValuesAsText("itemName").stream()
                    .map(itemName -> {
                        ItemEquiment item = new ItemEquiment();
                        item.setItemName(itemName);
                        item.setItemType("Equip");  // 예시로 타입을 Equip으로 설정 (필요시 수정)
                        item.setItemIcon("icon_url");  // 실제 API 응답에서 아이콘 URL을 파싱해야 함
                        return item;
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
    // 캐릭터 기본 정보 파싱
    private MapleCharacterDTO parseCharacterInfo(String basicInfoResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(basicInfoResponse);

            MapleCharacterDTO mcd = new MapleCharacterDTO();
            mcd.setNickname(rootNode.path("nickname").asText());
            mcd.setLevel(rootNode.path("level").asInt());
            mcd.setWorld(rootNode.path("world").asText());
            mcd.setRepresentativeImageUrl(rootNode.path("image").asText());

            return mcd;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
	
	
	
	
	private String getDate() {
		LocalDate date = LocalDate.now().minusDays(1);
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
	
}
