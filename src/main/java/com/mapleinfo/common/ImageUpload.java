package com.mapleinfo.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;


@RequestMapping("/upload/*")
@RestController
public class ImageUpload {

	
	// 임시 폴더 경로설정
	// /Users/geonhyo/koo/6_maple_project/workspace/temp/
	public static final String FILE_TEMP_UPLOAD_PATH = "/Users/geonhyo/koo/6_maple_project/workspace/temp/";
	
	
	
	// 이미지 임시 폴더 업로드
	@PostMapping("/imageUpload")
	public Map<String , Object> uploadTempSummerNoteImage(
			@RequestParam("file") MultipartFile file,
			HttpSession session
			) {
		
		Map<String , Object> result = new HashMap<>();
		
		
		// session 에 저장된 로그인 아이디
		// 로그인 안되어 있으면 쳐내
		String loginId = (String)session.getAttribute("loginId");
		if (loginId == null) {
			result.put("code", 300);
			return result;
		}
		
		// 업로드된 파일의 원본 파일명과 확장자 추출
		String originalFileName = file.getOriginalFilename();
		// 확장자
		String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
		// 확장자 체크
		if ( !(extension.equals(".jpg")) && !(extension.equals(".png")) && !(extension.equals(".gif"))) {
			result.put("code", 500);
			result.put("error_message", "확장자 오류");
			return result; 
		}
		
		// 저장할 파일 이름
		// UUID를 사용
		String fileName = UUID.randomUUID() + extension;
		
		// temp에 저장될 패스
		///Users/geonhyo/koo/6_maple_project/workspace/temp/f3de3f6d-ac72-4552-b261-6ba3717ef425.png
		String filePath = FILE_TEMP_UPLOAD_PATH + fileName;
		
	    
		// 파일 업로드
		try {
			byte[] bytes =  file.getBytes();
			Path path = Paths.get(filePath);
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
			result.put("code", 500);
			result.put("error_message", "파일 업로드 실패");
			return null;
		}
		
		result.put("code", 200);
		// /temp/f3de3f6d-ac72-4552-b261-6ba3717ef425.png
		String url = "/temp/" + fileName;
		result.put("url", url);
		return result;
	}
	
	
	
	
	// 임시파일 삭제
//	@PostMapping("/imageDelete")
//	public Map<String, Object> imageDelete(
//			HttpSession session,
//			@RequestParam("file")
//			){
//		
//		
//		
//		Map<String , Object> result = new HashMap<>();
//		
//		return result;
//	}
	
}
