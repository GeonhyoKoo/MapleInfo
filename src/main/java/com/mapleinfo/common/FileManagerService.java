package com.mapleinfo.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileManagerService {
	
		// 일반 파일 업로드 경로 
		//public static final String FILE_UPLOAD_PATH = "/Users/geonhyo/koo/6_maple_project/workspace/images/";
	
		public final static String FILE_UPLOAD_PATH = "/home/ec2-user/images/";
	
		// summernote 업로드 파일 경로
		public static final String FILE_UPLOAD_CLASS_BOARD_PATH = "/Users/geonhyo/koo/6_maple_project/workspace/classBoard/";
		
		// summernote 임시 저장 파일 경로
		public static final String FILE_UPLOAD_TEMP_PATH = "/Users/geonhyo/koo/6_maple_project/workspace/temp/";
		
		
		//파일 업로드 처음 건
		public String uploadFile( String loginId , MultipartFile file) {
			
			String directoryName = loginId + "_" + System.currentTimeMillis(); 
			
			String filePath = FILE_UPLOAD_PATH + directoryName + "/";

			File directory = new File(filePath);
			if (directory.mkdir() == false) {
				return null; 
			}

			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(filePath + file.getOriginalFilename());
				Files.write(path, bytes);
			} catch (IOException e) {
				e.printStackTrace();
				return null; 
			}

			return "/images/" + directoryName + "/" + file.getOriginalFilename();
		}
		
		
		//파일 업로드 두번째 부터
		public String uploadFileDuplicate(String imagePath , MultipartFile file) {
			
			String nowDirectoryName = imagePath.replace("/images/", "");
			
			String directoryName = nowDirectoryName.split("/")[0];
			
			String filePath = FILE_UPLOAD_PATH + directoryName + "/";
			
			

			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(filePath + file.getOriginalFilename());
				Files.write(path, bytes);
			} catch (IOException e) {
				e.printStackTrace();
				return null; 
			}
			return "/images/" + directoryName + "/" + file.getOriginalFilename();
		}


		// 파일 삭제 (마지막)
		public void deleteFile(String imagePath) { 
			
			Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));

			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[FileManagerService 파일삭제 시도] 이미지 파일 삭제 실패 path:{}", path.toString());
					return;
				}

				path = path.getParent();
				if (Files.exists(path)) {
					try {
						Files.delete(path);
					} catch (IOException e) {
						log.info("[FileManagerService 폴더삭제 시도] 디렉토리(폴더) 삭제 실패 path:{}", path.toString());
					}
				}
			}
		}
	
		
		// 파일 삭제 여러개 일때 앞쪽
		public void deletePrevFile(String imagePath) { 
			
			Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));

			if (Files.exists(path)) {
				try {
					Files.delete(path);
				} catch (IOException e) {
					log.info("[FileManagerService 파일삭제 시도] 이미지 파일 삭제 실패 path:{}", path.toString());
					return;
				}

			}
		}
			
			
		
			
		// summernote 이미지 저장 (처음 파일)
		// temp -> images로 이동
		public String uploadSummerNoteFileFirst(String prevPath, String loginId) {
			
			
			// 새로 만들 폴더
			String directoryName =  loginId + "_" + System.currentTimeMillis(); 
			
			// 새로 저장할 경로
			String filePath = FILE_UPLOAD_CLASS_BOARD_PATH + directoryName + "/";
			
			
			// 새로 만들 폴더 생성
			/// Users/geonhyo/koo/6_maple_project/workspace/classBoard/abcde_1734008777989
			File directory = new File(filePath);
			if( directory.mkdir() == false) { 
				return null; 
			}
			
			// 기존 파일 경로
			// /Users/geonhyo/koo/6_maple_project/workspace/temp/27922e0d-ccee-4b46-9f3a-568161cf3fd6.png
			String tempPath = FILE_UPLOAD_TEMP_PATH  + prevPath.replace("/temp/", "");
			
			try {
				// /Users/geonhyo/koo/6_maple_project/workspace/temp/27922e0d-ccee-4b46-9f3a-568161cf3fd6.png
				Path sourcePath = Paths.get(tempPath);
				 // 파일 이름 추출
				// 27922e0d-ccee-4b46-9f3a-568161cf3fd6.png
				 String fileName = sourcePath.getFileName().toString();
				// 복사할 대상 경로 (디렉토리 + 파일 이름)
				// /Users/geonhyo/koo/6_maple_project/workspace/classBoard/abcde_1734008777989/27922e0d-ccee-4b46-9f3a-568161cf3fd6.png
			    Path targetPath = Paths.get(filePath + fileName);
				// 복사 temp 에 있던 걸 -> 작성되는 폴더로
				Files.copy(sourcePath, targetPath,java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				// temp파일에 있던건 삭제하기
				Files.delete(sourcePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String filename = prevPath.split("/temp/")[1];
			
			return "/classBoard/" + directoryName + "/" + filename;
			
		}
		
		
		
		// summernote 두번째 부터 ~
		public String uploadSummerNoteFileElse(String prevPath, String nowPath) {
			
			// 첫번째 파일의 저장경로 
			// abcde_1734008963483/73fcf121-023c-41e1-9050-014f26545132.png
			String nowDirectoryName = prevPath.replace("/classBoard/", "");
			
			// 첫번째 파일이 저장된 폴더
			// abcde_1734008963483
			String directoryName = nowDirectoryName.split("/")[0];
			
			// 첫번째 파일의 풀 저장 경로
			// /Users/geonhyo/koo/6_maple_project/workspace/classBoard/abcde_1734008963483/
			String nowDirectory = FILE_UPLOAD_CLASS_BOARD_PATH + directoryName + "/";
			
			
			
			// 기존 파일의 경로
			// /Users/geonhyo/koo/6_maple_project/workspace/temp/bee02680-44fe-48ad-a0e5-9c625ae4a401.png
			String tempPath = FILE_UPLOAD_TEMP_PATH  + nowPath.replace("/temp/", "");
			
			try {
				// /Users/geonhyo/koo/5_spring_project/workspace/temp/8d4958dc-d821-4e4b-a642-e6ebca471f68.png
				Path sourcePath = Paths.get(tempPath);
				 // 파일 이름 추출
				// 8d4958dc-d821-4e4b-a642-e6ebca471f68.png
				 String fileName = sourcePath.getFileName().toString();
				// 복사할 대상 경로 (디렉토리 + 파일 이름)
				// /Users/geonhyo/koo/5_spring_project/workspace/testboard/aaaa_1733992957817/8d4958dc-d821-4e4b-a642-e6ebca471f68.png
			    Path targetPath = Paths.get(nowDirectory + fileName);
				// 복사 temp 에 있던 걸 -> 작성되는 폴더로
				Files.copy(sourcePath, targetPath,java.nio.file.StandardCopyOption.REPLACE_EXISTING);
				// temp파일에 있던건 삭제하기
				Files.delete(sourcePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String filename = nowPath.split("/temp/")[1];
			
			return "/classBoard/" + directoryName + "/" + filename;
		}
		
		
		
}
